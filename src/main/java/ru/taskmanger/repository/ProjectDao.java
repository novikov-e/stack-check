package ru.taskmanger.repository;

import ru.taskmanger.model.Project;
import ru.taskmanger.model.item.ListItem;
import java.util.List;
import java.util.UUID;

public interface ProjectDao {
    UUID createProject(UUID userId, String projectName, int projectPosition);
    Project getProjectByID(UUID projectId);
    Project getFullProjectById(UUID projectId);
    void updateProjectName(UUID projectId, String projectName);
    void updateProjectPosition(UUID projectId, int projectPosition);
    void deleteProject(UUID projectId);
    List<ListItem> getListProjectsByUserId(UUID userId);
    List<UUID> getSortingColumnsByProjectId(UUID projectId);
    List<Project> getProjectsByUserId(UUID userId);
}