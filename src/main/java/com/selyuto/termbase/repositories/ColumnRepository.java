package com.selyuto.termbase.repositories;

import com.selyuto.termbase.models.Column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ColumnRepository extends JpaRepository<Column, Long> {

    @Modifying
    @Query(value = "UPDATE terms SET properties = cast(properties as JSONB) || cast(?1 as JSONB)", nativeQuery = true)
    void addPropertyToJsonb(String prop);

    @Modifying
    @Query(value = "UPDATE terms SET properties = cast(properties as JSONB) - ?1", nativeQuery = true)
    void removePropertyFromJson(String propertyId);

    @Modifying
    @Query(value = "UPDATE columns SET position = ?2 WHERE id = ?1", nativeQuery = true)
    void updateColumnPosition(int columnId, int position);

}