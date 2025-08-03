package com.assignment.elasticsearch_course_search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.assignment.elasticsearch_course_search.document.CourseDocument;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {

   
}
