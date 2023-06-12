package com.selyuto.termbase.controllers;

import com.selyuto.termbase.annotations.RequiredPrivileges;
import com.selyuto.termbase.enums.Privilege;
import com.selyuto.termbase.models.Term;
import com.selyuto.termbase.services.TermService;

import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/terms")
public class TermController {

    final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping("")
    public List<Term> getTerms() {
        return termService.getTerms();
    }

    @GetMapping("/{id}")
    @RequiredPrivileges(privileges = {Privilege.TERM_MANAGER})
    public ResponseEntity<Term> getTermById(@PathVariable Long id) {
        try {
            Term term = termService.getTermById(id);
            if (term == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(term, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    @RequiredPrivileges(privileges = {Privilege.TERM_MANAGER})
    public ResponseEntity<Long> creteTerm(@RequestBody Term term) {
        try {
            Long id = termService.createTerm(term);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    @RequiredPrivileges(privileges = {Privilege.TERM_MANAGER})
    public ResponseEntity<Term> updateTerm(@RequestBody Term term, @PathVariable Long id) {
        try {
            Term updatedTerm = termService.updateTerm(term);
            return new ResponseEntity<>(updatedTerm, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    @RequiredPrivileges(privileges = {Privilege.TERM_MANAGER})
    public void deleteTerm(@PathVariable Long id) {
        termService.deleteTerm(id);
    }
}