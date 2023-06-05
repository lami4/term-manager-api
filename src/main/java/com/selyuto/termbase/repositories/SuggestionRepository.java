package com.selyuto.termbase.repositories;

import com.selyuto.termbase.models.Suggestion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}