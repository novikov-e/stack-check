package ru.taskmanger.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.taskmanger.repository.jdbc.*;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class DatabaseInitializer {

	private Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

	private final JdbcUserDao jdbcUserDao;
	private final JdbcProjectDao jdbcProjectDao;
	private final JdbcColumnDao jdbcColumnDao;
	private final JdbcTaskListDao jdbcTaskListDao;
	private final JdbcTaskDao jdbcTaskDao;

	public DatabaseInitializer(JdbcUserDao jdbcUserDao, JdbcProjectDao jdbcProjectDao, JdbcColumnDao jdbcColumnDao, JdbcTaskListDao jdbcTaskListDao, JdbcTaskDao jdbcTaskDao) {
		this.jdbcUserDao = jdbcUserDao;
		this.jdbcProjectDao = jdbcProjectDao;
		this.jdbcColumnDao = jdbcColumnDao;
		this.jdbcTaskListDao = jdbcTaskListDao;
		this.jdbcTaskDao = jdbcTaskDao;
	}

	@PostConstruct
	public void initDB(){
		logger.info("Starting database initialization...");
		UUID userId = jdbcUserDao.createUser("email@email.com", "password");
		jdbcUserDao.updateUserData(userId, "Firstname", "Lastname",
				LocalDate.of(2000, 1, 1), "M");
		jdbcUserDao.updateImage(
				userId,
				"https://rapidapi-prod-apis.s3.amazonaws.com/b42aa17d-8ae0-4a28-b29f-587af5454390.png");
		UUID projectId = jdbcProjectDao.createProject(userId, "Project 1", 0);
		UUID firstColumnId = jdbcColumnDao.createColumn(projectId, "Column 1", 0);
		UUID firstTaskListId = jdbcTaskListDao.createTaskList(firstColumnId, "Task List 1", 0);
		UUID firstTaskId = jdbcTaskDao.createTask(firstTaskListId, "Task 1", 0);
		UUID secondTaskId = jdbcTaskDao.createTask(firstTaskListId, "Task 2", 1);
		UUID thirdTaskId = jdbcTaskDao.createTask(firstTaskListId, "Task 3", 2);
		UUID secondTaskListId = jdbcTaskListDao.createTaskList(firstColumnId, "Task List 2", 1);
		UUID fourthTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 4", 0);
		UUID fifthTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 5", 1);
		UUID sixthTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 6", 2);
		UUID seventhTaskId = jdbcTaskDao.createTask(secondTaskListId, "Task 7", 3);

		UUID secondColumnId = jdbcColumnDao.createColumn(projectId, "Column 2", 1);
		UUID thirdTaskListId = jdbcTaskListDao.createTaskList(secondColumnId, "Task List 3", 2);
		UUID eighthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 8", 0);
		UUID ninthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 9", 1);
		UUID tenthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 10", 2);
		UUID eleventhTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 11", 4);
		UUID twelfthTaskId = jdbcTaskDao.createTask(thirdTaskListId, "Task 12", 5);

		UUID thirdColumnId = jdbcColumnDao.createColumn(projectId, "Column 3", 2);
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
		logger.info("Database initialization finished.");
	}
}