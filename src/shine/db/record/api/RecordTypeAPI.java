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
import shine.db.record.entity.FieldType;
import shine.db.record.entity.RecordType;

/**
 *
 * @author Lvhuihui
 */
public class RecordTypeAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public RecordType setRecordType(String type) {
        RecordType rt = new RecordType();
        rt.setType(type);
        em.getTransaction().begin();
        em.persist(rt);
        em.getTransaction().commit();
        return rt;
    }

    public RecordType getRecordType(String type) {
        Query q;
        q = em.createNamedQuery("RecordType.findByType").setParameter("type", type);
        List<RecordType> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    public List<RecordType> getAllRecordType() {
        Query q;
        q = em.createNamedQuery("RecordType.findAll");
        List<RecordType> list = q.getResultList();
        return list;
    }

    //add new FieldName,由record_type维护多对多映射关系
    public void setFieldTypeList(RecordType rt, List<FieldType> fList) {
        if (rt != null && (!"".equals(rt))) {
            em.getTransaction().begin();
            List<FieldType> list = rt.getFieldTypeList();
            list.addAll(fList);
            HashSet h = new HashSet(list);
            list.clear();
            list.addAll(h);            
            rt.setFieldTypeList(list);
            em.merge(rt);
            em.getTransaction().commit();
           // System.out.println(rt.getFieldTypeList());
        }
    }

    public List<FieldType> getCommonFieldTypeList() {
        RecordType rt = this.getRecordType("common");
        if (rt != null) {
            return rt.getFieldTypeList();
        }
        return null;
    }
}
