/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.ca;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.epics.ca.Channel;
import org.epics.ca.Context;
import org.epics.ca.data.Alarm;
import shine.db.record.api.FieldTypeAPI;
import shine.db.record.api.FieldValueAPI;
import shine.db.record.api.IocAPI;
import shine.db.record.api.RecordAPI;
import shine.db.record.entity.FieldType;
import shine.db.record.entity.FieldValue;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;

/**
 *
 * @author Lvhuihui
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
      
        CAChannelGet ca=new CAChannelGet();
        IocAPI iocAPI=new IocAPI();
        List<Ioc> iocList=iocAPI.getAllIoc();
        Iterator it=iocList.iterator();
        while(it.hasNext()){
            Ioc ioc=(Ioc) it.next();
            List<Record> rList=ioc.getRecordList();
            //System.out.println(ioc.getName()+rList.size());
            ca.setFieldsValueForRecords(ioc.getServierId().getIp(), ioc.getName(),rList);
        }
        
      

       
       // ca.getRecordType(ip, "iocfirst", a);
       //  List<Record> rList=ioc.getRecordList();
        // Record r=rList.get(1);
        // System.out.println(r.getName());
        // rList.clear();
        // rList.add(r);
        //  CAChannelGet.setFieldsValueForRecords(ip, rList);

      //    IOCStatsGet iocStats=new IOCStatsGet();
       //   System.out.println(iocStats.getIOCStatsName("E:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata\\iocStats"));
      //     iocStats.setEpicsEnv(ip, "E:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata\\iocStats");
    }
}
