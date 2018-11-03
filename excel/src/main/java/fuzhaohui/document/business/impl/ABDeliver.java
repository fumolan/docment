package fuzhaohui.document.business.impl;

import fuzhaohui.document.business.model.DeliverBaseVO;
import fuzhaohui.document.business.model.PoiDto;
import net.sf.json.JSONArray;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ABDeliver{
        public static  List<DeliverBaseVO> getABDeliverBaseVo() {

            List<DeliverBaseVO> list = new ArrayList<>();

            File directory = new File("");//设定为当前文件夹
            try
            {
                OPCPackage pkg= OPCPackage.open(directory.getCanonicalPath().toString()+"/source/excel/地理围栏信息.xlsx");
                XSSFWorkbook excel=new XSSFWorkbook(pkg);
                //获取第一个sheet
                XSSFSheet sheet0  = excel.getSheetAt(0);
                int rowNum = sheet0.getLastRowNum()+1;//找到总共几行
                System.out.println("总共行："+rowNum);
                //获取总共几列

                //遍历行数
                for (int i=1;i<rowNum;i++)
                {
                    XSSFRow row= row = sheet0.getRow(i);;
                    DeliverBaseVO deliverBaseVO = new DeliverBaseVO();
                    //遍历列数
                    deliverBaseVO.setShopCode(Double.valueOf(row.getCell(0).getStringCellValue()).intValue());
                    deliverBaseVO.setMerchantName(row.getCell(1).getStringCellValue());
                    deliverBaseVO.setModel(row.getCell(2).getStringCellValue());
                    deliverBaseVO.setPoi(getPoiString(row.getCell(5).getStringCellValue()));
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


        public static String getPoiString(String poiString) {
            if(!StringUtils.hasLength(poiString)){
                return null;
            }
            String[] poiList =  poiString.split(";");
            if(null == poiList || poiList.length ==0){
                return null;
            }
            List<PoiDto> list = new ArrayList<>();
            int i =0;
            for(String str : poiList){
                i+=1;
                //System.out.println(i%2+"   " + poiString.length());
                if(i%2 == 0){
                    //continue;
                }
                String[] point = str.split(",");
                PoiDto poiDto = new PoiDto();
                poiDto.setLng(new BigDecimal(point[1].toString().trim()));
                poiDto.setLat(new BigDecimal(point[0].toString().trim()));
                list.add(poiDto);
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(list);
            return  jsonArray.toString();
        }


    public static void main(String[] args) {
        getABDeliverBaseVo();
    }

    }