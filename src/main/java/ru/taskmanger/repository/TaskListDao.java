package ru.taskmanger.repository;

import ru.taskmanger.model.TaskList;
import java.util.List;
import java.util.UUID;

public interface TaskListDao {
    UUID createTaskList(UUID columnId, String taskListName, int taskListPosition);
    TaskList getTaskListById(UUID taskListId);
    void updateTaskListName(UUID taskListId, String taskListName);
    void updateTaskListColor(UUID taskListId, String taskListColor);
    void updateTaskListPosition(UUID taskListId, int taskListPosition);
    void deleteTaskList(UUID taskListId);
    List<UUID> getSortingTasksByTaskListId(UUID taskListId);
}
