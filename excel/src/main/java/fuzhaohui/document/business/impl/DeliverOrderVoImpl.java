package fuzhaohui.document.business.impl;

import com.alibaba.fastjson.JSON;
import fuzhaohui.document.business.model.DeliverOrderVO;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月1日18:38:27$ $
 */
public class DeliverOrderVoImpl {

    public static  List<DeliverOrderVO> getDeliverBaseVo() {

        List<DeliverOrderVO> list = new ArrayList<>();

        File directory = new File("");//设定为当前文件夹
        try
        {
            OPCPackage pkg= OPCPackage.open(directory.getCanonicalPath().toString()+"/source/excel/配送下单规则数据.xlsx");
            XSSFWorkbook excel=new XSSFWorkbook(pkg);
            //获取第一个sheet
            XSSFSheet sheet0  = excel.getSheetAt(1);
            int rowNum = sheet0.getLastRowNum()+1;//找到总共几行
            System.out.println("总共行："+rowNum);
            //获取总共几列

            //遍历行数
            for (int i=1;i<rowNum;i++)
            {
                XSSFRow row= row = sheet0.getRow(i);;
                DeliverOrderVO deliverOrderVO = new DeliverOrderVO();
                //遍历列数
                deliverOrderVO.setMerchantName(row.getCell(0).getStringCellValue());
                deliverOrderVO.setShopCode(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
                deliverOrderVO.setRuleName(row.getCell(2).getStringCellValue());
                deliverOrderVO.setLongestStartTime(new SimpleDateFormat("HH:mm").format(row.getCell(3).getDateCellValue()));
                deliverOrderVO.setLongestEndTime(new SimpleDateFormat("HH:mm").format(row.getCell(4).getDateCellValue()));
                deliverOrderVO.setLongestDay(Double.valueOf(row.getCell(5).getNumericCellValue()).intValue());
                deliverOrderVO.setDeliverRuleModelStartCreateOrderTime(new SimpleDateFormat("HH:mm").format(row.getCell(6).getDateCellValue()));
                deliverOrderVO.setDeliverRuleModelEndCreateOrderTime(new SimpleDateFormat("HH:mm").format(row.getCell(7).getDateCellValue()));
                deliverOrderVO.setModel(row.getCell(8).getStringCellValue());
                list.add(deliverOrderVO);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //log.warn(e);
        }
        System.out.println("有效行："+list.size());
        return list;

    }

    public static void main(String[] args) {
        getDeliverBaseVo();
    }



}
