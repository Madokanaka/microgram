package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.service.ImageService;
import org.attractor.microgram.service.UserService;
import org.attractor.microgram.util.FileUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final FileUtil fileUtil;
    private final UserService userService;

    @Override
    public String savePostImage(MultipartFile file, Long userId) {
        log.info("Saving post image for userId={}", userId);
        String fileName = fileUtil.saveUploadFile(file, "posts/");
        log.debug("Post image saved with fileName={}", fileName);
        return fileName;
    }

    @Override
    public ResponseEntity<?> findByName(String fileName, String directory) {
        log.info("Fetching image by fileName={}", fileName);
        return fileUtil.getOutputFile(fileName, directory, MediaType.IMAGE_JPEG);
    }

    @Override
    public void uploadImage(org.springframework.security.core.userdetails.User principal, MultipartFile file) {
        Long userId = userService.getUserByName(principal.getUsername()).getId();
        log.info("Saving image for userId={}", userId);

        String fileName = fileUtil.saveUploadFile(file, "images/");

        userService.saveAvatar(userId, fileName);

        log.debug("Image saved with fileName={}", fileName);
    }
}