package org.example.elasticsearch.EsAndDB.impl;

import org.example.elasticsearch.EsAndDB.CollectionService;
import org.example.elasticsearch.documentAnnotation.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liushaoya
 * @since 2025-10-27 17:54
 */
@Service
@ConditionalOnProperty(name = "spring.elasticsearch.enable", havingValue = "true")
public class CollectionEsServiceImpl implements CollectionService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Product> search() {
        Criteria criteria = new Criteria();
        Query query = new CriteriaQuery(criteria);
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);
        List<SearchHit<Product>> searchHitList = searchHits.getSearchHits();
        return searchHitList.stream().map(SearchHit::getContent).collect(Collectors.toList());

    }
}
