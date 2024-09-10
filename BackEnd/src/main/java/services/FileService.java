package services;

import dataprovider.Data;
import enums.FileStatusEnum;
import exceptions.NotFoundException;
import models.File;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public static List<File> findFilesByUserMail (String userMail) {
        String query = "SELECT file_id, name, kind, planting_date, owner_email, status FROM files WHERE owner_email = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            statement.setString(1, userMail);

            ResultSet resultSet = statement.executeQuery();

            List<File> allFiles = new ArrayList<>();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("file_id"));
                file.setName(resultSet.getString("name"));
                file.setKind(resultSet.getString("kind"));
                file.setPlantingDate(resultSet.getDate("planting_date"));
                file.setOwnerEmail(resultSet.getString("owner_email"));
                file.setStatus(resultSet.getString("status"));
                allFiles.add(file);
            }

            return allFiles;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static File findFileById(int id) {

        String query = "SELECT file_id, name, kind, planting_date, owner_email, status FROM files WHERE file_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("file_id"));
                file.setName(resultSet.getString("name"));
                file.setKind(resultSet.getString("kind"));
                file.setPlantingDate(resultSet.getDate("planting_date"));
                file.setOwnerEmail(resultSet.getString("owner_email"));
                file.setStatus(resultSet.getString("status"));
                return file;
            } else {
                return null; // File with the specified ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<File> getAllFiles () throws NotFoundException {
        String query = "SELECT file_id, name, kind, planting_date, owner_email, status FROM files";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            List<File> allFiles = new ArrayList<>();

            while (resultSet.next()) {
                File file = new File();
                file.setId(resultSet.getInt("file_id"));
                file.setName(resultSet.getString("name"));
                file.setKind(resultSet.getString("kind"));
                file.setPlantingDate(resultSet.getDate("planting_date"));
                file.setOwnerEmail(resultSet.getString("owner_email"));
                file.setStatus(resultSet.getString("status"));
                allFiles.add(file);
            }

            if (allFiles.isEmpty()) {
                throw new NotFoundException("Cannot find files!");
            }

            return allFiles;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static void saveFile(File file) {

        String query = "INSERT INTO files (name, kind, planting_date, owner_email, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            
            statement.setString(1, file.getName());
            statement.setString(2, file.getKind());
            statement.setDate(3, new Date(file.getPlantingDate().getTime()));
            statement.setString(4, file.getOwnerEmail());
            statement.setString(5, FileStatusEnum.CULTIVATION_PROCESS.name().toLowerCase());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateFile(File file) {

        String query = "UPDATE files SET name = ?, kind = ?, planting_date = ?, owner_email = ?, status = ? WHERE file_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, file.getName());
            statement.setString(2, file.getKind());
            statement.setDate(3, new Date(file.getPlantingDate().getTime()));
            statement.setString(4, file.getOwnerEmail());
            statement.setString(5, file.getStatus());
            statement.setInt(6, file.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteFile(File file) {

        String query = "DELETE FROM files WHERE file_id = ?";

        try (PreparedStatement statement = Data.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, file.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listFile (int id, BigDecimal price) throws NotFoundException {
        File file = findFileById(id);

        if (file == null) {
            throw new NotFoundException(String.format("File with id: %d not found", id));
        }

        file.setStatus(FileStatusEnum.LISTED.name().toLowerCase());

        updateFile(file);
    }

    public static void sellFile (int id, String sellerMail) throws NotFoundException {
        File file = findFileById(id);

        if (file == null) {
            throw new NotFoundException(String.format("File with id: %d not found", id));
        }

        file.setStatus(FileStatusEnum.SELLED.name().toLowerCase());

        updateFile(file);
    }

}
