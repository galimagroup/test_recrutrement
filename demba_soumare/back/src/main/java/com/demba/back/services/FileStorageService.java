package com.demba.back.services;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

import jakarta.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

  @Value("${application.file.uploads.files-root}")
  private String fileUploadPath;

  public String saveProductImage(@Nonnull MultipartFile sourceFile) {
    final String fileUploadSubPath = "products";
    return uploadFile(sourceFile, fileUploadSubPath);
  }

  private String uploadFile(
    @Nonnull MultipartFile sourceFile,
    @Nonnull String fileUploadSubPath
  ) {
    final String finalUploadPath =
      fileUploadPath + separator + fileUploadSubPath;
    File targetFolder = new File(finalUploadPath);

    // System.out.println("<<<<<<<<<<<<" + finalUploadPath + ">>>>>>>>>>>>>");

    if (!targetFolder.exists()) {
      boolean folderCreated = targetFolder.mkdirs();
      if (!folderCreated) {
        log.warn("Failed to create the target folder: " + targetFolder);
        return null;
      }
    }
    final String fileExtension = getFileExtension(
      sourceFile.getOriginalFilename()
    );
    String targetFilePath =
      finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
    Path targetPath = Paths.get(targetFilePath);
    try {
      Files.write(targetPath, sourceFile.getBytes());
      log.info("File saved to: " + targetFilePath);
      return targetFilePath;
    } catch (IOException e) {
      log.error("File was not saved", e);
    }
    return null;
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return "";
    }
    int lastDotIndex = fileName.lastIndexOf(".");
    if (lastDotIndex == -1) {
      return "";
    }
    return fileName.substring(lastDotIndex + 1).toLowerCase();
  }

  public MediaType getMediaType(String fileName){
    String fileExtention = getFileExtension(fileName);
    switch (fileExtention) {
      case "png":
        return MediaType.IMAGE_PNG;
      case "jpeg":
        return MediaType.IMAGE_JPEG;
      case "jpg":
        return MediaType.IMAGE_JPEG;
      case "gif":
        return MediaType.IMAGE_GIF;
      case "pdf":
        return MediaType.APPLICATION_PDF;
      default:
        return MediaType.ALL;
    }
  }

  public boolean removeFile(String path) throws IOException{
    File fileToDelete = new File(path.replace("\\", "/"));
    boolean removed = fileToDelete.delete();
    return removed;
  }

  public Path getRealPath(String url){
    Path path = Paths.get(url.replace(separator, "/"));
    return path;
  }

}
