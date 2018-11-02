package fuzhaohui.document.excel;

import ch.qos.logback.classic.Logger;
import fuzhaohui.document.App;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年10月31日20:48:02$ $
 */
public class ExcelCore {

    private static Logger log = (Logger) LoggerFactory.getLogger(ExcelCore.class);


    public static void main(String[] args) {
        File directory = new File("");//设定为当前文件夹
        try {
            System.out.println(readXlsx(directory.getCanonicalPath().toString()+"/source/excel/配送下单规则数据.xlsx"));
            System.out.println(readXlsx(directory.getCanonicalPath().toString()+"/source/excel/配送基础规则数据表.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String readXls(String path)
    {
        String text="";
        try
        {
            FileInputStream is =  new FileInputStream(path);
            HSSFWorkbook excel=new HSSFWorkbook(is);
            //获取第一个sheet
            HSSFSheet sheet0=excel.getSheetAt(0);
            for (Iterator rowIterator = sheet0.iterator(); rowIterator.hasNext();)
            {
                HSSFRow row=(HSSFRow) rowIterator.next();
                for (Iterator iterator=row.cellIterator();iterator.hasNext();)
                {
                    HSSFCell cell=(HSSFCell) iterator.next();
                    //根据单元的的类型 读取相应的结果
                    if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING) text+=cell.getStringCellValue()+"\t";
                    else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC) text+=cell.getNumericCellValue()+"\t";
                    else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA) text+=cell.getCellFormula()+"\t";
                }
                text+="\n";
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //log.warn(e);
        }

        return text;
    }
    public static String readXlsx(String path)
    {
        String text="";
        try
        {
            OPCPackage pkg= OPCPackage.open(path);
            XSSFWorkbook excel=new XSSFWorkbook(pkg);
            //获取第一个sheet
            XSSFSheet sheet0  = excel.getSheetAt(0);

            int rowNum = sheet0.getLastRowNum();//找到总共几行
            //获取总共几列
            Row rowx = sheet0.getRow(1);

            System.out.println(rowx.getCell(0));
            System.out.println(rowx.getCell(1));
            System.out.println(rowx.getCell(2));
            System.out.println(new SimpleDateFormat("HH:mm").format(rowx.getCell(3).getDateCellValue()));
            System.out.println(new SimpleDateFormat("HH:mm").format(rowx.getCell(4).getDateCellValue()));
            System.out.println(Double.valueOf(rowx.getCell(5).getNumericCellValue()).intValue());
            System.out.println(new SimpleDateFormat("HH:mm").format(rowx.getCell(6).getDateCellValue()));
            System.out.println(new SimpleDateFormat("HH:mm").format(rowx.getCell(7).getDateCellValue()));

            //遍历行数
            for (Iterator rowIterator=sheet0.iterator();rowIterator.hasNext();)
            {
                XSSFRow row=(XSSFRow) rowIterator.next();
                //遍历列数
                for (Iterator iterator = row.cellIterator();iterator.hasNext();)
                {
                    XSSFCell cell=(XSSFCell) iterator.next();

                    if(cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm") ||
                            cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd")){
                        SimpleDateFormat sdf = null;
                        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                                .getBuiltinFormat("h:mm")) {
                            sdf = new SimpleDateFormat("HH:mm");
                        } else {// 日期
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        Date date = cell.getDateCellValue();
                        text+=sdf.format(date)+"\t";
                        continue;
                    }

                    //根据单元的的类型 读取相应的结果
                    if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING) text+=cell.getStringCellValue()+"\t";
                    else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC) text+=cell.getNumericCellValue()+"\t";
                    else if(cell.getCellType()==XSSFCell.CELL_TYPE_FORMULA) text+=cell.getCellFormula()+"\t";
                }
                text+="\n";
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //log.warn(e);
        }

        return text;
    }


}
