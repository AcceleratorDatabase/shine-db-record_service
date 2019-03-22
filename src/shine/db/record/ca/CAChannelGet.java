/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.ca;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.epics.ca.Channel;
import org.epics.ca.ChannelDescriptor;
import org.epics.ca.Channels;
import org.epics.ca.Context;
import shine.db.record.api.FieldTypeAPI;
import shine.db.record.api.FieldValueAPI;
import shine.db.record.api.IocAPI;
import shine.db.record.api.RecordAPI;
import shine.db.record.api.RecordTypeAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.common.constants.ConnectSet;
import shine.db.record.entity.FieldType;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;
import shine.db.record.entity.RecordType;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class CAChannelGet {

    // static E  ExecutorService exec = Executors.newCachedThreadPool();xecutorService exec = Executors.newFixedThreadPool(1);
    // ExecutorService exec = Executors.newFixedThreadPool(1);
    ExecutorService exec = Executors.newCachedThreadPool();
    //EPICS_CA_SERVER_PORT默认为5064，map:key--record name  value--record type

    /*public static Map getRecordType(String IP, ArrayList<String> record_name_list) {
       // System.out.println(IP);
        Properties properties = new Properties();
        properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), IP);
        Context context = new Context(properties);
        Map<String, String> map = new HashMap<String, String>();
        List<ChannelDescriptor<?>> descriptors = new ArrayList<>();
        Iterator it = record_name_list.iterator();
        while (it.hasNext()) {
            String record_name = it.next().toString();
            descriptors.add(new ChannelDescriptor<>((record_name + ".RTYP"), String.class));
        }
        //   List<Channel<?>> channels = Channels.create(context, descriptors);
        
        Future<List<Channel<?>>> future = exec.submit(new Callable<List<Channel<?>>>() {
            public List<Channel<?>> call() {
                return Channels.create(context, descriptors);
            }
        });
        List<Channel<?>> channels;
        try {
            channels = future.get(ConnectSet.Timeout, TimeUnit.MILLISECONDS); //超时200ms
            for (int i = 0; i < record_name_list.size(); i++) {
                map.put(record_name_list.get(i), channels.get(i).get().toString());
            }

        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            future.cancel(true);
            System.out.println("connection timeout");
        } finally {
            exec.shutdown();
        }

        Channels.close(descriptors);
        context.close();
       //  System.out.println(map);
        return map;
    }*/ //map:key--record name  value--record type
    public Map getRecordType(String IP, String ioc_name, ArrayList<String> record_name_list) {
        Ioc ioc = new IocAPI().getByIP_Name(IP, ioc_name);
        Properties properties = new Properties();
        String ip = (IP + ":" + ioc.getEpicsCaServerPort()).replaceAll(" ", "");
        properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), ip);
        //  properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), "192.168.31.111:5066");
        //  properties.setProperty(Context.Configuration.EPICS_CA_SERVER_PORT.toString(), ioc.getEpicsCaServerPort());
        System.out.println(ip);
        Context context = new Context(properties);
        Map<String, String> map = new HashMap<String, String>();
        List<ChannelDescriptor<?>> descriptors = new ArrayList<>();
        Iterator it = record_name_list.iterator();
        while (it.hasNext()) {
            String record_name = it.next().toString();
            descriptors.add(new ChannelDescriptor<>((record_name + ".RTYP"), String.class));
        }
        //   List<Channel<?>> channels = Channels.create(context, descriptors);

        Future<List<Channel<?>>> future = exec.submit(new Callable<List<Channel<?>>>() {
            public List<Channel<?>> call() {
                return Channels.create(context, descriptors);
            }
        });
        List<Channel<?>> channels;
        try {
            channels = future.get(ConnectSet.Timeout, TimeUnit.MILLISECONDS); //超时200ms
            for (int i = 0; i < record_name_list.size(); i++) {
                map.put(record_name_list.get(i), channels.get(i).get().toString());
            }

        } catch (InterruptedException | ExecutionException | TimeoutException ex) {

            future.cancel(true);
            System.out.println("connection timeout");
        } finally {
            exec.shutdown();
        }

        Channels.close(descriptors);
        context.close();
        System.out.println(map);
        return map;
    }

    //set fields value for one record，默认端口是5064
    /*  public static void setFieldsValue(String IP, String record_name) {
        Properties properties = new Properties();
        properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), IP);
        Context context = new Context(properties);
        Record record = new RecordAPI().getByName(record_name);
        RecordTypeAPI rtapi = new RecordTypeAPI();
        FieldTypeAPI fnapi = new FieldTypeAPI();
        FieldValueAPI fvapi = new FieldValueAPI();
        if (record != null) {
            List<ChannelDescriptor<?>> descriptors = new ArrayList<>();
            RecordType rt = record.getRecordTypeId();

            if (rt != null) {
                //System.out.println(rt);
                List<FieldType> fields = rt.getFieldTypeList();
                //用户自定义类型，取common域
                if (fields.isEmpty()) {
                    fields = rtapi.getRecordType("common").getFieldTypeList();
                  //  System.out.println(fields);
                }
                Iterator it = fields.iterator();
                while (it.hasNext()) {
                    FieldType fn = (FieldType) it.next();
                    //System.out.println(fn.getName());

                    descriptors.add(new ChannelDescriptor<>((record_name + "." + fn.getName()), String.class));
                    // System.out.println(record_name + "." + fn.getName());
                }
                Map<FieldType, String> map = new HashMap();
                // List<Channel<?>> channels = Channels.create(context, descriptors);
                List<Channel<?>> channels = new ArrayList<>(descriptors.size());
                List<CompletableFuture<?>> futures = new ArrayList<>(descriptors.size());
                for (ChannelDescriptor<?> descriptor : descriptors) {
                    Channel<?> channel = context.createChannel(descriptor.getName(), descriptor.getType());
                    channels.add(channel);
                    futures.add(channel.connectAsync());
                }
                try {
                    Thread.sleep(ConnectSet.Timeout);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CAChannelGet.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i < fields.size(); i++) {
                    FieldType field_name = fields.get(i);
                    if (futures.get(i).isDone()) {
                        map.put(field_name, (String) channels.get(i).get());
                    } else {
                        map.put(field_name, null);
                    }

                }
                fvapi.setFieldValueList(record, map);
            } else {
                System.out.println("The record " + record_name + " does not have a type.");
            }
            Channels.close(descriptors);
            context.close();
        } else {
            System.out.println("The record " + record_name + " does not exist in the database.");
        }

    }*/
    //set fields value for a list of records
    public  void setFieldsValue(String IP, String ioc_name, ArrayList<String> record_name_list) {
        Ioc ioc = new IocAPI().getByIP_Name(IP, ioc_name);
        Properties properties = new Properties();
        String ip = (IP + ":" + ioc.getEpicsCaServerPort()).replaceAll(" ", "");
        properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), ip);
       
        int N = record_name_list.size() / ConnectSet.RecordNumPerTime + 1;
        // System.out.println(N);
        for (int t = 0; t < N; t++) {
            List<String> record_name_list_p = new ArrayList();
            if (t < N - 1) {
                record_name_list_p = record_name_list.subList(t * ConnectSet.RecordNumPerTime, (t + 1) * ConnectSet.RecordNumPerTime);
            } else {
                record_name_list_p = record_name_list.subList((N - 1) * ConnectSet.RecordNumPerTime, record_name_list.size());
            }
            RecordAPI rapi = new RecordAPI();
            RecordTypeAPI rtapi = new RecordTypeAPI();
            List<Record> record_list = new ArrayList();
            List<FieldType> field_name_list = new ArrayList();
            List<Map<FieldType, String>> fieldMapList = new ArrayList();
            List<Integer> fieldsNum = new ArrayList(); //存放每个record对应的fields数目
            // Properties properties = new Properties();
            //   properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), IP);
             Context context = new Context(properties);
            List<ChannelDescriptor<?>> descriptors = new ArrayList<>();
            Iterator it = record_name_list_p.iterator();
            while (it.hasNext()) {
                String record_name = it.next().toString();
                Record r = rapi.getByName(record_name);
                if (r == null) {
                    System.out.println("The record " + record_name + " does not exist in the database.");
                } else {
                    record_list.add(r);
                    //获得每个record对应的record type有哪些field name
                    List<FieldType> fields = r.getRecordTypeId().getFieldTypeList();
                    //用户自定义类型，取common域
                    if (fields.isEmpty()) {
                        fields = rtapi.getRecordType("common").getFieldTypeList();
                    }
                    // System.out.println(fields.size());
                    fieldsNum.add(fields.size());
                    Iterator it1 = fields.iterator();
                    while (it1.hasNext()) {
                        FieldType fn = (FieldType) it1.next();
                        field_name_list.add(fn);
                        descriptors.add(new ChannelDescriptor<>((record_name + "." + fn.getName()), String.class));
                    }

                }
            }
            List<Channel<?>> channels = new ArrayList<>(descriptors.size());
            List<CompletableFuture<?>> futures = new ArrayList<>(descriptors.size());
            for (ChannelDescriptor<?> descriptor : descriptors) {
                Channel<?> channel = context.createChannel(descriptor.getName(), descriptor.getType());
                channels.add(channel);
                futures.add(channel.connectAsync());
            }
            try {
                sleep(ConnectSet.Timeout);
            } catch (InterruptedException ex) {
                Logger.getLogger(CAChannelGet.class.getName()).log(Level.SEVERE, null, ex);
            }

            int num = 0;

            for (int i = 0; i < record_list.size(); i++) {

                Map<FieldType, String> map = new HashMap();
                //System.out.println(fieldsNum.get(i));
                for (int j = 0; j < fieldsNum.get(i); j++) {
                    System.out.println(num + "---" + channels.get(num).getConnectionState() + "---" + futures.get(num).isDone());
                    if (futures.get(num).isDone()) {
                        String value = null;
                        try {
                            value = (String) channels.get(num).get();
                        } catch (java.lang.RuntimeException e) {
                            value = null;
                        } finally {
                            map.put(field_name_list.get(num), value);
                        }

                    } else {
                        map.put(field_name_list.get(num), null);
                    }

                    num++;

                }

                fieldMapList.add(map);
            }
            Channels.close(descriptors);
            context.close();
            new FieldValueAPI().setFieldsValueForRecordList(record_list, fieldMapList);
        }

    }

    public  void setFieldsValueForRecords(String IP,String ioc_name, List<Record> record_list_all) {
        Ioc ioc = new IocAPI().getByIP_Name(IP, ioc_name);
        Properties properties = new Properties();
        String ip = (IP + ":" + ioc.getEpicsCaServerPort()).replaceAll(" ", "");
        properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), ip);
        Context context = new Context(properties);
        int N = (int) Math.ceil((double) record_list_all.size() / ConnectSet.RecordNumPerTime);
        // System.out.println(N);
        for (int t = 0; t < N; t++) {
            List<Record> record_list_p = new ArrayList();
            if (t < N - 1) {
                record_list_p = record_list_all.subList(t * ConnectSet.RecordNumPerTime, (t + 1) * ConnectSet.RecordNumPerTime);
            } else {
                record_list_p = record_list_all.subList((N - 1) * ConnectSet.RecordNumPerTime, record_list_all.size());
            }
            //RecordAPI rapi = new RecordAPI();
            RecordTypeAPI rtapi = new RecordTypeAPI();

            List<FieldType> field_name_list = new ArrayList();
            List<Map<FieldType, String>> fieldMapList = new ArrayList();
            List<Integer> fieldsNum = new ArrayList(); //存放每个record对应的fields数目
          //  Properties properties = new Properties();
        //    properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), IP);
           // Context context = new Context(properties);
            List<ChannelDescriptor<?>> descriptors = new ArrayList<>();
            Iterator it = record_list_p.iterator();
            // System.out.println(record_list_p);
            while (it.hasNext()) {
                //  for(int m=0;m<record_list_p.size();m++){                             
                //    Record r = record_list_p.get(m);      
                Record r = (Record) it.next();
                if (r == null) {
                    System.out.println("The record " + r.getName() + " does not exist in the database.");
                } else {
                    //   record_list.add(r);
                    //获得每个record对应的record type有哪些field name
                    List<FieldType> fields = r.getRecordTypeId().getFieldTypeList();
                    // System.out.println(fields.size());
                    fieldsNum.add(fields.size());
                    Iterator it1 = fields.iterator();
                    while (it1.hasNext()) {
                        FieldType fn = (FieldType) it1.next();
                        field_name_list.add(fn);
                        descriptors.add(new ChannelDescriptor<>((r.getName() + "." + fn.getName()), String.class));
                    }
                }
            }

            List<Channel<?>> channels = new ArrayList<>(descriptors.size());
            List<CompletableFuture<?>> futures = new ArrayList<>(descriptors.size());
            for (ChannelDescriptor<?> descriptor : descriptors) {
                Channel<?> channel = context.createChannel(descriptor.getName(), descriptor.getType());
                //System.out.println(channel.getConnectionState());
                channels.add(channel);
                futures.add(channel.connectAsync());
            }
            try {
                sleep(ConnectSet.Timeout*ConnectSet.RecordNumPerTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(CAChannelGet.class.getName()).log(Level.SEVERE, null, ex);
            }
            int num = 0;
            for (int i = 0; i < record_list_p.size(); i++) {
                Map<FieldType, String> map = new HashMap();
                //System.out.println(fieldsNum.get(i));
                for (int j = 0; j < fieldsNum.get(i); j++) {
                    System.out.println(channels.get(num).getName() + "---" + channels.get(num).getConnectionState() + "---" + futures.get(num).isDone());
                    if (futures.get(num).isDone()) {
                        String value = null;
                        try {
                            value = (String) channels.get(num).get();
                            //System.out.println(value);
                        } catch (java.lang.RuntimeException e) {
                            value = null;
                        } finally {
                            map.put(field_name_list.get(num), value);
                        }
                    } else {
                        map.put(field_name_list.get(num), null);
                    }
                    num++;
                }
                fieldMapList.add(map);
            }
            Channels.close(descriptors);
       //     context.close();
            new FieldValueAPI().setFieldsValueForRecordList(record_list_p, fieldMapList);
          //  context=new Context(properties);
        }
         context.close();
    }
}
