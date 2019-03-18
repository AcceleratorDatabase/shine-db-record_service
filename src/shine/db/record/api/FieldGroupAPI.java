/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.api;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import shine.db.record.common.tools.EmProvider;
import shine.db.record.entity.FieldGroup;
import shine.db.record.entity.FieldType;
import shine.db.record.entity.RecordType;

/**
 *
 * @author Lvhuihui
 */
public class FieldGroupAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public FieldGroup setFieldGroup(String group) {
        FieldGroup f = new FieldGroup();
        f.setName(group);
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        return f;
    }

    public FieldGroup getFieldGroup(String group) {
        Query q;
        q = em.createNamedQuery("FieldGroup.findByName").setParameter("name", group);
        List<FieldGroup> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

}
