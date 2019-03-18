/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.initFields;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author Lvhuihui
 */
public class ExcelTool {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static Workbook getWorkbook(InputStream in, File file) {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {
            try {
                wb = new HSSFWorkbook(in);
            } catch (IOException ex) {
                Logger.getLogger(ExcelTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (file.getName().endsWith(EXCEL_XLSX)) {
            try {
                wb = new XSSFWorkbook(in);
            } catch (IOException ex) {
                Logger.getLogger(ExcelTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return wb;
    }

    public static Workbook getWorkbook(File f) {
        Workbook wb = null;
        try {
            FileInputStream in = null;          
            try {
                in = new FileInputStream(f);
            } catch (FileNotFoundException ex) {
                System.out.println("The file path is not valid");
                Logger.getLogger(ExcelTool.class.getName()).log(Level.SEVERE, null, ex);
            }
            checkExcelValid(f);
            wb = getWorkbook(in, f);

        } catch (Exception ex) {
            Logger.getLogger(ExcelTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wb;
    }

    public static void checkExcelValid(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("The file does not exist!");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("The file is not Excel!");
        }
    }

}
