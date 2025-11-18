package org.example.elasticsearch.documentAnnotation;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-27 11:17
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByBrand(String brand);

}
