package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.SensitiveTest;

/**
 * @author liushaoya
 * @since 2025-10-15 9:32
 */
@Mapper
public interface SensitiveMapper extends BaseMapper<SensitiveTest> {
}
