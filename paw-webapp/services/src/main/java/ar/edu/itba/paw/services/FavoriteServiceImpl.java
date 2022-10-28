package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.FavoriteDao;
import ar.edu.itba.paw.interfaces.services.FavoriteService;
import ar.edu.itba.paw.interfaces.services.SecurityService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final UserService userService;
    private final SellerService sellerService;
    private final SecurityService securityService;
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteServiceImpl(UserService userService, SellerService sellerService,
                               SecurityService securityService, FavoriteDao favoriteDao){
        this.userService = userService;
        this.sellerService = sellerService;
        this.securityService = securityService;
        this.favoriteDao = favoriteDao;
    }


    @Override
    public void toggleFavorite(long sellerId, boolean add) {
        User user = securityService.getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        Optional<Seller> maybeSeller = sellerService.findById(sellerId);
        if(!maybeSeller.isPresent()) throw new UserNotFoundException();
        Seller seller = maybeSeller.get();
        if(add){
            favoriteDao.addFavorite(user, seller);
        }
        else{
            favoriteDao.removeFavorite(user, seller);
        }
    }

    @Override
    public void removeFavorite(User user, Seller seller){
        favoriteDao.removeFavorite(user, seller);
    }

    @Override
    public List<Favorite> getByUserId(long userId) {
        return favoriteDao.getByUserId(userId);
    }

    @Override
    public boolean isFavorite(User user, Seller seller){
        if(user == null || seller == null) return false;
        List<Favorite> favorites = getByUserId(user.getId());
        for(Favorite fav : favorites){
            if(fav.getSeller() == seller) return true;
        }
        return false;
    }
}
