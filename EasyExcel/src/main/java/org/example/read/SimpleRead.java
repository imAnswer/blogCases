package org.example.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.example.pojo.DemoDTO;

/**
 * @author liushaoya
 * @since 2024-12-25 20:11
 */
public class SimpleRead {
    public void read() {
        String fileName = "C:\\Users\\85789\\Desktop\\a.xlsx";
        EasyExcel.read(fileName, DemoDTO.class, new PageReadListener<DemoDTO>(dataList -> {
            for(DemoDTO demoDTO : dataList){
                System.out.println(demoDTO);
            }
        })).sheet().doRead();
    }
}
