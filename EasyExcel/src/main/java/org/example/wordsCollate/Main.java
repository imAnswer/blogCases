package org.example.wordsCollate;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author liushaoya
 * @since 2024-12-26 16:18
 */
public class Main {

    private static final String COLLATEFILENAME = "C:\\Users\\85789\\Desktop\\语料库默写.xlsx";

    private static final String RECITEFILENAME = "C:\\Users\\85789\\Desktop\\test.xlsx";

    private static final String COLLATE = "3-2";

    private static final String RECITE = "Sheet1";

    /**
     * 注意：
     * 如果不创建对象而要使用富文本格式，则必须自己定义转换器
     * 如果创建了对象，则不需要自己定义转换器
     * @param args
     */
    public static void main(String[] args) {

        int wrongNumber = 0;

        int totalNumber = 0;

        BigDecimal correctRate = new BigDecimal(0);

        //获取默写数据
        List<Map<Integer, String>> reciteList = EasyExcel.read(RECITEFILENAME).sheet(RECITE).headRowNumber(1).doReadSync();
        //获取正确答案数据
        List<Map<Integer, String>> collateList = EasyExcel.read(COLLATEFILENAME).sheet(COLLATE).headRowNumber(0).doReadSync();

        //核对结果
        List<ExcelBody> resultList = Lists.newArrayList();

        //设置红色颜色样式
        WriteFont writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.RED.getIndex());

        for(int i=0; i<reciteList.size(); i++) {
            ExcelBody excelBody = new ExcelBody();
            Map<Integer, String> reciteMap = reciteList.get(i);
            Map<Integer, String> collateMap = collateList.get(i);
            for(int j=0; j<reciteMap.size(); j++) {
                totalNumber++;
                if (reciteMap.get(j) == null || collateMap.get(j) == null) {
                    continue;
                }
                WriteCellData<String> writeCellData = new WriteCellData<>();
                writeCellData.setType(CellDataTypeEnum.RICH_TEXT_STRING);
                RichTextStringData richTextStringData = new RichTextStringData();
                writeCellData.setRichTextStringDataValue(richTextStringData);
                richTextStringData.setTextString(reciteMap.get(j));
                if(!reciteMap.get(j).equals(collateMap.get(j))){
                    wrongNumber++;
                    richTextStringData.applyFont(writeFont);
                }

                if(j==0) {
                    excelBody.setFirstLine(writeCellData);
                } else if(j==1) {
                    excelBody.setSecondLine(writeCellData);
                } else if (j==2) {
                    excelBody.setThirdLine(writeCellData);
                } else if (j==3) {
                    excelBody.setForthLine(writeCellData);
                }
            }
            resultList.add(excelBody);

        }

        //一定要写inMemory(true)，否则自定义的格式不会显示
        EasyExcel.write(RECITEFILENAME, ExcelBody.class).inMemory(true).sheet("Sheet1").doWrite(resultList);
        System.out.println("总共" + totalNumber + "单词，错了" +wrongNumber + "个，正确了" + (totalNumber - wrongNumber) + "个");
        System.out.println("正确率为" + new BigDecimal(totalNumber - wrongNumber).divide(new BigDecimal(totalNumber), 2, BigDecimal.ROUND_HALF_UP));

    }


}


