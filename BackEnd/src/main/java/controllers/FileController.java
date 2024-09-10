package controllers;

import api.FileApi;
import exceptions.NotFoundException;
import handlers.Response;
import models.File;
import services.FileService;

import java.math.BigDecimal;
import java.util.List;

public class FileController implements FileApi {

    @Override
    public List<File> getFilesByUserEmail(String email) {
        return FileService.findFilesByUserMail(email);
    }

    @Override
    public List<File> getFiles() throws NotFoundException {
        return FileService.getAllFiles();
    }

    @Override
    public File getFile(int id) throws NotFoundException {
        // Retrieve file from the database based on the provided ID
        File file = FileService.findFileById(id);
        if (file == null) {
            throw new NotFoundException("File not found");
        }
        return file;
    }

    @Override
    public Response createFile(File file) {
        // Save the new file in the database
        FileService.saveFile(file);
        return Response.created();
    }

    @Override
    public Response listFile(int id, BigDecimal price) throws NotFoundException {
        FileService.listFile(id, price);
        return Response.ok();
    }

    @Override
    public Response sellFile(int id, String sellerMail) throws NotFoundException {
        FileService.sellFile(id, sellerMail);
        return Response.ok();
    }

    @Override
    public Response updateFile(File file) throws NotFoundException {
        // Retrieve the existing file from the database based on the provided ID
        File existingFile = FileService.findFileById(file.getId());
        if (existingFile == null) {
            throw new NotFoundException("File not found");
        }
        // Update the file properties
        existingFile.setName(file.getName());
        existingFile.setOwnerEmail(file.getOwnerEmail());
        existingFile.setPlantingDate(file.getPlantingDate());
        existingFile.setKind(file.getKind());

        // Save the updated file in the database
        FileService.updateFile(existingFile);
        return Response.ok();
    }

    @Override
    public Response deleteFile(int id) throws NotFoundException {
        // Retrieve the file from the database based on the provided ID
        File file = FileService.findFileById(id);
        if (file == null) {
            throw new NotFoundException("File not found");
        }
        // Delete the file from the database
        FileService.deleteFile(file);
        return Response.ok();
    }
}
