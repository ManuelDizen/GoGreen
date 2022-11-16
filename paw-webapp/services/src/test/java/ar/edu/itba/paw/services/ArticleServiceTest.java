package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ArticleDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private UserService userService;
    @Mock
    private SellerService sellerService;
    @Mock
    private ImageService imageService;
    @Mock
    private ArticleDao articleDao;
    @Mock
    private FavoriteService favoriteService;
    @Mock
    private EmailService emailService;
    @Test
    public void testCreateArticle(){
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(sellerService.findByUserId(anyLong())).thenReturn(Optional.of(AUX_SELLER));
        //when(articleService.parseByteArrayToImage(any())).thenReturn(AUX_IMAGE);
        when(imageService.create(any())).thenReturn(AUX_IMAGE);
        when(articleDao.create(any(), anyString(), any(), any())).thenReturn(AUX_ARTICLE);
        when(favoriteService.getSubscribedUsers(any())).thenReturn(new ArrayList<>());
        doNothing().when(emailService).newArticleFromSeller(any(), any(), anyString());


        Article article = null;
        try{
            article = articleService.create(AUX_ARTICLE_MESSAGE, IMAGE_BYTES, DATE_TIME);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Article creation failed: " + e.getMessage());
        }
        Assert.assertNotNull(article);
        Assert.assertEquals(article.getMessage(), AUX_ARTICLE_MESSAGE);
    }
}
