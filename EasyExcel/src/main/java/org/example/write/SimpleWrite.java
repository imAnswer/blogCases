package org.example.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import org.example.pojo.DemoDTO;

import java.util.List;

/**
 * @author liushaoya
 * @since 2024-12-25 19:49
 */
public class SimpleWrite {
    private List<DemoDTO> data(int count) {
        List<DemoDTO> list = ListUtils.newArrayList();
        for(int i=1; i<=count; i++) {
            list.add(new DemoDTO("a", "b", "c", "d"));
        }
        return list;
    }

    public void write() {
        String fileName = "C:\\Users\\85789\\Desktop\\a.xlsx";
        EasyExcel.write(fileName, DemoDTO.class).sheet("aaa").doWrite(data(10));
    }

}
