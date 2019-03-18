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
@Table(name = "epics_env")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EpicsEnv.findAll", query = "SELECT e FROM EpicsEnv e")
    , @NamedQuery(name = "EpicsEnv.findById", query = "SELECT e FROM EpicsEnv e WHERE e.id = :id")
    , @NamedQuery(name = "EpicsEnv.findByEpicsEnvName", query = "SELECT e FROM EpicsEnv e WHERE e.epicsEnvName = :epicsEnvName")
    , @NamedQuery(name = "EpicsEnv.findByEpicsEnvValue", query = "SELECT e FROM EpicsEnv e WHERE e.epicsEnvValue = :epicsEnvValue")})
public class EpicsEnv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "epics_env_name")
    private String epicsEnvName;
    @Column(name = "epics_env_value")
    private String epicsEnvValue;
    @JoinColumn(name = "server_id", referencedColumnName = "id")
    @ManyToOne
    private Server serverId;

    public EpicsEnv() {
    }

    public EpicsEnv(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEpicsEnvName() {
        return epicsEnvName;
    }

    public void setEpicsEnvName(String epicsEnvName) {
        this.epicsEnvName = epicsEnvName;
    }

    public String getEpicsEnvValue() {
        return epicsEnvValue;
    }

    public void setEpicsEnvValue(String epicsEnvValue) {
        this.epicsEnvValue = epicsEnvValue;
    }

    public Server getServerId() {
        return serverId;
    }

    public void setServerId(Server serverId) {
        this.serverId = serverId;
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
        if (!(object instanceof EpicsEnv)) {
            return false;
        }
        EpicsEnv other = (EpicsEnv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.EpicsEnv[ id=" + id + " ]";
    }
    
}
