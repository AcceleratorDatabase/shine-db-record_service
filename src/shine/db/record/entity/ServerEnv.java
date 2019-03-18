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
@Table(name = "server_env")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServerEnv.findAll", query = "SELECT s FROM ServerEnv s")
    , @NamedQuery(name = "ServerEnv.findById", query = "SELECT s FROM ServerEnv s WHERE s.id = :id")
    , @NamedQuery(name = "ServerEnv.findByEnvName", query = "SELECT s FROM ServerEnv s WHERE s.envName = :envName")
    , @NamedQuery(name = "ServerEnv.findByEnvValue", query = "SELECT s FROM ServerEnv s WHERE s.envValue = :envValue")})
public class ServerEnv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "env_name")
    private String envName;
    @Column(name = "env_value")
    private String envValue;
    @JoinColumn(name = "server_id", referencedColumnName = "id")
    @ManyToOne
    private Server serverId;

    public ServerEnv() {
    }

    public ServerEnv(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getEnvValue() {
        return envValue;
    }

    public void setEnvValue(String envValue) {
        this.envValue = envValue;
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
        if (!(object instanceof ServerEnv)) {
            return false;
        }
        ServerEnv other = (ServerEnv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.ServerEnv[ id=" + id + " ]";
    }
    
}
