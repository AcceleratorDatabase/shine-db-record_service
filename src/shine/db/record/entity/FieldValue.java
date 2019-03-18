/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lvhuihui
 */
@Entity
@Table(name = "field_value")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FieldValue.findAll", query = "SELECT f FROM FieldValue f")
    , @NamedQuery(name = "FieldValue.findById", query = "SELECT f FROM FieldValue f WHERE f.id = :id")
    , @NamedQuery(name = "FieldValue.findByValue", query = "SELECT f FROM FieldValue f WHERE f.value = :value")})
public class FieldValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "field_type_id", referencedColumnName = "id")
    @ManyToOne
    private FieldType fieldTypeId;
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    @ManyToOne
    private Record recordId;

    public FieldValue() {
    }

    public FieldValue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FieldType getFieldTypeId() {
        return fieldTypeId;
    }

    public void setFieldTypeId(FieldType fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public Record getRecordId() {
        return recordId;
    }

    public void setRecordId(Record recordId) {
        this.recordId = recordId;
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
        if (!(object instanceof FieldValue)) {
            return false;
        }
        FieldValue other = (FieldValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.FieldValue[ id=" + id + " ]";
    }
    
}
