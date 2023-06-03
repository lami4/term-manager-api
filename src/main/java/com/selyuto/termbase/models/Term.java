package com.selyuto.termbase.models;

import com.selyuto.termbase.converters.JsonHashmapConverter;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "terms")
public class Term {

    @Id
    @GeneratedValue(generator = "term_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "term_seq", sequenceName = "term_seq", allocationSize = 1)
    private Long id;

    @Column(name = "properties")
    @Convert(converter = JsonHashmapConverter.class)
    private Map<String, Object> properties;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

}