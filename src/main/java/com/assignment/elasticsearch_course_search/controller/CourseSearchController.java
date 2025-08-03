package com.assignment.elasticsearch_course_search.controller;


import com.assignment.elasticsearch_course_search.document.CourseDocument;
import com.assignment.elasticsearch_course_search.service.CourseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class CourseSearchController {

    @Autowired
    private CourseSearchService searchService;

    @GetMapping
    public SearchResponse search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String startDate,
            @RequestParam(defaultValue = "upcoming") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Instant parsedDate = (startDate != null) ? Instant.parse(startDate) : null;

        SearchHits<CourseDocument> hits = searchService.search(
                q, category, type, minAge, maxAge, minPrice, maxPrice, parsedDate, sort, page, size
        );

        List<CourseDocument> courses = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new SearchResponse(hits.getTotalHits(), courses);
    }
}