/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.initFields;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import shine.db.record.api.FieldTypeAPI;
import shine.db.record.api.RecordTypeAPI;
import shine.db.record.entity.FieldType;
import shine.db.record.entity.RecordType;

/**
 *
 * @author Lvhuihui
 */
public class Fields2DB {

    public Fields2DB() {
    }

    public static void setFields(String record_type, ArrayList filedList) {
        RecordTypeAPI rapi = new RecordTypeAPI();
        RecordType rt = rapi.getRecordType(record_type);

        if (rt == null || ("".equals(rt))) {
            rt = rapi.setRecordType(record_type);
        }

        FieldTypeAPI fapi = new FieldTypeAPI();
        FieldType fieldType = new FieldType();
        ArrayList fieldTypeList = new ArrayList();
        for (Iterator rit = filedList.iterator(); rit.hasNext();) {
          //  ArrayList<RecordType> rList = new ArrayList();
          //  rList.add(rt);
            ArrayList oneRow = (ArrayList) rit.next();
            String field_name = (String) oneRow.get(0);
            String field_desp = (String) oneRow.get(1);
            String filed_group = (String) oneRow.get(2);
            fieldType = fapi.getFieldType(field_name, filed_group);
          //  System.out.println("*****"+i+fieldType+"*********"+field_name+filed_group);
          
            if (fieldType == null) {
                fieldType = fapi.setFieldType(field_name, field_desp, filed_group);
            }
            fieldTypeList.add(fieldType);

            /*    if(fieldName.getRecordTypeList()!=null&&(!"".equals(fieldName.getRecordTypeList()))){
                  rList.addAll(fieldName.getRecordTypeList());                 
            }*/
            //System.out.println(rList);
            //  fieldName.setRecordTypeList(rList);
         //   fapi.setRecordTypeList(fieldType, rList);
        }
        // rt.setFieldNameList(fieldList);
        rapi.setFieldTypeList(rt, fieldTypeList);

    }

    public static void setCommanFields(ArrayList filedList) {
        RecordTypeAPI rapi = new RecordTypeAPI();
        RecordType rt = rapi.getRecordType("common");
        if (rt == null || ("".equals(rt))) {
            rt = rapi.setRecordType("common");
        }
       // List<RecordType> rList = rapi.getAllRecordType();
        //System.out.println(rList);
        List<FieldType> fieldTypeList = new ArrayList();
        FieldType field_type = new FieldType();
        FieldTypeAPI fapi = new FieldTypeAPI();
        for (Iterator it = filedList.iterator(); it.hasNext();) {
            ArrayList oneRow = (ArrayList) it.next();
            String field_name = (String) oneRow.get(0);
            String field_desp = (String) oneRow.get(1);
            String filed_group = (String) oneRow.get(2);
            field_type = fapi.getFieldType(field_name);
            if (field_type == null) {
                field_type = fapi.setFieldType(field_name, field_desp, filed_group);
                
             //   fapi.setRecordTypeList(field_type, rList);
               // fieldTypeList= new ArrayList();
            }      
            fieldTypeList.add(field_type);
        }
        rapi.setFieldTypeList(rt, fieldTypeList);
        
       /* for (Iterator rit = rList.iterator(); rit.hasNext();) {
           List<FieldType> fList = new ArrayList();
            fList.addAll(fieldTypeList);
            RecordType record_type = (RecordType) rit.next();
            fList.addAll(record_type.getFieldTypeList());
              //System.out.println(fList);
            // record_type.setFieldNameList(fList);
            rapi.setFieldTypeList(record_type, fList);
        }*/
    }

}
