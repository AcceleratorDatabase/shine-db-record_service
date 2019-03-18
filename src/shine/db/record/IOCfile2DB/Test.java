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
        String filePath="D:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata";
        FolderFileScanner f=new FolderFileScanner();
        f.scanFromDir(filePath);
        
    /* RecordTypeAPI rtapi=new RecordTypeAPI();
        RecordType rt = rtapi.getRecordType("aSub");
        System.out.println(rt);
       
        rtapi.setFieldTypeList(rt, rtapi.getCommonFieldTypeList());*/
       
       

        /*  String s="ai";
         RecordType rt=new RecordTypeAPI().getRecordType(s);
         System.out.println(rt.getFieldNameList().size());*/
        //   Data2DB.write2DB("D:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata\\iocfirst");
        //  RecordType rt=new RecordTypeAPI().getRecordType("common");
        //  System.out.println(rt);
        //  System.out.println(rt.getFieldTypeList().size());
        //  System.out.println(Read2String.read("D:\\\\NetBeansProjects\\\\shine-db-record_service\\\\data\\\\IOCdata\\\\iocfirst"));
        //   String IP="10.40.18.143";      
        //   ArrayList<String> a=new ArrayList();
        //   a.add("IOCTEST:EPICS_VERS");
        //  a.add("a:aSubExample");
        //  a.add("b:calcExample");
        //   a.add("b:compressExample");
        //   a.add("a:subExample");
        //   a.add("b:xxxExample");
        // System.out.println("***********");
        // CAChannelGet.getRecordType("10.40.18.143", a);
        //context.close();
    }
}
