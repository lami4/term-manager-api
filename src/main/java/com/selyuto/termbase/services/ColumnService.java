package com.selyuto.termbase.services;

import com.selyuto.termbase.dto.ColumnPositionSetting;
import com.selyuto.termbase.models.Column;
import com.selyuto.termbase.repositories.ColumnRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class ColumnService {

    private final ColumnRepository columnRepository;

    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public List<Column> getColumns() {
        return columnRepository.findAll();
    }

    public Column getColumnById(Long id) {
        return columnRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long saveColumn(Column column) {
        columnRepository.save(column);
        column.setHtmlId("column_".concat(column.getId().toString()));
        columnRepository.addPropertyToJsonb(getJsonPropertyAsString(column.getHtmlId()));
        return column.getId();
    }

    public Column updateColumn(Column column) {
        columnRepository.save(column);
        return getColumnById(column.getId());
    }

    @Transactional
    public void deleteColumn(Long id) {
        columnRepository.deleteById(id);
        columnRepository.removePropertyFromJson("column_" + id);
    }

    @Transactional
    public void reorderColumns(List<ColumnPositionSetting> columnPositionSettings) {
        columnPositionSettings.forEach(setting -> columnRepository.updateColumnPosition(setting.getId(), setting.getPosition()));
    }

    private String getJsonPropertyAsString(String propertyName) {
        return String.format("{\"%s\":null}", propertyName);
    }
}