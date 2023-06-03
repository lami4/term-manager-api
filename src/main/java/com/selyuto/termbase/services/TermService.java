package com.selyuto.termbase.services;


import com.selyuto.termbase.models.Term;
import com.selyuto.termbase.repositories.TermRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {
    private final TermRepository termRepository;

    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    public List<Term> getTerms() {
        return termRepository.findAll();
    }

    public Term getTermById(Long id) {
        return termRepository.findById(id).orElse(null);
    }

    public Long saveTerm(Term term) {
        termRepository.save(term);
        return term.getId();
    }

    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }
}