package org.attractor.microgram.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String savePostImage(MultipartFile file, Long userId);

    ResponseEntity<?> findByName(String fileName, String f);

    void uploadImage(org.springframework.security.core.userdetails.User principal, MultipartFile file);
}