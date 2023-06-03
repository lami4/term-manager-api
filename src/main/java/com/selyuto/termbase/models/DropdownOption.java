package com.selyuto.termbase.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "dropdown_options")
public class DropdownOption {
    @Id
    @GeneratedValue(generator = "dropdown_option_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "dropdown_option_seq", sequenceName = "dropdown_option_seq", allocationSize = 1)
    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private Long id;
    @javax.persistence.Column(name = "position")
    private int position;
    @javax.persistence.Column(name = "name")
    private String name;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="column_id")
    @javax.persistence.Column(name = "column_id")
    private Long columnId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @JsonIgnore
//    public Column getColumn() {
//        return column;
//    }
//
//    public void setColumn(Column column) {
//        this.column = column;
//    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
