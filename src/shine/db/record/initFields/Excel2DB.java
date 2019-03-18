/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.initFields;

import java.io.File;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import shine.db.record.api.FieldTypeAPI;

/**
 *
 * @author Lvhuihui
 */
public class Excel2DB {
    
    public static void setAllFIelds(File f){      
       Workbook wb=ExcelTool.getWorkbook(f);
       int num=wb.getNumberOfSheets();
       for(int i=0;i<num;i++){
           Sheet sheet=wb.getSheetAt(i);
           String sheetName=sheet.getSheetName();
           
           ArrayList list=(ArrayList) ReadExcel.getSheetAt(wb, i);
           
           if(!sheetName.toLowerCase().equals("common")){
               Fields2DB.setFields(sheetName, list);
           }                    
       }
       ArrayList<String> list=ReadExcel.getSheet(wb, "common");
       if(!list.isEmpty())  
           Fields2DB.setCommanFields(list);
      
    }   
 
}
