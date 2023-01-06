package ru.taskmanger.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserDao {
    UUID createUser(String email, String password);
    void updateEmail(UUID userId, String email);
    void updatePassword(UUID userId, String password);
    void updateUserData(UUID userId, String firstname, String lastname, LocalDate dateOfBirth, String sex);
    void updateImage(UUID userId, String imageUrl);
    void deleteUser(UUID userId);
    List<UUID> getSortingProjectsByUserId(UUID userId);
}