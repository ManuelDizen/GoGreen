package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.exceptions.EmailErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    
    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    private final MessageSource messageSource;

    @Autowired
    public EmailServiceImpl(final JavaMailSender mailSender, final SpringTemplateEngine templateEngine, final MessageSource messageSource) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
    }

    private void sendThymeleafMail(String to, String template, Map<String, Object> data,
                                   String subject, Locale locale) {
        final Context ctx = new Context(locale);
        ctx.setVariables(data);
        String htmlBody = templateEngine.process(template, ctx);
        String sbj = messageSource.getMessage(subject, null, locale);
        System.out.println(sbj);
        try {
            sendMail(to, htmlBody, sbj);
        } catch(MessagingException mex) {
            throw new EmailErrorException();
        }
    }

    private void sendMail(String to, String template, String subject) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setFrom("GoGreen Contact <gogreen2022.contact@gmail.com>");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(template, true);
        this.mailSender.send(mimeMessage);
    }

    @Async
    @Override
    public void purchase(String buyerEmail, String buyer, Product product, int amount, Integer price,
                         String sellerName, String sellerPhone, String sellerMail, Locale locale) {
        Map<String, Object> data = new HashMap<>();
        data.put("buyer", buyer);
        data.put("product", product.getName());
        data.put("amount", amount);
        data.put("price", price);
        data.put("total", amount*price);
        data.put("sellerName", sellerName);
        data.put("sellerPhone", sellerPhone);
        data.put("sellerMail", sellerMail);
        sendThymeleafMail(buyerEmail, "productPurchase", data,
                "subject.buyerMailTitle", locale);
    }

    @Async
    @Override
    public void itemsold(String sellerEmail, String seller, Product product, int amount, Integer price,
                         String buyerName, String buyerEmail, String buyerMessage, Locale locale) {
        Map<String, Object> data = new HashMap<>();
        data.put("seller", seller);
        data.put("product", product.getName());
        data.put("amount", amount);
        data.put("price", price);
        data.put("buyerName", buyerName);
        data.put("buyerEmail", buyerEmail);
        data.put("buyerMessage", buyerMessage);

        sendThymeleafMail(sellerEmail, "sellerPurchase", data,
                "subject.sellerMailTitle", locale);
    }

    @Async
    @Override
    public void registration(User user, Locale locale) {
        Map<String,Object> data = new HashMap<>();
        data.put("name", user.getFirstName());
        data.put("surname", user.getSurname());
        data.put("email", user.getEmail());
        sendThymeleafMail(user.getEmail(), "userRegistration", data,
                "subject.registerUserTitle", locale);
    }

    @Async
    @Override
    public void noMoreStock(Product product, String sellerEmail, String sellerName,
                            String sellerSurname, Locale locale) {
        Map<String, Object> data = new HashMap<>();
        data.put("productName", product.getName());
        data.put("name", sellerName);
        data.put("surname", sellerSurname);
        sendThymeleafMail(sellerEmail, "noStock", data,
                "subject.noMoreStock", locale);
    }

    @Async
    @Override
    public void orderCancelled(Order order, Locale buyerLocale, Locale sellerLocale) {
        notifyBuyerOrderCancelled(order.getProductName(), order.getBuyerEmail(), order.getBuyerName(),
                order.getBuyerSurname(), order.getParsedDateTime(), order.getAmount(), buyerLocale);
        notifySellerOrderCancelled(order.getProductName(), order.getSellerEmail(), order.getSellerName(),
                order.getSellerSurname(), order.getAmount(), sellerLocale);
    }

    @Async
    @Override
    public void newArticleFromSeller(Seller seller, List<User> subscribed, String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("sellerName", seller.getUser().getFirstName());
        data.put("sellerSurname", seller.getUser().getSurname());
        data.put("articleMessage", message);
        for(User u : subscribed){
            sendThymeleafMail(u.getEmail(), "newArticle", data, "subject.newArticle",
                    u.getLocale());
        }
    }

    @Async
    @Override
    public void newComment(User user, Product product, String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("userName", user.getFirstName());
        data.put("userSurname", user.getSurname());
        data.put("message", message);
        data.put("productName", product.getName());
        sendThymeleafMail(product.getSeller().getUser().getEmail(),
                "newComment", data, "subject.newComment", product.getSeller().getUser().getLocale());
    }

    @Async
    @Override
    public void replyComment(User user, Product product, String message){
        Map<String, Object> data = new HashMap<>();
        data.put("productName", product.getName());
        data.put("userName", user.getFirstName());
        data.put("userSurname", user.getSurname());
        data.put("message", message);
        sendThymeleafMail(user.getEmail(), "replyComment", data,
                "subject.replyComment",
                user.getLocale());
    }

    @Async
    @Override
    public void updatePassword(User user, String link, Locale userLocale) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getFirstName());
        data.put("link", link);
        sendThymeleafMail(user.getEmail(), "updatePassword", data,
                "subject.resetpassword", userLocale);
    }

    void notifyBuyerOrderCancelled(String productName, String buyerEmail, String buyerName, String buyerSurname,
                                   String orderDateTime, Integer amount, Locale locale){
        Map<String, Object> data = new HashMap<>();
        data.put("productName", productName);
        data.put("name", buyerName);
        data.put("surname", buyerSurname);
        data.put("datetime", orderDateTime);
        data.put("amount", amount);
        sendThymeleafMail(buyerEmail, "orderCancelledBuyer", data,
                "subject.orderCancelledBuyer", locale);
    }

    void notifySellerOrderCancelled(String productName, String sellerEmail, String sellerName, String sellerSurname,
                                    Integer amount, Locale locale){
        Map<String, Object> data = new HashMap<>();
        data.put("productName", productName);
        data.put("name", sellerName);
        data.put("surname", sellerSurname);
        data.put("amount", amount);
        sendThymeleafMail(sellerEmail, "orderCancelledSeller", data,
                "subject.orderCancelledSeller", locale);
    }


}
