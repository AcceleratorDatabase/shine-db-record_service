/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.ca;

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
        
                    
       String ip="10.40.18.143";
        /*Ioc ioc=new IocAPI().getByName("iocfirst");
        List<Record> rList=ioc.getRecordList();
        CAChannelGet.setFieldsValueForRecords(ip, rList);*/
     
       IOCStatsGet iocStats=new IOCStatsGet();
      System.out.println(iocStats.getIOCStatsName("D:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata\\iocStats"));
       iocStats.setEpicsEnv(ip, "D:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata\\iocStats");
     
    }
}
