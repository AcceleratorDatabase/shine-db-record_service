/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.api;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import shine.db.record.common.constants.EpicsEnvName;
import shine.db.record.common.tools.EmProvider;
import shine.db.record.entity.EpicsEnv;
import shine.db.record.entity.Server;
import shine.db.record.entity.ServerEnv;

/**
 *
 * @author Lvhuihui
 */
public class EpicsEnvAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public EpicsEnv setEpicsEnv(String name, String value) {
        EpicsEnv epicsEnv = new EpicsEnv();
        epicsEnv.setEpicsEnvName(name);
        epicsEnv.setEpicsEnvValue(value);
        em.getTransaction().begin();
        em.persist(epicsEnv);
        em.getTransaction().commit();
        return epicsEnv;
    }

    public EpicsEnv setEpicsEnv(Server server, String name, String value) {
        EpicsEnv epicsEnv = new EpicsEnv();
        epicsEnv.setEpicsEnvName(name);
        epicsEnv.setEpicsEnvValue(value);
        epicsEnv.setServerId(server);
        em.getTransaction().begin();
        em.persist(epicsEnv);
        em.getTransaction().commit();
        return epicsEnv;
    }

    public EpicsEnv getEpicsEnv(Server server, String name) {
        Query q;
        q = em.createQuery("select ee from EpicsEnv ee  where ee.serverId=:serverId "
                + "and ee.epicsEnvName=:envName").setParameter("serverId", server).setParameter("envName", name);
        List<EpicsEnv> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    public void updateEpicsEnv(Server server, String name, String value) {
        EpicsEnv epicsEnv = this.getEpicsEnv(server,name);
        epicsEnv.setEpicsEnvValue(value);       
        em.getTransaction().begin();
        em.merge(epicsEnv);
        em.getTransaction().commit();
    }

    //Map:key-env_name, value-env_value
    public void setEpicsEnv(Server server, Map<String, String> map) {
        if ((server != null) && (!map.isEmpty())) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                EpicsEnv epicsEnv = this.getEpicsEnv(server, entry.getKey());
                if (epicsEnv == null) {
                    this.setEpicsEnv(server, entry.getKey(), entry.getValue());
                } else {
                    this.updateEpicsEnv(server, entry.getKey(), entry.getValue());
                }
            }
        }
    }
    /*
     public Server setEpicsEnv(Server s, Date startup_time, Date scan_time, String os_vers,
            String memTotal, String memFree, String buffers, String cached, String cpu_load,
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
     */
}
