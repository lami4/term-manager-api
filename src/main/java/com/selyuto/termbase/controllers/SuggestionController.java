package com.selyuto.termbase.controllers;


import com.selyuto.termbase.models.Suggestion;
import com.selyuto.termbase.services.SuggestionService;

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
@RequestMapping("/suggestions")
public class SuggestionController {

    final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("")
    public List<Suggestion> getSuggestions() {
        return suggestionService.getSuggestions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestionById(@PathVariable Long id) {
        try {
            Suggestion suggestion = suggestionService.getSuggestionById(id);
            if (suggestion == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(suggestion, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Long> createSuggestion(@RequestBody Suggestion suggestion) {
        try {
            Long id = suggestionService.createSuggestion(suggestion);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSuggestion(@RequestBody Suggestion suggestion, @PathVariable Long id) {
        try {
            suggestionService.updateSuggestion(suggestion);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSuggestion(@PathVariable Long id) {
        suggestionService.deleteSuggestion(id);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Long> acceptSuggestion(@RequestBody Suggestion suggestion, @PathVariable Long id) {
        try {
            suggestionService.acceptSuggestion(suggestion);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}