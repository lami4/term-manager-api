package com.selyuto.termbase.services;


import com.selyuto.termbase.authentication.Authenticator;
import com.selyuto.termbase.models.Term;
import com.selyuto.termbase.models.User;
import com.selyuto.termbase.repositories.TermRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class TermService {
    private final TermRepository termRepository;

    private final Authenticator authenticator;
    private final HttpServletRequest httpServletRequest;

    public TermService(TermRepository termRepository, Authenticator authenticator, HttpServletRequest httpServletRequest) {
        this.termRepository = termRepository;
        this.authenticator = authenticator;
        this.httpServletRequest = httpServletRequest;
    }

    public List<Term> getTerms() {
        return termRepository.findAll();
    }

    public Term getTermById(Long id) {
        return termRepository.findById(id).orElse(null);
    }

    public Long createTerm(Term term) {
        User user = authenticator.getUserBySessionIdCookie(WebUtils.getCookie(httpServletRequest, "sessionIdTm"));
        if (user != null) {
            term.setAuthoredBy(user.getFullName());
        } else {
            term.setAuthoredBy("Guest");
        }
        termRepository.save(term);
        return term.getId();
    }

    public Term updateTerm(Term term) {
        termRepository.save(term);
        return getTermById(term.getId());
    }

    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }
}