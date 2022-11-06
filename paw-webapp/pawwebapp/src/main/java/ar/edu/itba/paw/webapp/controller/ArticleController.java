package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ArticleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ArticleController {

    private final SecurityService securityService;
    private final SellerService sellerService;
    private final ArticleService articleService;

    private final ProductService productService;

    @Autowired
    public ArticleController(final SecurityService securityService,
                             final SellerService sellerService,
                             final ArticleService articleService, final ProductService productService){
        this.securityService = securityService;
        this.sellerService = sellerService;
        this.articleService = articleService;
        this.productService = productService;
    }

    @RequestMapping(value = "/createArticle", method = RequestMethod.GET)
    public ModelAndView createArticle(
            @ModelAttribute("articleForm") final ArticleForm form
    ){
        return new ModelAndView("createArticle");
    }

    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public ModelAndView createArticle(
            @Valid @ModelAttribute("articleForm") final ArticleForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            return createArticle(form);
        }
        User logged = securityService.getLoggedUser();
        //Should this check even be done? Doesn't spring security check for SELLER role?
        if(logged == null) throw new UnauthorizedRoleException();
        Optional<Seller> seller = sellerService.findByUserId(logged.getId());
        if(!seller.isPresent()) throw new UserNotFoundException();

        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {throw new RuntimeException(e);}

        articleService.create(seller.get(), form.getMessage(), image, LocalDateTime.now());

        return new ModelAndView("redirect:/sellerPage/" + seller.get().getId());
    }

    @RequestMapping(value = "/sellerPage/{sellerId:[0-9]+}/news")
    public ModelAndView sellerNews(@PathVariable("sellerId") long sellerId,
                                   @RequestParam(name="page", defaultValue = "1") final int page){
        Optional<Seller> seller = sellerService.findById(sellerId);
        if(!seller.isPresent()) throw new UserNotFoundException();
        List<Article> news = articleService.getBySellerId(sellerId);

        ModelAndView mav = new ModelAndView("sellerNews");

        mav.addObject("user", seller.get().getUser());
        User user = securityService.getLoggedUser();
        String loggedEmail = user == null? null : user.getEmail();
        mav.addObject("loggedEmail", loggedEmail);

        List<List<Article>> newsPages = productService.divideIntoPages(news, 8);

        mav.addObject("news", newsPages.get(page-1));
        mav.addObject("seller", seller.get());
        mav.addObject("user", seller.get().getUser());
        mav.addObject("pages", newsPages);
        mav.addObject("currentPage", page);
        return mav;
    }

    @RequestMapping(value = "/deleteArticle/{articleId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long articleId){
        Optional<Article> article = articleService.getById(articleId);
        if(!article.isPresent())
            throw new NoSuchElementException();
        long sellerId = article.get().getSeller().getId();
        articleService.delete(articleId);
        return new ModelAndView("redirect:/sellerPage/" + sellerId + "/news");
    }
}
