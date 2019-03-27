/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.IOCfile2DB;

import java.io.File;
import java.util.ArrayList;
import shine.db.record.api.ServerAPI;
import shine.db.record.ca.IOCStatsGet;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class FolderFileScanner {

    public void scanFromDir(String filePath) {
        File[] files = new File(filePath).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String ip = file.getName();
                File[] childFiles = new File(file.getAbsolutePath()).listFiles();
                for (File childFile : childFiles) {
                    if (!childFile.getName().toLowerCase().contains("stats")) {
                        Data2DB.write2DB(ip, childFile);
                    } else {
                        IOCStatsGet iocStats = new IOCStatsGet();
                        iocStats.setEpicsEnv(ip, childFile.getAbsolutePath());
                    }

                }
            }

            /*  String fileName=childFile.getName();
             if(!fileName.toLowerCase().contains("stats")){
                // System.out.println("+++++++++"+childFile.getName());
                 Data2DB.write2DB(childFile);
             }*/
        }
    }

}
