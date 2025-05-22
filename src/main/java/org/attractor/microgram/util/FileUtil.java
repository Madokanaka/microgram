package org.attractor.microgram.util;

import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUtil {
    private static final String UPLOAD_DIR = "data/";

    @SneakyThrows
    public String saveUploadFile(MultipartFile file, String subDir) {
        String uuidFile = java.util.UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get(UPLOAD_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = pathDir.resolve(resultFileName);
        Files.write(filePath, file.getBytes());

        return resultFileName;
    }

    public ResponseEntity<?> getOutputFile(String fileName, String subDir, MediaType mediaType) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + subDir + fileName);
            byte[] fileBytes = Files.readAllBytes(filePath);
            Resource resource = new ByteArrayResource(fileBytes);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }
}