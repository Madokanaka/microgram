package org.attractor.microgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final UserService userService;

    @GetMapping("/search")
    public String showSearchForm(Model model, @RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.trim().isEmpty()) {
            log.info("Searching users with query: {}", query);
            model.addAttribute("users", userService.searchUsers(query));
            model.addAttribute("query", query);
        } else {
            model.addAttribute("users", null);
            model.addAttribute("query", "");
        }
        return "search";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<UserDto> searchUsersApi(@RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.trim().isEmpty()) {
            log.info("API search users with query: {}", query);
            return userService.searchUsers(query);
        }
        return List.of();
    }
}