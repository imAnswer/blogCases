package org.example.elasticsearch.controller;

import org.example.elasticsearch.EsAndDB.CollectionService;
import org.example.elasticsearch.documentAnnotation.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-27 17:57
 */
@RestController
public class EsAndDBController {
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/test")
    public List<Product> search() {
        return collectionService.search();
    }
}
