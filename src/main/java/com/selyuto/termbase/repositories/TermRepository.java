package com.selyuto.termbase.repositories;

import com.selyuto.termbase.models.Term;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface TermRepository extends JpaRepository<Term, Long> {

}