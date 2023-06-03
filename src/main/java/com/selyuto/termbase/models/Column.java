package com.selyuto.termbase.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "columns")
public class Column {
    @Id
    @GeneratedValue(generator = "column_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "column_seq", sequenceName = "column_seq", allocationSize = 1)
    private Long id;
    @javax.persistence.Column(name = "name")
    private String name;
    @javax.persistence.Column(name = "html_id")
    private String htmlId;
    @javax.persistence.Column(name = "sortable")
    private Boolean sortable;
    @javax.persistence.Column(name = "filterable")
    private Boolean filterable;
    @javax.persistence.Column(name = "element_type")
    private String elementType;
    @javax.persistence.Column(name = "mandatory")
    private Boolean mandatory;
    @javax.persistence.Column(name = "position")
    private int position;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "column_id")
    private List<DropdownOption> dropdownOptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlId() {
        return htmlId;
    }

    public void setHtmlId(String htmlId) {
        this.htmlId = htmlId;
    }

    public Boolean getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public Boolean getFilterable() {
        return filterable;
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<DropdownOption> getDropdownOptions() {
        return dropdownOptions;
    }

    public void setDropdownOptions(List<DropdownOption> dropdownOptions) {
        this.dropdownOptions = dropdownOptions;
    }
}