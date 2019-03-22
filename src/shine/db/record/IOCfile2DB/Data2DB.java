/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.IOCfile2DB;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Boolean.TRUE;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import shine.db.record.api.IocAPI;
import shine.db.record.api.RecordAPI;
import shine.db.record.api.ServerAPI;
import shine.db.record.ca.CAChannelGet;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;
import shine.db.record.entity.Server;
import shine.db.record.readServerEnv.OSUtils;
import shine.db.record.readServerEnv.SSHExecutor;
import shine.db.record.readServerEnv.SSHInfo;

/**
 *
 * @author Lvhuihui
 */
public class Data2DB {

    public static void write2DB(String path) {
        String s = Read2String.read(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        //StringBuffer strbuf = new StringBuffer();

        String ioc_name = null;
        String install_path = null;
        Date date = null;
        String ip = null;
        String EPICS_CA_SERVER_PORT = null;
        String EPICS_CA_REPEATER_PORT = null;
        int end;
        ArrayList<String> recordList = new ArrayList();
        try {
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains("iocname")) {
                    ioc_name = line.substring(line.indexOf(":") + 1);
                } else if (line.toLowerCase().contains("path")) {
                    install_path = line.substring(line.indexOf(":") + 1);
                } else if (line.toLowerCase().contains("date")) {
                    String string = line.substring(line.indexOf(":") + 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    try {
                        date = sdf.parse(string);
                    } catch (ParseException ex) {
                        Logger.getLogger(Data2DB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (line.contains("EPICS_CA_SERVER_PORT")) {
                    EPICS_CA_SERVER_PORT = line.substring(line.indexOf(":") + 1);
                } else if (line.contains("EPICS_CA_REPEATER_PORT")) {
                    EPICS_CA_REPEATER_PORT = line.substring(line.indexOf(":") + 1);
                } else if (line.toLowerCase().contains("record")) {
                    br.mark(s.length() + 1);
                } else if (line.toLowerCase().contains("ip")) {
                    ip = line.substring(line.indexOf(":") + 1);
                }

            }
            br.reset();
            while ((line = br.readLine()) != null) {
                if (!line.toLowerCase().contains("ip")) {
                    recordList.add(line);
                    //System.out.println(line);
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Data2DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        ServerAPI serverAPI = new ServerAPI();
        Server server = serverAPI.getByIP(ip);

        if (server == null) {
            //初始化server_env表，只给env_name赋值
            server = serverAPI.setServer(ip);
            serverAPI.initServerEnv(server);
            //初始化epics_env表，只给epics_name赋值  
            serverAPI.initEpicsEnv(server);
        }

        //读server相关信息
        SSHInfo sshInfo = new SSHInfo("root", "sinap123", ip, 22);
        SSHExecutor ssh = new SSHExecutor(sshInfo);
        OSUtils os = new OSUtils();
        os.setSsh(ssh);
        Map<String, String> memMap = os.memoryinfo();
        Map<String, String> cpuMap = os.cpumodel();
        Map<String, Integer> fileMap = os.fileDesc();
        Map<String, String> serverEnvMap = os.serverEnv();
        //System.out.println(os.loadAverage());
        serverAPI.updateServer(server, os.startupTime(), new Date(), os.OSvern(),
                memMap.get("mem_total"), memMap.get("mem_free"), memMap.get("buffers"),
                memMap.get("cached"), String.valueOf(os.cpuUsage()), os.loadAverage(), cpuMap.get("cpu_model"),
                cpuMap.get("cpu_num"), fileMap.get("file_free"), fileMap.get("file_max"), serverEnvMap);
        // System.out.println(os.loadAverage());
        ssh.close();
        //读server相关信息结束

        //ioc相关信息
        IocAPI iocAPI = new IocAPI();
        Ioc ioc = iocAPI.getByName(ioc_name);
        if (ioc == null) {
            ioc = iocAPI.setIOC(server, ioc_name, install_path, date, new Date(), TRUE, EPICS_CA_SERVER_PORT, EPICS_CA_REPEATER_PORT);
        } else {
            ioc = iocAPI.updateIOC(ioc, install_path, date, new Date(), TRUE, EPICS_CA_SERVER_PORT, EPICS_CA_REPEATER_PORT);
        }

        //record相关信息
        RecordAPI recordAPI = new RecordAPI();
        ArrayList<Record> rList = new ArrayList();
        Iterator it = recordList.iterator();
        while (it.hasNext()) {
            String record_name = it.next().toString();
            Record r = recordAPI.getByName(record_name);
            if (r == null) {
                r = recordAPI.setRecord(ioc, record_name, TRUE);
            }
            rList.add(r);
        }
        //get record tpye from epics channel
        CAChannelGet ca=new CAChannelGet();
        Map record_type_map = ca.getRecordType(ip,ioc.getName(), recordList);
       // System.out.println(recordList);
        //IOC connection failure
        if (record_type_map.isEmpty()) {
            Iterator it1 = rList.iterator();
            while (it1.hasNext()) {
                Record r = (Record) it1.next();
                recordAPI.updateRecord(r, false);
            }
            iocAPI.updateIOC(ioc, false);
        } else {
            new RecordAPI().setRecordType(record_type_map);
            Iterator it2 = rList.iterator();
            while (it2.hasNext()) {
                Record r = (Record) it2.next();
                recordAPI.updateRecord(r, true);
            }
            iocAPI.updateIOC(ioc, true);
        }
    }
    
    public static void write2DB(File file) {
        String s = Read2String.read(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        //StringBuffer strbuf = new StringBuffer();
       // System.out.println(br);
        String ioc_name = null;
        String install_path = null;
        Date date = null;
        String ip = null;
        String EPICS_CA_SERVER_PORT = null;
        String EPICS_CA_REPEATER_PORT = null;
        int end;
        ArrayList<String> recordList = new ArrayList();
        try {
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains("iocname")) {
                    ioc_name = line.substring(line.indexOf(":") + 1);
                } else if (line.toLowerCase().contains("path")) {
                    install_path = line.substring(line.indexOf(":") + 1);
                } else if (line.toLowerCase().contains("date")) {
                    String string = line.substring(line.indexOf(":") + 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    try {
                        date = sdf.parse(string);
                    } catch (ParseException ex) {
                        Logger.getLogger(Data2DB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (line.contains("EPICS_CA_SERVER_PORT")) {
                    EPICS_CA_SERVER_PORT = line.substring(line.indexOf(":") + 1);
                } else if (line.contains("EPICS_CA_REPEATER_PORT")) {
                    EPICS_CA_REPEATER_PORT = line.substring(line.indexOf(":") + 1);
                } else if (line.toLowerCase().contains("record")) {
                    br.mark(s.length() + 1);
                } else if (line.toLowerCase().contains("ip")) {
                    ip = line.substring(line.indexOf(":") + 1);
                }

            }
            br.reset();
            while ((line = br.readLine()) != null) {
                if (!line.toLowerCase().contains("ip")) {
                    recordList.add(line);
                    //System.out.println(line);
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Data2DB.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  System.out.println(ip);
        ServerAPI serverAPI = new ServerAPI();
        Server server = serverAPI.getByIP(ip);

        if (server == null) {
            //初始化server_env表，只给env_name赋值
            server = serverAPI.setServer(ip);
            serverAPI.initServerEnv(server);
            //初始化epics_env表，只给epics_name赋值  
            serverAPI.initEpicsEnv(server);
        }

        //读server相关信息
        SSHInfo sshInfo = new SSHInfo("root", "sinap123", ip, 22);
        SSHExecutor ssh = new SSHExecutor(sshInfo);
        OSUtils os = new OSUtils();
        os.setSsh(ssh);
        Map<String, String> memMap = os.memoryinfo();
        Map<String, String> cpuMap = os.cpumodel();
        Map<String, Integer> fileMap = os.fileDesc();
        Map<String, String> serverEnvMap = os.serverEnv();
        //System.out.println(os.loadAverage());
        serverAPI.updateServer(server, os.startupTime(), new Date(), os.OSvern(),
                memMap.get("mem_total"), memMap.get("mem_free"), memMap.get("buffers"),
                memMap.get("cached"), String.valueOf(os.cpuUsage()), os.loadAverage(), cpuMap.get("cpu_model"),
                cpuMap.get("cpu_num"), fileMap.get("file_free"), fileMap.get("file_max"), serverEnvMap);
        // System.out.println(os.loadAverage());
        ssh.close();
        //读server相关信息结束

        //ioc相关信息
        IocAPI iocAPI = new IocAPI();
        Ioc ioc = iocAPI.getByName(ioc_name);
        if (ioc == null) {
            ioc = iocAPI.setIOC(server, ioc_name, install_path, date, new Date(), TRUE, EPICS_CA_SERVER_PORT, EPICS_CA_REPEATER_PORT);
        } else {
            ioc = iocAPI.updateIOC(ioc, install_path, date, new Date(), TRUE, EPICS_CA_SERVER_PORT, EPICS_CA_REPEATER_PORT);
        }
       // System.out.println("+++++++++ioc over");
        //record相关信息
        RecordAPI recordAPI = new RecordAPI();
        ArrayList<Record> rList = new ArrayList();
       // System.out.println(recordList);
        Iterator it = recordList.iterator();
        while (it.hasNext()) {
            String record_name = it.next().toString();
            Record r = recordAPI.getByName(record_name);
            if (r == null) {
                r = recordAPI.setRecord(ioc, record_name, TRUE);
            }
            rList.add(r);
        }
      //  System.out.println("++++读record type开始");
        //get record tpye from epics channel
     //   System.out.println(ioc.getName());
        CAChannelGet ca=new CAChannelGet();
        Map record_type_map = ca.getRecordType(ip,ioc.getName(), recordList);
     //    System.out.println("++++读record type结束");
        //IOC connection failure
        if (record_type_map.isEmpty()) {
            Iterator it1 = rList.iterator();
            while (it1.hasNext()) {
                Record r = (Record) it1.next();
                recordAPI.updateRecord(r, false);
            }
            iocAPI.updateIOC(ioc, false);
        } else {
            new RecordAPI().setRecordType(record_type_map);
            Iterator it2 = rList.iterator();
            while (it2.hasNext()) {
                Record r = (Record) it2.next();
                recordAPI.updateRecord(r, true);
            }
            iocAPI.updateIOC(ioc, true);
        }
    }

}
