package org.example.elasticsearch.documentAnnotation;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * @author liushaoya
 * @since 2025-10-25 17:23
 */
@Data
@Document(
        indexName = "product",      // 绑定到 ES 索引 product
        createIndex = true,         // 启动时若不存在则尝试创建
        shards = 1,                 // 演示用 1 分片
        replicas = 0,               // 开发用 0 副本
        versionType = Document.VersionType.INTERNAL
)
public class Product {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String sku;

    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String name;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private Instant createdAt;
}
