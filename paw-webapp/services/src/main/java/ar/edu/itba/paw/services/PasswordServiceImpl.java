package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.PasswordDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PasswordService;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordDao passwordDao;

    private final EmailService emailService;

    @Autowired
    public PasswordServiceImpl(final PasswordDao passwordDao, final EmailService emailService) {
        this.passwordDao = passwordDao;
        this.emailService = emailService;
    }

    @Override
    public Token create(String passToken, User user) {
        return passwordDao.create(passToken, user);
    }

    @Transactional
    @Override
    public void passwordToken(String path, User user) {
        //if (passwordResetTokenService.hasValidToken(user.getId())) return;
        Token token = create(UUID.randomUUID().toString(), user);
        emailService.updatePassword(
                user, path + token.getPassToken(), user.getLocale());

    }
}
