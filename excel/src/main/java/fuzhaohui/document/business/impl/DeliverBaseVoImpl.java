package fuzhaohui.document.business.impl;

import com.alibaba.fastjson.JSON;
import fuzhaohui.document.business.model.DeliverBaseVO;
import fuzhaohui.document.business.model.DeliverOrderVO;
import fuzhaohui.document.business.model.PoiDto;
import net.sf.json.JSONArray;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月1日18:38:27$ $
 */
public class DeliverBaseVoImpl {

    public static  List<DeliverBaseVO> getDeliverBaseVo() {
        List<DeliverBaseVO> aBDeliverBaseVO = ABDeliver.getABDeliverBaseVo();
        Map<String,DeliverBaseVO> deliverBaseVOMap = new HashMap<>();

        for(DeliverBaseVO deliverBaseVO : aBDeliverBaseVO){
            deliverBaseVOMap.put(deliverBaseVO.getShopCode()+deliverBaseVO.getModel(),deliverBaseVO);
        }



        List<DeliverBaseVO> list = new ArrayList<>();
        File directory = new File("");//设定为当前文件夹
        try
        {
            OPCPackage pkg= OPCPackage.open(directory.getCanonicalPath().toString()+"/source/excel/配送基础规则数据表.xlsx");
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
                DeliverBaseVO deliverBaseVO = new DeliverBaseVO();
                //遍历列数
                deliverBaseVO.setMerchantName(row.getCell(0).getStringCellValue());
                deliverBaseVO.setShopCode(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
                deliverBaseVO.setRuleName(row.getCell(2).getStringCellValue());
                deliverBaseVO.setModel(row.getCell(3).getStringCellValue());
                deliverBaseVO.setDeliverScope(row.getCell(4).getStringCellValue());
                deliverBaseVO.setReserveDay(String.valueOf(row.getCell(5).getNumericCellValue()));
                deliverBaseVO.setDeliverStartTime(new SimpleDateFormat("HH:mm").format(row.getCell(6).getDateCellValue()));
                deliverBaseVO.setDeliverEndTime(new SimpleDateFormat("HH:mm").format(row.getCell(7).getDateCellValue()));
                deliverBaseVO.setWeekMax(String.valueOf(row.getCell(8).getNumericCellValue()));
                deliverBaseVO.setWeekendMax(String.valueOf(row.getCell(9).getNumericCellValue()));
                deliverBaseVO.setFee(String.valueOf(row.getCell(10).getNumericCellValue()));
                //
                DeliverBaseVO aBDbeliverBaseVO  = deliverBaseVOMap.get(deliverBaseVO.getShopCode()+deliverBaseVO.getModel());
                if(aBDbeliverBaseVO != null){
                    deliverBaseVO.setPoi(aBDbeliverBaseVO.getPoi());
                }
                list.add(deliverBaseVO);
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
