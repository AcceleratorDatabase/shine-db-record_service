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
import javax.persistence.ManyToOne;
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
@Table(name = "field_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FieldType.findAll", query = "SELECT f FROM FieldType f")
    , @NamedQuery(name = "FieldType.findById", query = "SELECT f FROM FieldType f WHERE f.id = :id")
    , @NamedQuery(name = "FieldType.findByName", query = "SELECT f FROM FieldType f WHERE f.name = :name")
    , @NamedQuery(name = "FieldType.findByDesp", query = "SELECT f FROM FieldType f WHERE f.desp = :desp")})
public class FieldType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "desp")
    private String desp;
    
    @ManyToMany(mappedBy = "fieldTypeList")
    private List<RecordType> recordTypeList;
    @OneToMany(mappedBy = "fieldTypeId")
    private List<FieldValue> fieldValueList;
    @JoinColumn(name = "field_group_id", referencedColumnName = "id")
    @ManyToOne
    private FieldGroup fieldGroupId;

    public FieldType() {
    }

    public FieldType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    @XmlTransient
    public List<RecordType> getRecordTypeList() {
        return recordTypeList;
    }

    public void setRecordTypeList(List<RecordType> recordTypeList) {
        this.recordTypeList = recordTypeList;
    }

    @XmlTransient
    public List<FieldValue> getFieldValueList() {
        return fieldValueList;
    }

    public void setFieldValueList(List<FieldValue> fieldValueList) {
        this.fieldValueList = fieldValueList;
    }

    public FieldGroup getFieldGroupId() {
        return fieldGroupId;
    }

    public void setFieldGroupId(FieldGroup fieldGroupId) {
        this.fieldGroupId = fieldGroupId;
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
        if (!(object instanceof FieldType)) {
            return false;
        }
        FieldType other = (FieldType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.FieldType[ id=" + id + " ]";
    }
    
}
