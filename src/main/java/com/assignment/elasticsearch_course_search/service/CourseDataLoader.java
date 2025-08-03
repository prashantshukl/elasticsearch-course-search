package com.assignment.elasticsearch_course_search.service;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.assignment.elasticsearch_course_search.document.CourseDocument;
import com.assignment.elasticsearch_course_search.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class CourseDataLoader {

    private final CourseRepository courseRepository;

    public CourseDataLoader(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("sample-courses.json");

            List<CourseDocument> courses = mapper.readValue(inputStream, new TypeReference<>() {});
            courseRepository.saveAll(courses);

            System.out.println("✅ Sample course data indexed into Elasticsearch.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}
