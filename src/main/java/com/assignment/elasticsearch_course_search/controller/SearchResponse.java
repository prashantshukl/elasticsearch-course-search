package com.assignment.elasticsearch_course_search.controller;


import com.assignment.elasticsearch_course_search.document.CourseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponse {
    private long total;
    private List<CourseDocument> courses;
}