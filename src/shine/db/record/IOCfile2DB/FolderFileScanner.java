/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.IOCfile2DB;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Lvhuihui
 */
public class FolderFileScanner {

    public  void scanFromDir(String filePath) {
        File[] files = new File(filePath).listFiles();
        for(File childFile:files){
             String fileName=childFile.getName();
             if(!fileName.toLowerCase().contains("stats")){
                 Data2DB.write2DB(childFile);
             }
        }
    }

}
