package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    private void sendThymeleafMail(String to, String template, Map<String, Object> data, String subject) {
        Locale locale = LocaleContextHolder.getLocale();
        final Context ctx = new Context(locale);
        ctx.setVariables(data);
        String htmlBody = templateEngine.process(template, ctx);
        try {
            sendMail(to, htmlBody, subject);
        } catch(MessagingException mex) {
            System.out.println("error"); //TODO exception management
        }
    }

    private void sendMail(String to, String template, String subject) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setFrom("gogreen2022.contact@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(template, true);
        this.mailSender.send(mimeMessage);
    }

    @Override
    public void purchase(String buyerEmail, String buyer, Product product, int amount) {
        Map<String, Object> data = new HashMap<>();
        data.put("buyer", buyer);
        data.put("product", product.getName());
        data.put("amount", amount);
        sendThymeleafMail(buyerEmail, "productPurchase", data, "Se completó tu compra");
    }
}
