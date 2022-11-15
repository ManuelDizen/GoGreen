package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Article;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private UserService userService;
    @Mock
    private SellerService sellerService;

    @Test
    public void testCreateArticle(){
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(sellerService.findByUserId(anyLong())).thenReturn(Optional.of(AUX_SELLER));
        when(articleService.parseByteArrayToImage(any())).thenReturn(AUX_IMAGE);

        Article article = null;
        try{
            article = articleService.create(ARTICLE_MESSAGE, IMAGE_BYTES, DATE_TIME);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Article creation failed: " + e.getMessage());
        }
        Assert.assertNotNull(article);
        Assert.assertEquals(article.getMessage(), ARTICLE_MESSAGE);
    }
}
