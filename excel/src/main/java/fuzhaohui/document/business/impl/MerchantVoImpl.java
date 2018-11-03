package fuzhaohui.document.business.impl;

import com.alibaba.fastjson.JSON;
import fuzhaohui.document.business.model.DeliverBaseVO;
import fuzhaohui.document.business.model.MerchantVO;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月2日11:40:35$ $
 */
public class MerchantVoImpl {

    public static List<MerchantVO> getMerchantVOList() {

        List<MerchantVO> merchantVOList = new ArrayList<>();
        try{
            File directory = new File("");//设定为当前文件夹
            directory = new File(directory.getCanonicalPath().toString()+"/source/txt/商家门店poi.txt");
            BufferedReader br = new BufferedReader(new FileReader(directory));//构造一个BufferedReader类来读取文件
            br.readLine();//先读一行
            String line = null;
            int totalNum =1;

            while((line = br.readLine()) != null){
                totalNum = totalNum+1;
                if("".equals(line)){
                    continue;
                }
                String[] str = line.split("\\|");
                MerchantVO merchantVO = new MerchantVO();
                merchantVO.setMerchantId(Long.valueOf(str[0]));
                merchantVO.setShopCode(Integer.valueOf(str[1]));
                merchantVO.setMerchantName(str[2]);
                if(str.length>3){
                    merchantVO.setPoi(str[3]);
                }
                merchantVOList.add(merchantVO);
            }
            System.out.println("总共行" + totalNum);
            System.out.println("有效行" + merchantVOList.size());
            br.close();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //log.warn(e);
        }
        return merchantVOList;

    }


    public static void main(String[] args) {
        getMerchantVOList();
    }



}
