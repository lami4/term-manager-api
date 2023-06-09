package com.selyuto.termbase.services;

import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.models.Suggestion;
import com.selyuto.termbase.models.Term;
import com.selyuto.termbase.models.User;
import com.selyuto.termbase.repositories.SuggestionRepository;
import com.selyuto.termbase.repositories.TermRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static com.selyuto.termbase.authentication.AuthenticationConstants.SESSION_ID_COOKIE_NAME;

@Service
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final Authenticator authenticator;
    private final HttpServletRequest httpServletRequest;
    private final TermRepository termRepository;

    public SuggestionService(SuggestionRepository suggestionRepository,
                             Authenticator authenticator,
                             HttpServletRequest httpServletRequest,
                             TermRepository termRepository) {
        this.suggestionRepository = suggestionRepository;
        this.authenticator = authenticator;
        this.httpServletRequest = httpServletRequest;
        this.termRepository = termRepository;
    }

    public List<Suggestion> getSuggestions() {
        return suggestionRepository.findAll();
    }

    public Suggestion getSuggestionById(Long id) {
        return suggestionRepository.findById(id).orElse(null);
    }

    public Long createSuggestion(Suggestion suggestion) {
        User user = authenticator.getUserBySessionIdCookie(WebUtils.getCookie(httpServletRequest, SESSION_ID_COOKIE_NAME));
        if (user != null) {
            suggestion.setAuthoredBy(user.getFullName());
        } else {
            suggestion.setAuthoredBy("Guest");
        }
        suggestionRepository.save(suggestion);
        return suggestion.getId();
    }

    public Suggestion updateSuggestion(Suggestion suggestion) {
        suggestionRepository.save(suggestion);
        return suggestion;
    }

    @Transactional
    public Long acceptSuggestion(Suggestion suggestion) {
        Term term = new Term();
        term.setProperties(suggestion.getProperties());
        term.setAuthoredBy(suggestion.getAuthoredBy());
        termRepository.save(term);
        suggestionRepository.deleteById(suggestion.getId());
        return term.getId();
    }

    public void deleteSuggestion(Long id) {
        suggestionRepository.deleteById(id);
    }
}