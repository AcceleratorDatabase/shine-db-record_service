/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lvhuihui
 */
@Entity
@Table(name = "record_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecordType.findAll", query = "SELECT r FROM RecordType r")
    , @NamedQuery(name = "RecordType.findById", query = "SELECT r FROM RecordType r WHERE r.id = :id")
    , @NamedQuery(name = "RecordType.findByType", query = "SELECT r FROM RecordType r WHERE r.type = :type")})
public class RecordType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    private String type;
    @JoinTable(name = "record_type_field_type", joinColumns = {
        @JoinColumn(name = "record_type_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "field_type_id", referencedColumnName = "id")})
    @ManyToMany
    private List<FieldType> fieldTypeList;
    @OneToMany(mappedBy = "recordTypeId")
    private List<Record> recordList;

    public RecordType() {
    }

    public RecordType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public List<FieldType> getFieldTypeList() {
        return fieldTypeList;
    }

    public void setFieldTypeList(List<FieldType> fieldTypeList) {
        this.fieldTypeList = fieldTypeList;
    }

    @XmlTransient
    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecordType)) {
            return false;
        }
        RecordType other = (RecordType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.RecordType[ id=" + id + " ]";
    }
    
}
