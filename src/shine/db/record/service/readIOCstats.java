/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import shine.db.record.api.ServerAPI;
import shine.db.record.ca.IOCStatsGet;
import shine.db.record.entity.Server;

/**
 *
 * @author lvhuihui
 */
public class readIOCstats {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        IOCStatsGet iocStats = new IOCStatsGet();
        ServerAPI sAPI=new ServerAPI();
        List<Server> sList=sAPI.getAllServer();
        Iterator it=sList.iterator();
        while(it.hasNext()){
           Server s=(Server) it.next();
           iocStats.setEpicsEnv(s.getIp(), "E:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata\\iocStats");
        }       
    }
}
