/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.ca;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
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
import shine.db.record.IOCfile2DB.Read2String;
import shine.db.record.api.EpicsEnvAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.common.constants.ConnectSet;
import shine.db.record.common.constants.EpicsEnvName;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class IOCStatsGet {

    static ExecutorService exec = Executors.newFixedThreadPool(1);

    public String getIOCStatsName(String path) {
        String s = Read2String.read(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        String name = null;
        try {
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains("access")) {
                    name = line.substring(0, line.indexOf(":") + 1);
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(IOCStatsGet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    public void setEpicsEnv(String IP, String path) {
        String name = this.getIOCStatsName(path);
        Properties properties = new Properties();
        properties.setProperty(Context.Configuration.EPICS_CA_ADDR_LIST.toString(), IP);
        Context context = new Context(properties);
        List<ChannelDescriptor<?>> descriptors = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : EpicsEnvName.ENVLIST.entrySet()) {
           // System.out.println(name + entry.getValue());
            descriptors.add(new ChannelDescriptor<>((name + entry.getValue()), String.class));
        }
     
        Future<List<Channel<?>>> future = exec.submit(new Callable<List<Channel<?>>>() {
            public List<Channel<?>> call() {              
                return Channels.create(context, descriptors);
            }
        });
        List<Channel<?>> channels;
        try {
            channels = future.get(ConnectSet.Timeout, TimeUnit.MILLISECONDS); //超时200ms
            int i = 0;
            for (Map.Entry<String, String> entry : EpicsEnvName.ENVLIST.entrySet()) {
                map.put(entry.getKey(), channels.get(i).get().toString());
                i++;
            }
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            future.cancel(true);
            System.out.println("connection timeout");
        } finally {
            exec.shutdown();
        }
        Channels.close(descriptors);
        Server s = new ServerAPI().getByIP(IP);
        //System.out.println(map);
        new EpicsEnvAPI().setEpicsEnv(s, map);        
        context.close();
    }
}
