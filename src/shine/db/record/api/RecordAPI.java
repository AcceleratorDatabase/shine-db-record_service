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
import shine.db.record.common.tools.EmProvider;
import shine.db.record.entity.Ioc;
import shine.db.record.entity.Record;
import shine.db.record.entity.RecordType;

/**
 *
 * @author Lvhuihui
 */
public class RecordAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Record setRecord(Ioc ioc, String name, Boolean active) {
        Record record = new Record();
        record.setIocId(ioc);
        record.setName(name);
        record.setActive(active);
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        return record;
    }

    public void updateRecord(Record record, Boolean active) {;
        record.setActive(active);
        em.getTransaction().begin();
        em.merge(record);
        em.getTransaction().commit();
    }

    public Record getByName(String name) {
        Query q;
        q = em.createNamedQuery("Record.findByName").setParameter("name", name);
        List<Record> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }
    //map key:record name  value:record type

    public void setRecordType(Map<String, String> map) {
        RecordTypeAPI rtapi = new RecordTypeAPI();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            String type = entry.getValue();
            Record record = this.getByName(name);
            if (record != null) {
                RecordType rt = rtapi.getRecordType(type.toLowerCase());
                em.getTransaction().begin();
                if (rt != null) {
                    record.setRecordTypeId(rt);
                    em.merge(record);
                } else {
                    //如果RecordType不存在，将common的FieldTypeList赋值给新建的RecordType
                    rt = new RecordType();
                    rt.setType(type);
                    rt.setFieldTypeList(rtapi.getCommonFieldTypeList());
                                       
                    record.setRecordTypeId(rt);
                    em.persist(rt);
                    em.merge(record);
                     
                }
                em.getTransaction().commit();
                 //System.out.println(rt.getType()+"*******"+rt.getFieldTypeList().size());
            }
        }

    }

}
