package org.attractor.microgram.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private Long userId;
    private String username;
    private String imageUrl;
    private String description;
    private LocalDateTime createdAt;
    private Long likesCount;
    private Long commentsCount;
}