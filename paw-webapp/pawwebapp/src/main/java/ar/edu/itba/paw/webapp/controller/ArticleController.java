package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleCreationException;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
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

    private final UserService userService;
    private final SellerService sellerService;
    private final ArticleService articleService;
    @Autowired
    public ArticleController(final UserService userService,
                             final SellerService sellerService,
                             final ArticleService articleService){
        this.userService = userService;
        this.sellerService = sellerService;
        this.articleService = articleService;
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
        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {throw new RuntimeException(e);}

        Article article = articleService.create(form.getMessage(), image, LocalDateTime.now());
        if(article == null) throw new ArticleCreationException();

        return new ModelAndView("redirect:/sellerPage/" + article.getSeller().getId());
    }

    @RequestMapping(value = "/sellerPage/{sellerId:[0-9]+}/news")
    public ModelAndView sellerNews(@PathVariable("sellerId") long sellerId,
                                   @RequestParam(name="page", defaultValue = "1") final int page){
        Optional<Seller> seller = sellerService.findById(sellerId);
        if(!seller.isPresent()) throw new UserNotFoundException();
        Pagination<Article> news = articleService.getBySellerId(sellerId, page);

        ModelAndView mav = new ModelAndView("sellerNews");

        mav.addObject("user", seller.get().getUser());
        User user = userService.getLoggedUser();
        mav.addObject("loggedEmail", user == null? null : user.getEmail());

        mav.addObject("news", news.getItems());
        mav.addObject("seller", seller.get());
        mav.addObject("user", seller.get().getUser());
        mav.addObject("pages", news.getPageCount());
        mav.addObject("currentPage", page);
        return mav;
    }

    @RequestMapping(value = "/deleteArticle/{articleId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long articleId){
        Optional<Article> article = articleService.getById(articleId);
        if(!article.isPresent())
            throw new ArticleNotFoundException();
        long sellerId = article.get().getSeller().getId();
        articleService.delete(articleId);
        return new ModelAndView("redirect:/sellerPage/" + sellerId + "/news");
    }
}
