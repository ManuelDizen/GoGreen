package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.FAQDao;
import ar.edu.itba.paw.interfaces.services.FAQService;
import ar.edu.itba.paw.models.FAQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FAQServiceImpl implements FAQService {

    private final FAQDao faqDao;

    @Autowired
    public FAQServiceImpl(final FAQDao faqDao) {
        this.faqDao = faqDao;
    }

    @Override
    public List<FAQ> getFAQs() {
        return faqDao.getFAQs();
    }
}
