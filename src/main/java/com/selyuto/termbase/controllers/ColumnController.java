package com.selyuto.termbase.controllers;


import com.selyuto.termbase.dto.ColumnPositionSetting;
import com.selyuto.termbase.models.Column;
import com.selyuto.termbase.services.ColumnService;

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
@RequestMapping("/columns")
public class ColumnController {

    final ColumnService columnService;

    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }

    @GetMapping("")
    public List<Column> getColumns() {
        return columnService.getColumns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Column> getColumnById(@PathVariable Long id) {
        try {
            Column column = columnService.getColumnById(id);
            if (column == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(column, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Long> createColumn(@RequestBody Column column) {
        try {
            Long id = columnService.saveColumn(column);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateColumn(@RequestBody Column column, @PathVariable Integer id) {
        try {
            columnService.updateColumn(column);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteColumn(@PathVariable Long id) {
        columnService.deleteColumn(id);
    }

    @PostMapping("/reorder")
    public void reorderColumns(@RequestBody List<ColumnPositionSetting> columnPositionSetting) {
        columnService.reorderColumns(columnPositionSetting);
    }
}