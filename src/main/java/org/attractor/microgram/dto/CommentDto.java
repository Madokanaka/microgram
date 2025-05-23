package org.attractor.microgram.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long postId;
    private String username;
    private String text;
    private LocalDateTime createdAt;
    private String avatar;
}