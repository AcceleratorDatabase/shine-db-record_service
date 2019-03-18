/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.initFields;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author huihui
 */
public class ReadExcel {

    public ReadExcel() {

    }

   //arraylist包含若干行的内容，每一行是一个ArrayList
    public static ArrayList getSheetAt(Workbook wb, int num) {
        ArrayList dataList = new ArrayList();
        Sheet sheet = wb.getSheetAt(num);
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
            Row row = (Row) rit.next();
            ArrayList rowList = new ArrayList();
            for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
                Cell cell = cit.next();
                rowList.add(cell.getStringCellValue());
                //  System.out.println(cell);
            }
            if (rowList.size() < 3) {
                rowList.add("");
            }
            dataList.add(rowList);
        }
        dataList.remove(0);
        // System.out.println(dataList);
        return dataList;
    }

    public static ArrayList getSheet(Workbook wb, String sheetName) {
        ArrayList dataList = new ArrayList();
        Sheet sheet = wb.getSheet(sheetName);
        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
            Row row = (Row) rit.next();
            ArrayList rowList = new ArrayList();
            for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
                Cell cell = cit.next();
                rowList.add(cell.getStringCellValue());
                //  System.out.println(cell);
            }
            if (rowList.size() < 3) {
                rowList.add("");
            }
            dataList.add(rowList);
        }
        dataList.remove(0);
        // System.out.println(dataList);
        return dataList;
    }
}
