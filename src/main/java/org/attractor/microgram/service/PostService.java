package org.attractor.microgram.service;

import org.attractor.microgram.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getPostsByUserId(Long userId);
}
