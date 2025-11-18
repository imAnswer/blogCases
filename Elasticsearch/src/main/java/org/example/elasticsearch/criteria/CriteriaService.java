package org.example.elasticsearch.criteria;

import org.example.elasticsearch.documentAnnotation.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-27 15:21
 */
@Service
public class CriteriaService {

    @Autowired
    private ElasticsearchOperations operations;


    public List<SearchHit<Product>> search() {
        // 1) 组合条件：
        // brand ∈ {ACME, NIKE}
        // AND price ∈ [300, 1200]
        // AND ( name 包含 "跑鞋"  OR  name 以 "轻量" 开头 )
        // AND sku ∉ {SKU-100, SKU-101}
        // AND createdAt >= 现在-30天
        Criteria c = Criteria.where("brand").in("ACME", "NIKE")
                .and("price").between(300, 1200)
                .and(Criteria.where("name").contains("跑鞋")
                        .or("name").startsWith("轻量")
                )
                .and("sku").notIn("SKU-100", "SKU-101")
                .and("createdAt").greaterThanEqual(Instant.now().minus(30, ChronoUnit.DAYS));

        /**
         * 第二种写法
         */
//        Criteria b = new Criteria()
//                .and(new Criteria("brand").in("ACME", "NIKE"))
//                .and(new Criteria("price").between(300, 1200))
//                .and(new Criteria("name").contains("跑鞋").or("name").startsWith("轻量"))
//                .and(new Criteria("sku").notIn("SKU-100", "SKU-101"))
//                .and(new Criteria("createdAt").greaterThanEqual(Instant.now().minus(30, ChronoUnit.DAYS)));
        CriteriaQuery query = new CriteriaQuery(c);
        query.setPageable(PageRequest.of(0, 10, Sort.by(
                Sort.Order.desc("price"),
                Sort.Order.desc("createdAt")
        )));
        query.addSourceFilter(new FetchSourceFilter(
                new String[]{"sku", "name", "price", "brand", "createdAt"}, // include
                new String[]{} // exclude
        ));

        // 3) 执行
        SearchHits<Product> hits = operations.search(query, Product.class);
        return hits.getSearchHits();
    }

}
