/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.IOCfile2DB;

import java.util.ArrayList;
import java.util.Properties;
import org.epics.ca.Context;
import static shine.db.record.api.RecordAPI.em;
import shine.db.record.api.RecordTypeAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.ca.CAChannelGet;
import shine.db.record.entity.RecordType;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class Test {

    public static void main(String[] args) {
        String filePath="E:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata";
        FolderFileScanner f=new FolderFileScanner();
        f.scanFromDir(filePath);       
    }
}
