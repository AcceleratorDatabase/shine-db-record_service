/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record_service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import shine.db.record.api.ServerAPI;
import shine.db.record.ca.CAChannelGet;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class ShineDbRecord_service {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Server> serverList=new ArrayList();
        ServerAPI sapi=new ServerAPI();
        serverList=sapi.getAllServer();
        Iterator it=serverList.iterator();
        while(it.hasNext()){
            Server server=(Server) it.next();
            String IP=server.getIp();           
            List<Ioc> ioc_list=server.getIocList();
            
            Iterator it1=ioc_list.iterator();
            while(it1.hasNext()){
               Ioc ioc=(Ioc) it1.next();             
               List<Record> record_list=ioc.getRecordList();          
             //  CAChannelGet.setFieldsValueForRecords(IP,  record_list);
            }
           
        }
    }     
    
}
