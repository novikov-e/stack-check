package ru.taskmanger.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.taskmanger.model.Column;
import ru.taskmanger.repository.ColumnDao;
import ru.taskmanger.repository.jdbc.mapper.ColumnMapper;
import ru.taskmanger.repository.jdbc.mapper.UUIDMapper;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Repository("columnDao")
public class JdbcColumnDao implements ColumnDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcColumnDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID createColumn(UUID projectId, String columnName, int columnPosition) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into columns (project_id, column_name, column_position) " +
                            "values (?, ?, ?);", new String[] { "column_id" });
            preparedStatement.setObject(1, projectId);
            preparedStatement.setString(2, columnName);
            preparedStatement.setInt(3, columnPosition);
            return preparedStatement;
        }, keyHolder);
        return (UUID) keyHolder.getKeys().get("column_id");
    }

    @Override
    public Column getColumnById(UUID columnId) {
        return jdbcTemplate.query("select column_id, column_name, column_position from columns where column_id = ?;",
                new Object[]{columnId}, new ColumnMapper()).stream().findAny().orElse(null);
    }

    @Override
    public void updateColumnName(UUID columnId, String columnName) {
        jdbcTemplate.update("update columns set column_name = ? " +
                "where column_id = ?;", columnName, columnId);
    }

    @Override
    public void updateColumnPosition(UUID columnId, int columnPosition) {
        jdbcTemplate.update("update columns set column_position = ? " +
                "where column_id = ?;", columnPosition, columnId);
    }

    @Override
    public void deleteColumn(UUID columnId) {
        jdbcTemplate.update("delete from columns where column_id = ?;", columnId);
    }

    @Override
    public List<UUID> getSortingTaskListsByColumnId(UUID columnId) {
        return jdbcTemplate.query("select task_list_id from task_lists " +
                "where column_id = ?;", new Object[]{columnId}, new UUIDMapper());
    }
}
