package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.FAQ;

import java.util.List;
import java.util.Optional;

public interface FAQDao {
    List<FAQ> getFAQs();
}
