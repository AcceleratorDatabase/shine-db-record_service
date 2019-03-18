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
@Table(name = "record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Record.findAll", query = "SELECT r FROM Record r")
    , @NamedQuery(name = "Record.findById", query = "SELECT r FROM Record r WHERE r.id = :id")
    , @NamedQuery(name = "Record.findByName", query = "SELECT r FROM Record r WHERE r.name = :name")
    , @NamedQuery(name = "Record.findByActive", query = "SELECT r FROM Record r WHERE r.active = :active")})
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "ioc_id", referencedColumnName = "id")
    @ManyToOne
    private Ioc iocId;
    @JoinColumn(name = "record_type_id", referencedColumnName = "id")
    @ManyToOne
    private RecordType recordTypeId;
    @OneToMany(mappedBy = "recordId")
    private List<FieldValue> fieldValueList;

    public Record() {
    }

    public Record(Integer id) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Ioc getIocId() {
        return iocId;
    }

    public void setIocId(Ioc iocId) {
        this.iocId = iocId;
    }

    public RecordType getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(RecordType recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    @XmlTransient
    public List<FieldValue> getFieldValueList() {
        return fieldValueList;
    }

    public void setFieldValueList(List<FieldValue> fieldValueList) {
        this.fieldValueList = fieldValueList;
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
        if (!(object instanceof Record)) {
            return false;
        }
        Record other = (Record) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.Record[ id=" + id + " ]";
    }
    
}
