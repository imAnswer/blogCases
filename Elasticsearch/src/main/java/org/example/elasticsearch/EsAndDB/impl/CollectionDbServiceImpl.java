package org.example.elasticsearch.EsAndDB.impl;


import org.example.elasticsearch.EsAndDB.CollectionService;
import org.example.elasticsearch.EsAndDB.mapper.DbMapper;
import org.example.elasticsearch.documentAnnotation.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liushaoya
 * @since 2025-10-27 17:54
 */
@Service
@ConditionalOnProperty(name = "spring.elasticsearch.enable", havingValue = "false", matchIfMissing = true)
public class CollectionDbServiceImpl implements CollectionService {

    @Autowired
    private DbMapper dbMapper;

    @Override
    public List<Product> search() {
        return dbMapper.selectList(null);
    }
}
