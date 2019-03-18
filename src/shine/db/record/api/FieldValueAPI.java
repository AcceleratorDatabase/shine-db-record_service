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
import shine.db.record.entity.FieldType;
import shine.db.record.entity.FieldValue;
import shine.db.record.entity.Record;

/**
 *
 * @author Lvhuihui
 */
public class FieldValueAPI {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public void setFieldValue(Record record, FieldType filedType, Object value) {
        em.getTransaction().begin();
        FieldValue field_value = this.getFieldValue(record, filedType);
        if (field_value == null) {
            field_value = new FieldValue();
            field_value.setRecordId(record);
            field_value.setFieldTypeId(filedType);
            field_value.setValue(String.valueOf(value));
            em.persist(field_value);
            em.getTransaction().commit();
        } else {
            if (!field_value.getValue().equals(value)) {
                field_value.setValue(String.valueOf(value));
                em.merge(field_value);
                em.getTransaction().commit();
            }
        }

    }

    public FieldValue getFieldValue(Record record, FieldType field_name) {
        Query q;
        q = em.createQuery("select fv from FieldValue fv where fv.recordId=:record and fv.fieldTypeId=:field_type").setParameter("record", record).setParameter("field_type", field_name);
        List<FieldValue> dList = q.getResultList();
        if (dList.isEmpty()) {
            return null;
        } else {
            return dList.get(0);
        }
    }

    //map: key:FieldName vlaue:field_value
    public void setFieldValueList(Record record, Map<FieldType, String> map) {
        em.getTransaction().begin();
        // Boolean sign = true;
        for (Map.Entry<FieldType, String> entry : map.entrySet()) {
            FieldType field_name = entry.getKey();
            String value = entry.getValue();
            FieldValue field_value = this.getFieldValue(record, field_name);
            if (field_value == null) {
                field_value = new FieldValue();
                field_value.setRecordId(record);
                field_value.setFieldTypeId(field_name);
                field_value.setValue(value);
                em.persist(field_value);
                // sign = false;
            } else {
                if (!field_value.getValue().equals(value)) {
                    field_value.setValue(value);
                    em.merge(field_value);
                    //   sign = false;
                }
            }
        }
        // if (!sign) {
        em.getTransaction().commit();
        // }

    }

    //fieldMapList中每个Map是一个fieldName——value对
    public void setFieldsValueForRecordList(List<Record> record_list, List<Map<FieldType, String>> fieldMapList) {

        if (!record_list.isEmpty() && record_list.size() == fieldMapList.size()) {
            em.getTransaction().begin();
            // Boolean sign = true;
            for (int i = 0; i < record_list.size(); i++) {
                Record record = record_list.get(i);
                Map<FieldType, String> map = fieldMapList.get(i);
                //System.out.println("&&&&&&&&&&"+map.size());
                if (!map.isEmpty()) {
                    for (Map.Entry<FieldType, String> entry : map.entrySet()) {
                        FieldType field_name = entry.getKey();
                        String value = entry.getValue();
                        //System.out.println("*********"+value);
                        FieldValue field_value = this.getFieldValue(record, field_name);
                        if (field_value == null) {
                            field_value = new FieldValue();
                            field_value.setRecordId(record);
                            field_value.setFieldTypeId(field_name);
                            field_value.setValue(value);
                            em.persist(field_value);
                            // sign = false;
                        } else {
                            // System.out.println(record.getName() + "--------" + field_name);
                            // System.out.println("++++++++++" + field_value.getValue() + "------" + value);
                            if (field_value.getValue() != null) {
                                if (!field_value.getValue().equals(value)) {
                                    field_value.setValue(value);
                                    em.merge(field_value);
                                    //  sign = false;
                                }
                            } else {
                                field_value.setValue(value);
                                em.merge(field_value);
                            }
                        }
                    }
                }
            }
            // if (!sign) {
            em.getTransaction().commit();
            // }
        }
    }
}
