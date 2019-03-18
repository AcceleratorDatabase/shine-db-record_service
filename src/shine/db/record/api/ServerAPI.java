/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import shine.db.record.common.constants.EnvName;
import shine.db.record.common.constants.EpicsEnvName;
import shine.db.record.common.tools.EmProvider;
import shine.db.record.entity.EpicsEnv;
import shine.db.record.entity.Server;
import shine.db.record.entity.ServerEnv;

/**
 *
 * @author Lvhuihui
 */
public class ServerAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Server setServer(String ip) {
        Server server = new Server();
        server.setIp(ip);
        em.getTransaction().begin();
        em.persist(server);
        em.getTransaction().commit();
        return server;
    }

    public void initServerEnv(Server s) {
        em.getTransaction().begin();
        ArrayList<ServerEnv> serverEnvList = new ArrayList();
        Iterator<String> it = EnvName.ENVLIST.iterator();
        while (it.hasNext()) {
            String name = it.next();
            ServerEnv se = new ServerEnv();
            se.setEnvName(name);
            se.setServerId(s);
            em.persist(se);
            serverEnvList.add(se);
        }
        s.setServerEnvList(serverEnvList);
        em.getTransaction().commit();
    }

    public void initEpicsEnv(Server s) {
        em.getTransaction().begin();
        ArrayList<EpicsEnv> epicsEnvList = new ArrayList();
        for (Map.Entry<String, String> entry : EpicsEnvName.ENVLIST.entrySet()) {
            String name = entry.getKey();
            EpicsEnv se = new EpicsEnv();
            se.setEpicsEnvName(name);
            se.setServerId(s);
            em.persist(se);
            epicsEnvList.add(se);
        }
        s.setEpicsEnvList(epicsEnvList);
        em.getTransaction().commit();
    }

    public Server getByIP(String ip) {
        Query q;
        q = em.createNamedQuery("Server.findByIp").setParameter("ip", ip);
        List<Server> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    public List<Server> getAllServer() {
        Query q;
        q = em.createNamedQuery("Server.findAll");
        List<Server> list = q.getResultList();
        return list;
    }

    public Server setServer(String ip, Date startup_time, Date scan_time, String os_vers,
            String memTotal, String memFree, String buffers, String cached, String cpu_load,String load_average,
            String cpu_model, String cpu_num, int file_free, int file_max) {
        Server s = new Server();
        s.setIp(ip);
        s.setStartupTime(startup_time);
        s.setScanTime(scan_time);
        s.setOsVers(os_vers);
        s.setMemTotal(memTotal);
        s.setMemFree(memFree);
        s.setBuffers(buffers);
        s.setCached(cached);
        s.setCpuLoad(cpu_load);
        s.setLoadAverage(load_average);
        s.setCpuModel(cpu_model);
        s.setCpuNum(cpu_num);
        s.setFileFree(file_free);
        s.setFileMax(file_max);
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }

    public Server updateServer(Server s, Date startup_time, Date scan_time, String os_vers,
            String memTotal, String memFree, String buffers, String cached, String cpu_load,String load_average,
            String cpu_model, String cpu_num, int file_free, int file_max) {
        s.setStartupTime(startup_time);
        s.setScanTime(scan_time);
        s.setOsVers(os_vers);
        s.setMemTotal(memTotal);
        s.setMemFree(memFree);
        s.setBuffers(buffers);
        s.setCached(cached);
        s.setCpuLoad(cpu_load);
        s.setLoadAverage(load_average);
        s.setCpuModel(cpu_model);
        s.setCpuNum(cpu_num);
        s.setFileFree(file_free);
        s.setFileMax(file_max);
        em.getTransaction().begin();
        em.merge(s);
        em.getTransaction().commit();
        return s;
    }

    public Server updateServer(Server s, Date startup_time, Date scan_time, String os_vers,
            String memTotal, String memFree, String buffers, String cached, String cpu_load,String load_average,
            String cpu_model, String cpu_num, int file_free, int file_max, Map<String, String> serverEnvMap) {
        em.getTransaction().begin();
        s.setStartupTime(startup_time);
        s.setScanTime(scan_time);
        s.setOsVers(os_vers);
        s.setMemTotal(memTotal);
        s.setMemFree(memFree);
        s.setBuffers(buffers);
        s.setCached(cached);
        s.setCpuLoad(cpu_load);
        s.setLoadAverage(load_average);
        s.setCpuModel(cpu_model);
        s.setCpuNum(cpu_num);
        s.setFileFree(file_free);
        s.setFileMax(file_max);

        List<ServerEnv> serverEnvList = s.getServerEnvList();

        ServerEnvAPI serverEnvAPI = new ServerEnvAPI();
        Iterator it = serverEnvList.iterator();
        while (it.hasNext()) {
            ServerEnv serverEnv = (ServerEnv) it.next();
            serverEnvAPI.updateServerEnv(s, serverEnv.getEnvName(), serverEnvMap.get(serverEnv.getEnvName()));
        }

        em.merge(s);
        em.getTransaction().commit();
        return s;
    }

}
