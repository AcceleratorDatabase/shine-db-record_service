/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.initFields;

import java.io.File;
import org.apache.poi.ss.usermodel.Workbook;
import shine.db.record.api.FieldGroupAPI;
import shine.db.record.api.FieldTypeAPI;

/**
 *
 * @author Lvhuihui
 */
public class Test {
    public static void main(String[] args){
       File f=new File("D:\\NetBeansProjects\\shine-db-record_service\\data\\Initial Fields.xlsx");
     //  Workbook wb=ExcelTool.getWorkbook(f);
     //  ReadExcel.getSheetAt(wb, 0);*/
       Excel2DB.setAllFIelds(f);

    
    }
}