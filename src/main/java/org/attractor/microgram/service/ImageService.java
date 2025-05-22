package org.attractor.microgram.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String savePostImage(MultipartFile file, Long userId);
    String saveAvatar(MultipartFile file, Long userId);
    ResponseEntity<?> getImage(String fileName);
}