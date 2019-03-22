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
import shine.db.record.api.IocAPI;
import shine.db.record.ca.CAChannelGet;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;

/**
 *
 * @author lvhuihui
 */
public class readPVs {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        CAChannelGet ca = new CAChannelGet();
        IocAPI iocAPI = new IocAPI();
        List<Ioc> iocList = iocAPI.getAllIoc();
        Iterator it = iocList.iterator();
        while (it.hasNext()) {
            Ioc ioc = (Ioc) it.next();
            List<Record> rList = ioc.getRecordList();
            //System.out.println(ioc.getName()+rList.size());
            ca.setFieldsValueForRecords(ioc.getServierId().getIp(), ioc.getName(), rList);
        }
    }
}
