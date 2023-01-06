package ru.taskmanger.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.taskmanger.model.User;
import ru.taskmanger.repository.UserDao;
import ru.taskmanger.repository.jdbc.mapper.UUIDMapper;
import ru.taskmanger.repository.jdbc.mapper.UserMapper;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository("userDao")
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID createUser(String email, String password) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users (email, password) values (?, ?);", new String[] { "user_id" });
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            return preparedStatement;
        }, keyHolder);
        return (UUID) keyHolder.getKeys().get("user_id");
    }

    public User getUserById(UUID userId) {
        return jdbcTemplate.query("select * from users where user_id = ?;",
                new Object[]{userId},
                new UserMapper()).stream().findAny().orElse(null);
    }

    @Override
    public void updateEmail(UUID userId, String email) {
        jdbcTemplate.update("update users set email = ? where user_id = ?;", email, userId);
    }

    @Override
    public void updatePassword(UUID userId, String password) {
        jdbcTemplate.update("update users set password = ? where user_id = ?;", password, userId);
    }

    @Override
    public void updateUserData(UUID userId, String firstname, String lastname, LocalDate bithday, String sex) {
        jdbcTemplate.update(
                        "update users set " +
                        "firstname = ?, " +
                        "lastname = ?, " +
                        "date_of_birth = ?, " +
                        "sex = ? " +
                        "where user_id = ?;",
                firstname, lastname, bithday, sex, userId);
    }

    @Override
    public void updateImage(UUID userId, String imageUrl) {
        jdbcTemplate.update("update users set image = ? where user_id = ?;", imageUrl, userId);
    }

    @Override
    public void deleteUser(UUID userId) {
        jdbcTemplate.update("delete from users where user_id = ?;", userId);
    }

    @Override
    public List<UUID> getSortingProjectsByUserId(UUID userId) {
        return jdbcTemplate.query("select project_id from projects " +
                        "where user_id = ? order by project_position;",
                new Object[]{userId}, new UUIDMapper());
    }
}