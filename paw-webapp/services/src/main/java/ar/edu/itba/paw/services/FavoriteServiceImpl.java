package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.FavoriteDao;
import ar.edu.itba.paw.interfaces.services.FavoriteService;
import ar.edu.itba.paw.interfaces.services.SecurityService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
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

    @Transactional
    @Override
    public List<Favorite> getByUserId(long userId) {
        return favoriteDao.getByUserId(userId);
    }

    @Transactional
    @Override
    public List<Seller> getFavoriteSellersByUserId(long userId){
        List<Favorite> favorites = getByUserId(userId);
        List<Seller> sellers = new ArrayList<>();
        for(Favorite f : favorites){
            if(!sellers.contains(f.getSeller())) sellers.add(f.getSeller());
        }
        return sellers;
    }

    @Transactional
    @Override
    public boolean isFavorite(User user, Seller seller){
        if(user == null || seller == null) return false;
        Set<Role> roles = user.getRoles();
        for(Role role : roles){
            if(Objects.equals(role.getName(), "SELLER")) return false;
        }

        List<Favorite> favorites = getByUserId(user.getId());
        for(Favorite fav : favorites){
            if(Objects.equals(fav.getSeller().getId(), seller.getId())) return true;
        }
        return false;
    }
}
