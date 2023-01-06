package ru.taskmanger.repository;

import ru.taskmanger.model.Task;
import java.util.UUID;

public interface TaskDao {
    UUID createTask(UUID taskListId, String taskName, int taskPosition);
    Task getTaskById(UUID taskId);
    void updateTaskName(UUID taskId, String taskName);
    void updateTaskPosition(UUID taskId, int taskPosition);
    void updateTaskState(UUID taskId, boolean taskState);
    void deleteTask(UUID taskId);
}
