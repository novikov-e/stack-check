package ru.taskmanger.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.taskmanger.model.Task;
import ru.taskmanger.repository.TaskDao;
import ru.taskmanger.repository.jdbc.mapper.TaskMapper;
import java.sql.PreparedStatement;
import java.util.UUID;

@Repository("taskDao")
public class JdbcTaskDao implements TaskDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTaskDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID createTask(UUID taskListId, String taskName, int taskPosition) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into tasks (task_list_id, task_name, task_position) " +
                            "values (?, ?, ?);", new String[] { "task_id" });
            preparedStatement.setObject(1, taskListId);
            preparedStatement.setString(2, taskName);
            preparedStatement.setInt(3, taskPosition);
            return preparedStatement;
        }, keyHolder);
        return (UUID) keyHolder.getKeys().get("task_id");
    }

    @Override
    public Task getTaskById(UUID taskId) {
        return jdbcTemplate.query("select * from tasks where task_id = ?;",
                        new Object[]{taskId}, new TaskMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void updateTaskName(UUID taskId, String taskName) {
        jdbcTemplate.update("update tasks set task_name = ? " +
                "where task_id = ?;", taskName, taskId);
    }

    @Override
    public void updateTaskPosition(UUID taskId, int taskPosition) {
        jdbcTemplate.update("update tasks set task_position = ? " +
                "where task_id = ?;", taskPosition, taskId);
    }

    @Override
    public void updateTaskState(UUID taskId, boolean taskState) {
        jdbcTemplate.update("update tasks set task_state = ? where task_id = ?;",
                taskState, taskId);
    }

    @Override
    public void deleteTask(UUID taskId) {
        jdbcTemplate.update("delete from tasks where task_id = ?;", taskId);
    }
}
