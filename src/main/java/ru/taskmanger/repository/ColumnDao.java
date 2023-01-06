package ru.taskmanger.repository;

import ru.taskmanger.model.Column;
import java.util.List;
import java.util.UUID;

public interface ColumnDao {
    UUID createColumn(UUID projectId, String columnName, int columnPosition);
    Column getColumnById(UUID columnId);
    void updateColumnName(UUID columnId, String columnName);
    void updateColumnPosition(UUID columnId, int position);
    void deleteColumn(UUID columnId);
    List<UUID> getSortingTaskListsByColumnId(UUID columnId);
}
