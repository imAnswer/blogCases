package org.example.elasticsearch.controller;

import org.example.elasticsearch.criteria.CriteriaService;
import org.example.elasticsearch.documentAnnotation.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-27 16:00
 */
@RestController
public class CriteriaController {
    @Autowired
    private CriteriaService criteriaService;

    @GetMapping("/criteria")
    public List<SearchHit<Product>> criteria() {
        return criteriaService.search();
    }
}
