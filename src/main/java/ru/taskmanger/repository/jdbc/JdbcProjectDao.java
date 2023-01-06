package ru.taskmanger.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.taskmanger.model.Project;
import ru.taskmanger.model.item.ListItem;
import ru.taskmanger.repository.ProjectDao;
import ru.taskmanger.repository.jdbc.mapper.FullProjectMapper;
import ru.taskmanger.repository.jdbc.mapper.ListItemMapper;
import ru.taskmanger.repository.jdbc.mapper.ProjectMapper;
import ru.taskmanger.repository.jdbc.mapper.UUIDMapper;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Repository("projectDao")
public class JdbcProjectDao implements ProjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcProjectDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID createProject(UUID userId, String projectName, int projectPosition) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into projects (user_id, project_name, project_position) " +
                            "values (?, ?, ?);", new String[] { "project_id" });
            preparedStatement.setObject(1, userId);
            preparedStatement.setString(2, projectName);
            preparedStatement.setInt(3, projectPosition);
            return preparedStatement;
        }, keyHolder);
        return (UUID) keyHolder.getKeys().get("project_id");
    }

    @Override
    public Project getProjectByID(UUID projectId) {
        return jdbcTemplate.query("select project_id, project_name, project_position from projects " +
                        "where project_id = ?;", new Object[]{projectId}, new ProjectMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public Project getFullProjectById(UUID projectId) {
        return jdbcTemplate.query("select * from projects p " +
                                "left join columns using (project_id) " +
                                "left join task_lists using (column_id) " +
                                "left join tasks using (task_list_id) " +
                                "where project_id = ? " +
                                "order by column_position, task_list_position, task_position;",
                new Object[]{projectId}, new FullProjectMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void updateProjectName(UUID projectId, String projectName) {
        jdbcTemplate.update("update projects set project_name = ? where project_id = ?;",
                projectName, projectId);
    }

    @Override
    public void updateProjectPosition(UUID projectId, int projectPosition) {
        jdbcTemplate.update("update projects set project_position = ? where project_id = ?;",
                projectPosition, projectId);
    }

    @Override
    public void deleteProject(UUID projectId) {
        jdbcTemplate.update("delete from projects where project_id = ?;", projectId);
    }

    @Override
    public List<ListItem> getListProjectsByUserId(UUID userId) {
        return jdbcTemplate.query("select project_id, project_name from projects " +
                        "where user_id = ? order by project_position;",
                new Object[]{userId}, new ListItemMapper());
    }

    @Override
    public List<UUID> getSortingColumnsByProjectId(UUID projectId) {
        return jdbcTemplate.query("select column_id from columns " +
                "where project_id = ? order by column_position;",
                new Object[]{projectId}, new UUIDMapper());
    }

    @Override
    public List<Project> getProjectsByUserId(UUID userId) {
        return jdbcTemplate.query("select project_id, project_name, project_position from projects " +
                "where user_id = ? order by project_position;",
                new Object[]{userId}, new ProjectMapper());
    }
}
