package org.unstoppable.montao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unstoppable.montao.entity.Community;
import org.unstoppable.montao.service.CommunityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/search")
public class SearchRestController {
    private final CommunityService communityService;

    @Autowired
    public SearchRestController(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Ищет сообщества по полному названию.
     * Потом замутить тут нормальный поиск.
     * @param q тело запроса (query)
     * @return ответ
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity search(String q) {
        Community community = communityService.getByTitle(q);
        List<Community> communities = new ArrayList<>();
        communities.add(community);
        return ResponseEntity.ok(communities);
    }
}
