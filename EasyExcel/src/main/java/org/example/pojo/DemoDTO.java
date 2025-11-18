package org.example.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liushaoya
 * @since 2024-12-25 19:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DemoDTO {
    @ExcelProperty("第一列")
    private String firstColumn;
    @ExcelProperty("第二列")
    private String secondColumn;
    @ExcelProperty("第三列")
    private String thirdColumn;
    @ExcelProperty("第四列")
    private String fourthColumn;
}
