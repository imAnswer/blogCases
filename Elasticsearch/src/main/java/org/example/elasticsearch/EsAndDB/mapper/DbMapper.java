package org.example.elasticsearch.EsAndDB.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.elasticsearch.documentAnnotation.Product;

/**
 * @author liushaoya
 * @since 2025-10-28 22:10
 */
@Mapper
public interface DbMapper extends BaseMapper<Product> {
}
