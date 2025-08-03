package com.assignment.elasticsearch_course_search.service;

import com.assignment.elasticsearch_course_search.document.CourseDocument;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CourseSearchService {

    @Autowired
    private ElasticsearchOperations operations;

    public SearchHits<CourseDocument> search(
            String keyword,
            String category,
            String type,
            Integer minAge,
            Integer maxAge,
            Double minPrice,
            Double maxPrice,
            Instant startDate,
            String sort,
            int page,
            int size
    ) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // Full-text search
        if (keyword != null && !keyword.isEmpty()) {
            MultiMatchQueryBuilder multiMatch = QueryBuilders.multiMatchQuery(keyword)
                    .field("title")
                    .field("description");
            boolQuery.must(multiMatch);
        }

        // Filters
        if (category != null && !category.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("category.keyword", category));
        }

        if (type != null && !type.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery("type.keyword", type));
        }

        if (minAge != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("minAge").gte(minAge));
        }

        if (maxAge != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("maxAge").lte(maxAge));
        }

        if (minPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice));
        }

        if (maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("price").lte(maxPrice));
        }

        if (startDate != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("nextSessionDate").gte(startDate.toString()));
        }

        // Sorting
        SortOrder sortOrder = SortOrder.ASC;
        String sortField = "nextSessionDate";
        if ("priceAsc".equalsIgnoreCase(sort)) {
            sortField = "price";
            sortOrder = SortOrder.ASC;
        } else if ("priceDesc".equalsIgnoreCase(sort)) {
            sortField = "price";
            sortOrder = SortOrder.DESC;
        }

        Pageable pageable = PageRequest.of(page, size);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withSorts(SortBuilders.fieldSort(sortField).order(sortOrder))
                .withPageable(pageable)
                .build();

        return operations.search(query, CourseDocument.class);
    }
}
