/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.api;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import shine.db.record.common.tools.EmProvider;
import shine.db.record.entity.Server;
import shine.db.record.entity.ServerEnv;
import static shine.db.record.entity.Server_.ip;

/**
 *
 * @author Lvhuihui
 */
public class ServerEnvAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public ServerEnv setServerEnv(String name, String value) {
        ServerEnv serverEnv = new ServerEnv();
        serverEnv.setEnvName(name);
        serverEnv.setEnvValue(value);
        em.getTransaction().begin();
        em.persist(serverEnv);
        em.getTransaction().commit();
        return serverEnv;
    }

    public ServerEnv setServerEnv(String name, String value, Server server) {
        ServerEnv serverEnv = new ServerEnv();
        serverEnv.setEnvName(name);
        serverEnv.setEnvValue(value);
        serverEnv.setServerId(server);
        em.getTransaction().begin();
        em.persist(serverEnv);
        em.getTransaction().commit();
        return serverEnv;
    }
    
    public ServerEnv getServerEnv(String name, Server server) {
        Query q;
        q = em.createQuery("select se from ServerEnv se where se.serverId=:serverId "
                + "and se.envName=:envName").setParameter("serverId", server).setParameter("envName", name);
        List<ServerEnv> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }
    
    public void updateServerEnv(Server server, String name, String value) {
        ServerEnv serverEnv = this.getServerEnv(name, server);
        serverEnv.setEnvValue(value);
        em.getTransaction().begin();
        em.merge(serverEnv);
        em.getTransaction().commit();   
    }


}
