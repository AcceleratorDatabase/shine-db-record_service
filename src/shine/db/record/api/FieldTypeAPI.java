/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.api;

import java.util.ArrayList;
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
public class FieldTypeAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public FieldType setFieldType(String name) {
        FieldType f = new FieldType();
        f.setName(name);
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        return f;
    }

    public FieldType setFieldType(String name, String desc, String group) {
        FieldType f = this.getFieldType(name, group);
        if (f == null) {
            f=new FieldType();
            f.setName(name);
            f.setDesp(desc);
            FieldGroup fg;          
            if (!"".equals(group) && group != null) {
                FieldGroupAPI fgAPI = new FieldGroupAPI();
                fg = fgAPI.getFieldGroup(group);
                if (fg == null) {
                    fg = fgAPI.setFieldGroup(group);
                    //em.persist(fg);
                }
                f.setFieldGroupId(fg);
            }
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
        }
        return f;
    }

    public FieldType getFieldType(String name) {
        Query q;
        q = em.createNamedQuery("FieldType.findByName").setParameter("name", name);
        List<FieldType> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    public FieldType getFieldType(String name, String group) {
        Query q;
        // q = em.createQuery("SELECT m FROM Model m WHERE m.initialConditionInd=:ind").setParameter("ind", 1);

        q = em.createQuery("SELECT  ft FROM FieldType ft WHERE ft.name=:name and"
                + " ft.fieldGroupId.name=:groupName").setParameter("name", name).setParameter("groupName", group);
        List<FieldType> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    //add new RecordType
   /* public void setRecordTypeList(FieldType f, List<RecordType> rtList) {
        if (f != null && (!"".equals(f))) {
            List<RecordType> list = f.getRecordTypeList();
            list.addAll(rtList);
            HashSet h = new HashSet(list);
            list.clear();
            list.addAll(h);
            f.setRecordTypeList(list);
            em.getTransaction().begin();
            em.merge(f);
            em.getTransaction().commit();
        }
    }*/
    
   
}
