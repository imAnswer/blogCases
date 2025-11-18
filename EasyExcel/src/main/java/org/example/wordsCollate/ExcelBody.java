package org.example.wordsCollate;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liushaoya
 * @since 2025-01-08 13:20
 */
@Getter
@Setter
@EqualsAndHashCode
public class ExcelBody {

    /**
     * 第一行
     */
    @ColumnWidth(20)
    private WriteCellData<String> firstLine;

    /**
     * 第二行
     */
    @ColumnWidth(20)
    private WriteCellData<String> secondLine;

    /**
     * 第三行
     */
    @ColumnWidth(20)
    private WriteCellData<String> ThirdLine;

    /**
     * 第四行
     */
    @ColumnWidth(20)
    private WriteCellData<String> forthLine;

}
