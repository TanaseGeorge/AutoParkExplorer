package api;

import exceptions.NotFoundException;
import handlers.Response;
import models.File;

import java.math.BigDecimal;
import java.util.List;

public interface FileApi {

    List<File> getFilesByUserEmail (String email);

    List<File> getFiles() throws NotFoundException;

    File getFile(int id) throws NotFoundException;

    Response createFile(File file);

    Response listFile(int id, BigDecimal price) throws NotFoundException;

    Response sellFile(int id, String sellerMail) throws NotFoundException;

    Response updateFile(File file) throws NotFoundException;

    Response deleteFile(int id) throws NotFoundException;
    
}
