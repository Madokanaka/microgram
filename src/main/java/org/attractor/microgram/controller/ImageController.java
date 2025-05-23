package org.attractor.microgram.controller;

import org.attractor.microgram.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping({"/images/{imageName}"})
    public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) {
        return imageService.findByName(imageName, "images/");
    }

    @GetMapping({"/posts/{imageName}"})
    public ResponseEntity<?> getImageForPost(@PathVariable("imageName") String imageName) {
        var im = imageService.findByName(imageName, "posts/");
        return imageService.findByName(imageName, "posts/");
    }


}
