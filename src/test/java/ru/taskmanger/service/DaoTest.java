package ru.taskmanger.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.taskmanger.ContainersEnvironment;
import ru.taskmanger.TaskMangerApplication;
import ru.taskmanger.model.*;
import ru.taskmanger.repository.jdbc.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TaskMangerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DaoTest extends ContainersEnvironment {

    @Autowired
    JdbcUserDao jdbcUserDao;
    @Autowired
    JdbcProjectDao jdbcProjectDao;
    @Autowired
    JdbcColumnDao jdbcColumnDao;
    @Autowired
    JdbcTaskListDao jdbcTaskListDao;
    @Autowired
    JdbcTaskDao jdbcTaskDao;

    @Test
    public void testUserDao() {
        String email = "user@email.com";
        String updateEmail = "update@email.com";
        String password = "password";
        String updatePassword = "update_password";
        String image = "/home/imege.jpg";
        String firstname = "Name";
        String lastname = "Lastname";
        LocalDate date = LocalDate.of(1990, 1, 31);
        String sex = "M";
        UUID uuid = jdbcUserDao.createUser(email, password);
        jdbcUserDao.updateImage(uuid, image);
        jdbcUserDao.updateUserData(uuid, firstname, lastname, date, sex);
        User user = jdbcUserDao.getUserById(uuid);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getImage()).isEqualTo(image);
        assertThat(user.getFirstname()).isEqualTo(firstname);
        assertThat(user.getLastname()).isEqualTo(lastname);
        assertThat(user.getDateOfBirth()).isEqualTo(date);
        assertThat(user.getSex()).isEqualTo(sex);
        jdbcUserDao.updateEmail(uuid, updateEmail);
        jdbcUserDao.updatePassword(uuid, updatePassword);
        User updatedUser = jdbcUserDao.getUserById(uuid);
        assertThat(updatedUser.getEmail()).isEqualTo(updateEmail);
        assertThat(updatedUser.getPassword()).isEqualTo(updatePassword);
        jdbcUserDao.deleteUser(uuid);
        assertThat(jdbcUserDao.getUserById(uuid)).isEqualTo(null);
    }

    @Test
    public void testProjectDao() {
        String projectName = "Test Project";
        String updatedProjectName = "Updated Project";
        int projectPosition = 0;
        int updatedProjectPosition = 5;
        UUID userUuid = jdbcUserDao.createUser("user@email.com", "password");
        UUID projectUuid = jdbcProjectDao.createProject(userUuid, projectName, projectPosition);
        Project project = jdbcProjectDao.getProjectByID(projectUuid);
        assertThat(project.getName()).isEqualTo(projectName);
        assertThat(project.getPosition()).isEqualTo(projectPosition);
        jdbcProjectDao.updateProjectName(projectUuid, updatedProjectName);
        jdbcProjectDao.updateProjectPosition(projectUuid, updatedProjectPosition);
        Project updatedProject = jdbcProjectDao.getProjectByID(projectUuid);
        assertThat(updatedProject.getName()).isEqualTo(updatedProjectName);
        assertThat(updatedProject.getPosition()).isEqualTo(updatedProjectPosition);
        jdbcProjectDao.deleteProject(projectUuid);
        assertThat(jdbcProjectDao.getProjectByID(projectUuid)).isEqualTo(null);
        jdbcUserDao.deleteUser(userUuid);
    }

    @Test
    public void testColumnDao() {
        UUID userUuid = jdbcUserDao.createUser("user@email.com", "password");
        UUID projectUuid = jdbcProjectDao.createProject(userUuid, "projectName", 0);
        String columnName = "Test Column";
        String updatedName = "Updated Column";
        int columnPosition = 0;
        int updatedPosition = 5;
        UUID columnUuid = jdbcColumnDao.createColumn(projectUuid, columnName, columnPosition);
        Column column = jdbcColumnDao.getColumnById(columnUuid);
        assertThat(column.getName()).isEqualTo(columnName);
        assertThat(column.getPosition()).isEqualTo(columnPosition);
        jdbcColumnDao.updateColumnName(columnUuid, updatedName);
        jdbcColumnDao.updateColumnPosition(columnUuid, updatedPosition);
        Column updatedColumn = jdbcColumnDao.getColumnById(columnUuid);
        assertThat(updatedColumn.getName()).isEqualTo(updatedName);
        assertThat(updatedColumn.getPosition()).isEqualTo(updatedPosition);
        jdbcColumnDao.deleteColumn(columnUuid);
        assertThat(jdbcColumnDao.getColumnById(columnUuid)).isEqualTo(null);
        jdbcProjectDao.deleteProject(projectUuid);
        jdbcUserDao.deleteUser(userUuid);
    }

    @Test
    public void testTaskListDao() {
        UUID userUuid = jdbcUserDao.createUser("user@email.com", "password");
        UUID projectUuid = jdbcProjectDao.createProject(userUuid, "projectName", 0);
        UUID columnUuid = jdbcColumnDao.createColumn(projectUuid, "columnName", 0);
        String name = "Test Task List";
        String updatedName = "Updated Task List";
        int position = 0;
        int updatedPosition = 5;
        String color = "#e9e5e3";
        String updatedColor = "#9fba88";
        UUID taskListId = jdbcTaskListDao.createTaskList(columnUuid, name, position);
        TaskList taskList = jdbcTaskListDao.getTaskListById(taskListId);
        assertThat(taskList.getName()).isEqualTo(name);
        assertThat(taskList.getPosition()).isEqualTo(position);
        assertThat(taskList.getColor()).isEqualTo(color);
        jdbcTaskListDao.updateTaskListName(taskListId, updatedName);
        jdbcTaskListDao.updateTaskListPosition(taskListId, updatedPosition);
        jdbcTaskListDao.updateTaskListColor(taskListId, updatedColor);
        TaskList updatedTaskList = jdbcTaskListDao.getTaskListById(taskListId);
        assertThat(updatedTaskList.getName()).isEqualTo(updatedName);
        assertThat(updatedTaskList.getPosition()).isEqualTo(updatedPosition);
        assertThat(updatedTaskList.getColor()).isEqualTo(updatedColor);
        jdbcTaskListDao.deleteTaskList(taskListId);
        assertThat(jdbcTaskListDao.getTaskListById(taskListId)).isEqualTo(null);
        jdbcColumnDao.deleteColumn(columnUuid);
        jdbcProjectDao.deleteProject(projectUuid);
        jdbcUserDao.deleteUser(userUuid);
    }

    @Test
    public void testTaskDao() {
        UUID userUuid = jdbcUserDao.createUser("user@email.com", "password");
        UUID projectUuid = jdbcProjectDao.createProject(userUuid, "Project", 0);
        UUID columnUuid = jdbcColumnDao.createColumn(projectUuid, "Column", 0);
        UUID taskListId = jdbcTaskListDao.createTaskList(columnUuid, "Task List", 0);
        String name = "Task";
        String updatedName = "Updated Task";
        int position = 0;
        int updatedPosition = 5;
        boolean state = false;
        boolean updatedState = true;
        UUID taskId = jdbcTaskDao.createTask(taskListId, name, position);
        Task task = jdbcTaskDao.getTaskById(taskId);
        assertThat(task.getName()).isEqualTo(name);
        assertThat(task.getPosition()).isEqualTo(position);
        assertThat(task.isState()).isEqualTo(state);
        jdbcTaskDao.updateTaskName(taskId, updatedName);
        jdbcTaskDao.updateTaskPosition(taskId, updatedPosition);
        jdbcTaskDao.updateTaskState(taskId, updatedState);
        Task updatedTask = jdbcTaskDao.getTaskById(taskId);
        assertThat(updatedTask.getName()).isEqualTo(updatedName);
        assertThat(updatedTask.getPosition()).isEqualTo(updatedPosition);
        assertThat(updatedTask.isState()).isEqualTo(updatedState);
        jdbcTaskDao.deleteTask(taskId);
        assertThat(jdbcTaskDao.getTaskById(taskId)).isEqualTo(null);
        jdbcTaskListDao.deleteTaskList(taskListId);
        jdbcColumnDao.deleteColumn(columnUuid);
        jdbcProjectDao.deleteProject(projectUuid);
        jdbcUserDao.deleteUser(userUuid);
    }

    @Test
    public void testFullProjectAndSortings() {
        UUID userId = jdbcUserDao.createUser("user@email.com", "password");
        UUID firstProjectId = jdbcProjectDao.createProject(userId, "Project 1", 0);
        UUID secondProjectId = jdbcProjectDao.createProject(userId, "Project 2", 1);
        UUID thirdProjectId = jdbcProjectDao.createProject(userId, "Project 3", 2);

        UUID firstColumnId = jdbcColumnDao.createColumn(firstProjectId, "Column 1", 0);
        UUID firstTaskListId = jdbcTaskListDao.createTaskList(firstColumnId, "Task List 1", 0);
        UUID firstTaskId = jdbcTaskDao.createTask(firstTaskListId, "Task 1", 0);
        UUID secondTaskId = jdbcTaskDao.createTask(firstTaskListId, "Task 2", 1);
        UUID thirdTaskId = jdbcTaskDao.createTask(firstTaskListId, "Task 3", 2);
        UUID secondTaskListId = jdbcTaskListDao.createTaskList(firstColumnId, "Task List 2", 1);
        UUID fourthTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 4", 0);
        UUID fifthTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 5", 1);
        UUID sixthTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 6", 2);
        UUID seventhTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 7", 3);

        UUID secondColumnId = jdbcColumnDao.createColumn(firstProjectId, "Column 2", 1);
        UUID thirdTaskListId = jdbcTaskListDao.createTaskList(secondColumnId, "Task List 3", 2);
        UUID eighthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 8", 0);
        UUID ninthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 9", 1);
        UUID tenthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 10", 2);
        UUID eleventhTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 11", 4);
        UUID twelfthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 12", 5);

        UUID thirdColumnId = jdbcColumnDao.createColumn(firstProjectId, "Column 3", 2);
        UUID fourthTaskListId = jdbcTaskListDao.createTaskList(thirdColumnId, "Task List 4", 3);
        UUID thirteenthTaskId = jdbcTaskDao.createTask(fourthTaskListId, "Task 13", 0);
        UUID fourteenthTaskId = jdbcTaskDao.createTask(fourthTaskListId, "Task 14", 1);
        UUID fifthTaskListId = jdbcTaskListDao.createTaskList(thirdColumnId, "Task List 5", 4);
        UUID fifteenthTaskId = jdbcTaskDao.createTask(fifthTaskListId, "Task 15", 0);
        UUID sixteenthTaskId = jdbcTaskDao.createTask(fifthTaskListId, "Task 16", 1);
        UUID seventeenthTaskId = jdbcTaskDao.createTask(fifthTaskListId, "Task 17", 2);
        UUID eighteenthTaskId = jdbcTaskDao.createTask(fifthTaskListId, "Task 18", 3);
        UUID sixthTaskListId = jdbcTaskListDao.createTaskList(thirdColumnId, "Task List 6", 5);
        UUID nineteenthTaskId = jdbcTaskDao.createTask(sixthTaskListId, "Task 19", 0);
        UUID twentiethTaskId = jdbcTaskDao.createTask(sixthTaskListId, "Task 20", 1);
        UUID twentyFirstTaskId = jdbcTaskDao.createTask(sixthTaskListId, "Task 21", 2);
        UUID twentySecondTaskId = jdbcTaskDao.createTask(sixthTaskListId, "Task 22", 3);

        Project project = jdbcProjectDao.getFullProjectById(firstProjectId);
        assertThat(project.getColumns().get(0).getId()).isEqualTo(firstColumnId);
        assertThat(project.getColumns().get(0).getTaskLists().get(0).getId()).isEqualTo(firstTaskListId);
        assertThat(project.getColumns().get(0).getTaskLists().get(0).getTasks().get(0).getId()).isEqualTo(firstTaskId);
        assertThat(project.getColumns().get(0).getTaskLists().get(0).getTasks().get(1).getId()).isEqualTo(secondTaskId);
        assertThat(project.getColumns().get(0).getTaskLists().get(0).getTasks().get(2).getId()).isEqualTo(thirdTaskId);
        assertThat(project.getColumns().get(0).getTaskLists().get(1).getId()).isEqualTo(secondTaskListId);
        assertThat(project.getColumns().get(0).getTaskLists().get(1).getTasks().get(0).getId()).isEqualTo(fourthTaskId);
        assertThat(project.getColumns().get(0).getTaskLists().get(1).getTasks().get(1).getId()).isEqualTo(fifthTaskId);
        assertThat(project.getColumns().get(0).getTaskLists().get(1).getTasks().get(2).getId()).isEqualTo(sixthTaskId);
        assertThat(project.getColumns().get(0).getTaskLists().get(1).getTasks().get(3).getId()).isEqualTo(seventhTaskId);

        assertThat(project.getColumns().get(1).getId()).isEqualTo(secondColumnId);
        assertThat(project.getColumns().get(1).getTaskLists().get(0).getId()).isEqualTo(thirdTaskListId);
        assertThat(project.getColumns().get(1).getTaskLists().get(0).getTasks().get(0).getId()).isEqualTo(eighthTaskId);
        assertThat(project.getColumns().get(1).getTaskLists().get(0).getTasks().get(1).getId()).isEqualTo(ninthTaskId);
        assertThat(project.getColumns().get(1).getTaskLists().get(0).getTasks().get(2).getId()).isEqualTo(tenthTaskId);
        assertThat(project.getColumns().get(1).getTaskLists().get(0).getTasks().get(3).getId()).isEqualTo(eleventhTaskId);
        assertThat(project.getColumns().get(1).getTaskLists().get(0).getTasks().get(4).getId()).isEqualTo(twelfthTaskId);

        assertThat(project.getColumns().get(2).getId()).isEqualTo(thirdColumnId);
        assertThat(project.getColumns().get(2).getTaskLists().get(0).getId()).isEqualTo(fourthTaskListId);
        assertThat(project.getColumns().get(2).getTaskLists().get(0).getTasks().get(0).getId()).isEqualTo(thirteenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(0).getTasks().get(1).getId()).isEqualTo(fourteenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(1).getId()).isEqualTo(fifthTaskListId);
        assertThat(project.getColumns().get(2).getTaskLists().get(1).getTasks().get(0).getId()).isEqualTo(fifteenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(1).getTasks().get(1).getId()).isEqualTo(sixteenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(1).getTasks().get(2).getId()).isEqualTo(seventeenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(1).getTasks().get(3).getId()).isEqualTo(eighteenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(2).getId()).isEqualTo(sixthTaskListId);
        assertThat(project.getColumns().get(2).getTaskLists().get(2).getTasks().get(0).getId()).isEqualTo(nineteenthTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(2).getTasks().get(1).getId()).isEqualTo(twentiethTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(2).getTasks().get(2).getId()).isEqualTo(twentyFirstTaskId);
        assertThat(project.getColumns().get(2).getTaskLists().get(2).getTasks().get(3).getId()).isEqualTo(twentySecondTaskId);

        List<UUID> sortingProjects = jdbcUserDao.getSortingProjectsByUserId(userId);
        assertThat(sortingProjects.get(0)).isEqualTo(firstProjectId);
        assertThat(sortingProjects.get(1)).isEqualTo(secondProjectId);
        assertThat(sortingProjects.get(2)).isEqualTo(thirdProjectId);

        List<UUID> sortingColumns = jdbcProjectDao.getSortingColumnsByProjectId(firstProjectId);
        assertThat(sortingColumns.get(0)).isEqualTo(firstColumnId);
        assertThat(sortingColumns.get(1)).isEqualTo(secondColumnId);
        assertThat(sortingColumns.get(2)).isEqualTo(thirdColumnId);

        List<UUID> sortingTaskLists = jdbcColumnDao.getSortingTaskListsByColumnId(firstColumnId);
        assertThat(sortingTaskLists.get(0)).isEqualTo(firstTaskListId);
        assertThat(sortingTaskLists.get(1)).isEqualTo(secondTaskListId);

        List<UUID> sortingTasks = jdbcTaskListDao.getSortingTasksByTaskListId(firstTaskListId);
        assertThat(sortingTasks.get(0)).isEqualTo(firstTaskId);
        assertThat(sortingTasks.get(1)).isEqualTo(secondTaskId);
        assertThat(sortingTasks.get(2)).isEqualTo(thirdTaskId);

        jdbcTaskDao.deleteTask(firstTaskId);
        jdbcTaskDao.deleteTask(secondTaskId);
        jdbcTaskDao.deleteTask(thirdTaskId);
        jdbcTaskDao.deleteTask(fourthTaskId);
        jdbcTaskDao.deleteTask(fifthTaskId);
        jdbcTaskDao.deleteTask(sixthTaskId);
        jdbcTaskDao.deleteTask(seventhTaskId);
        jdbcTaskDao.deleteTask(eighthTaskId);
        jdbcTaskDao.deleteTask(ninthTaskId);
        jdbcTaskDao.deleteTask(tenthTaskId);
        jdbcTaskDao.deleteTask(eleventhTaskId);
        jdbcTaskDao.deleteTask(twelfthTaskId);
        jdbcTaskDao.deleteTask(thirteenthTaskId);
        jdbcTaskDao.deleteTask(fourteenthTaskId);
        jdbcTaskDao.deleteTask(fifteenthTaskId);
        jdbcTaskDao.deleteTask(sixteenthTaskId);
        jdbcTaskDao.deleteTask(seventeenthTaskId);
        jdbcTaskDao.deleteTask(eighteenthTaskId);
        jdbcTaskDao.deleteTask(nineteenthTaskId);
        jdbcTaskDao.deleteTask(twentiethTaskId);
        jdbcTaskDao.deleteTask(twentyFirstTaskId);
        jdbcTaskDao.deleteTask(twentySecondTaskId);
        jdbcTaskListDao.deleteTaskList(firstTaskListId);
        jdbcTaskListDao.deleteTaskList(secondTaskListId);
        jdbcTaskListDao.deleteTaskList(thirdTaskListId);
        jdbcTaskListDao.deleteTaskList(fourthTaskListId);
        jdbcTaskListDao.deleteTaskList(fifthTaskListId);
        jdbcTaskListDao.deleteTaskList(sixthTaskListId);
        jdbcColumnDao.deleteColumn(firstColumnId);
        jdbcColumnDao.deleteColumn(secondColumnId);
        jdbcColumnDao.deleteColumn(thirdColumnId);
        jdbcProjectDao.deleteProject(firstProjectId);
        jdbcProjectDao.deleteProject(secondProjectId);
        jdbcProjectDao.deleteProject(thirdProjectId);
        jdbcUserDao.deleteUser(userId);
    }
}
