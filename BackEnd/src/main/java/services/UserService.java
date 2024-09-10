package services;

import dataprovider.Data;
import models.File;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    public static User findUserByEmail(String email) {

        String query = "SELECT email, username, password, role FROM users WHERE email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPasswordHash(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveUser (User user) {
        String query = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getRole());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(String email) {

        List<File> userFiles = FileService.findFilesByUserMail(email);
        for (File file : userFiles) {
            FileService.deleteFile(file);
        }

        String query = "DELETE FROM users WHERE email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
