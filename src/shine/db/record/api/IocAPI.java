/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import shine.db.record.common.tools.EmProvider;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;
import shine.db.record.entity.Server;

/**
 *
 * @author Lvhuihui
 */
public class IocAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Ioc setIOC(Server server, String name, String install_path, Date boot_time, Boolean active) {
        Ioc ioc = new Ioc();
        ioc.setServierId(server);
        ioc.setName(name);
        ioc.setInstallPath(install_path);
        ioc.setBootTime(boot_time);
        ioc.setActive(active);
        em.getTransaction().begin();
        em.persist(ioc);
        em.getTransaction().commit();
        return ioc;
    }

    public Ioc setIOC(Server server, String name, String install_path, Date boot_time, Date scan_tiem, Boolean active) {
        Ioc ioc = new Ioc();
        ioc.setServierId(server);
        ioc.setName(name);
        ioc.setInstallPath(install_path);
        ioc.setBootTime(boot_time);
        ioc.setScanTime(scan_tiem);
        ioc.setActive(active);
        em.getTransaction().begin();
        em.persist(ioc);
        em.getTransaction().commit();
        return ioc;
    }

    public Ioc setIOC(Server server, String name, String install_path, Date boot_time,
            Date scan_tiem, Boolean active, String epics_ca_server_port, String epics_ca_repeater_port) {
        Ioc ioc = new Ioc();
        ioc.setServierId(server);
        ioc.setName(name);
        ioc.setInstallPath(install_path);
        ioc.setBootTime(boot_time);
        ioc.setScanTime(scan_tiem);
        ioc.setActive(active);
        ioc.setEpicsCaServerPort(epics_ca_server_port);
        ioc.setEpicsCaRepeaterPort(epics_ca_repeater_port);
        em.getTransaction().begin();
        em.persist(ioc);
        em.getTransaction().commit();
        return ioc;
    }

    public Ioc updateIOC(Ioc ioc, String install_path, Date boot_time, Date scan_tiem, Boolean active) {
        ioc.setInstallPath(install_path);
        ioc.setBootTime(boot_time);
        ioc.setScanTime(scan_tiem);
        ioc.setActive(active);
        em.getTransaction().begin();
        em.merge(ioc);
        em.getTransaction().commit();
        return ioc;
    }

    public Ioc updateIOC(Ioc ioc, String install_path, Date boot_time, Date scan_tiem, Boolean active, String epics_ca_server_port, String epics_ca_repeater_port) {
        ioc.setInstallPath(install_path);
        ioc.setBootTime(boot_time);
        ioc.setScanTime(scan_tiem);
        ioc.setActive(active);
        ioc.setEpicsCaServerPort(epics_ca_server_port);
        ioc.setEpicsCaRepeaterPort(epics_ca_repeater_port);
        em.getTransaction().begin();
        em.merge(ioc);
        em.getTransaction().commit();
        return ioc;
    }

    public Ioc updateIOC(Ioc ioc, Boolean active) {
        ioc.setActive(active);
        em.getTransaction().begin();
        em.merge(ioc);
        em.getTransaction().commit();
        return ioc;
    }

    public Ioc getByName(String name) {
        Query q;
        q = em.createNamedQuery("Ioc.findByName").setParameter("name", name);
        List<Ioc> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    public List<Record> getConnectedRecordList(Ioc ioc) {
        Query q;
        q = em.createQuery("select r from Record r where r.iocId=:ioc and r.active=:active")
                .setParameter("ioc", ioc).setParameter("active", true);
        List<Record> aList = q.getResultList();
        return aList;
    }
}
