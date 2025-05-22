package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.model.User;
import org.attractor.microgram.repository.UserRepository;
import org.attractor.microgram.service.ImageService;
import org.attractor.microgram.util.FileUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    @Override
    public String savePostImage(MultipartFile file, Long userId) {
        log.info("Saving post image for userId={}", userId);
        String fileName = fileUtil.saveUploadFile(file, "posts/");
        log.debug("Post image saved with fileName={}", fileName);
        return fileName;
    }

    @Override
    public String saveAvatar(MultipartFile file, Long userId) {
        log.info("Saving avatar for userId={}", userId);
        String fileName = fileUtil.saveUploadFile(file, "avatars/");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        user.setAvatar(fileName);
        userRepository.save(user);
        log.debug("Avatar saved with fileName={}", fileName);
        return fileName;
    }

    @Override
    public ResponseEntity<?> getImage(String fileName) {
        log.info("Fetching image by fileName={}", fileName);
        return fileUtil.getOutputFile(fileName, "posts/", MediaType.IMAGE_JPEG);
    }
}