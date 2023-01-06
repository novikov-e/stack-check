package ru.taskmanger.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.taskmanger.model.TaskList;
import ru.taskmanger.repository.TaskListDao;
import ru.taskmanger.repository.jdbc.mapper.TaskListMapper;
import ru.taskmanger.repository.jdbc.mapper.UUIDMapper;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Repository("taskListDao")
public class JdbcTaskListDao implements TaskListDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTaskListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID createTaskList(UUID columnId, String taskListName, int taskListPosition) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into task_lists (column_id, task_list_name, task_list_position) " +
                            "values (?, ?, ?);", new String[] { "task_list_id" });
            preparedStatement.setObject(1, columnId);
            preparedStatement.setString(2, taskListName);
            preparedStatement.setInt(3, taskListPosition);
            return preparedStatement;
        }, keyHolder);
        return (UUID) keyHolder.getKeys().get("task_list_id");
    }

    @Override
    public TaskList getTaskListById(UUID taskListId) {
        return jdbcTemplate.query("select task_list_id, task_list_name, task_list_position, task_list_color " +
                        "from task_lists where task_list_id = ?;",
                new Object[]{taskListId}, new TaskListMapper()).stream().findAny().orElse(null);
    }

    @Override
    public void updateTaskListName(UUID taskListId, String taskListName) {
        jdbcTemplate.update("update task_lists set task_list_name = ? where task_list_id = ?;",
                taskListName, taskListId);
    }

    @Override
    public void updateTaskListColor(UUID taskListId, String taskListColor) {
        jdbcTemplate.update("update task_lists set task_list_color = ? where task_list_id = ?;",
                taskListColor, taskListId);
    }

    @Override
    public void updateTaskListPosition(UUID taskListId, int taskListPosition) {
        jdbcTemplate.update("update task_lists set task_list_position = ? where task_list_id = ?;",
                taskListPosition, taskListId);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        jdbcTemplate.update("delete from task_lists where task_list_id = ?;", taskListId);
    }

    @Override
    public List<UUID> getSortingTasksByTaskListId(UUID taskListId) {
        return jdbcTemplate.query("select task_id from tasks " +
                "where task_list_id = ? order by task_position;",
                new Object[]{taskListId}, new UUIDMapper());
    }
}
