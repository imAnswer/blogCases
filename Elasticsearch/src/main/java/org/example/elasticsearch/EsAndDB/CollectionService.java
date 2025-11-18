package org.example.elasticsearch.EsAndDB;

import org.example.elasticsearch.documentAnnotation.Product;

import java.util.List;

/**
 * 实现Es和DB的动态修改
 * @author liushaoya
 * @since 2025-10-27 17:46
 */
public interface CollectionService {
    List<Product> search();
}
