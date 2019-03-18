/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lvhuihui
 */
@Entity
@Table(name = "ioc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ioc.findAll", query = "SELECT i FROM Ioc i")
    , @NamedQuery(name = "Ioc.findById", query = "SELECT i FROM Ioc i WHERE i.id = :id")
    , @NamedQuery(name = "Ioc.findByName", query = "SELECT i FROM Ioc i WHERE i.name = :name")
    , @NamedQuery(name = "Ioc.findByInstallPath", query = "SELECT i FROM Ioc i WHERE i.installPath = :installPath")
    , @NamedQuery(name = "Ioc.findByBootTime", query = "SELECT i FROM Ioc i WHERE i.bootTime = :bootTime")
    , @NamedQuery(name = "Ioc.findByScanTime", query = "SELECT i FROM Ioc i WHERE i.scanTime = :scanTime")
    , @NamedQuery(name = "Ioc.findByActive", query = "SELECT i FROM Ioc i WHERE i.active = :active")
    , @NamedQuery(name = "Ioc.findByEpicsCaServerPort", query = "SELECT i FROM Ioc i WHERE i.epicsCaServerPort = :epicsCaServerPort")
    , @NamedQuery(name = "Ioc.findByEpicsCaRepeaterPort", query = "SELECT i FROM Ioc i WHERE i.epicsCaRepeaterPort = :epicsCaRepeaterPort")})
public class Ioc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "install_path")
    private String installPath;
    @Column(name = "boot_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bootTime;
    @Column(name = "scan_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scanTime;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "epics_ca_server_port")
    private String epicsCaServerPort;
    @Column(name = "epics_ca_repeater_port")
    private String epicsCaRepeaterPort;
    @OneToMany(mappedBy = "iocId")
    private List<Record> recordList;
    @JoinColumn(name = "servier_id", referencedColumnName = "id")
    @ManyToOne
    private Server servierId;

    public Ioc() {
    }

    public Ioc(Integer id) {
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

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public Date getBootTime() {
        return bootTime;
    }

    public void setBootTime(Date bootTime) {
        this.bootTime = bootTime;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEpicsCaServerPort() {
        return epicsCaServerPort;
    }

    public void setEpicsCaServerPort(String epicsCaServerPort) {
        this.epicsCaServerPort = epicsCaServerPort;
    }

    public String getEpicsCaRepeaterPort() {
        return epicsCaRepeaterPort;
    }

    public void setEpicsCaRepeaterPort(String epicsCaRepeaterPort) {
        this.epicsCaRepeaterPort = epicsCaRepeaterPort;
    }

    @XmlTransient
    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public Server getServierId() {
        return servierId;
    }

    public void setServierId(Server servierId) {
        this.servierId = servierId;
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
        if (!(object instanceof Ioc)) {
            return false;
        }
        Ioc other = (Ioc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.Ioc[ id=" + id + " ]";
    }
    
}
