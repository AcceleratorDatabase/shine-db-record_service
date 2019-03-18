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
@Table(name = "server")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Server.findAll", query = "SELECT s FROM Server s")
    , @NamedQuery(name = "Server.findById", query = "SELECT s FROM Server s WHERE s.id = :id")
    , @NamedQuery(name = "Server.findByIp", query = "SELECT s FROM Server s WHERE s.ip = :ip")
    , @NamedQuery(name = "Server.findByStartupTime", query = "SELECT s FROM Server s WHERE s.startupTime = :startupTime")
    , @NamedQuery(name = "Server.findByScanTime", query = "SELECT s FROM Server s WHERE s.scanTime = :scanTime")
    , @NamedQuery(name = "Server.findByOsVers", query = "SELECT s FROM Server s WHERE s.osVers = :osVers")
    , @NamedQuery(name = "Server.findByMemTotal", query = "SELECT s FROM Server s WHERE s.memTotal = :memTotal")
    , @NamedQuery(name = "Server.findByMemFree", query = "SELECT s FROM Server s WHERE s.memFree = :memFree")
    , @NamedQuery(name = "Server.findByBuffers", query = "SELECT s FROM Server s WHERE s.buffers = :buffers")
    , @NamedQuery(name = "Server.findByCached", query = "SELECT s FROM Server s WHERE s.cached = :cached")
    , @NamedQuery(name = "Server.findByCpuLoad", query = "SELECT s FROM Server s WHERE s.cpuLoad = :cpuLoad")
    , @NamedQuery(name = "Server.findByLoadAverage", query = "SELECT s FROM Server s WHERE s.loadAverage = :loadAverage")
    , @NamedQuery(name = "Server.findByCpuModel", query = "SELECT s FROM Server s WHERE s.cpuModel = :cpuModel")
    , @NamedQuery(name = "Server.findByCpuNum", query = "SELECT s FROM Server s WHERE s.cpuNum = :cpuNum")
    , @NamedQuery(name = "Server.findByFileFree", query = "SELECT s FROM Server s WHERE s.fileFree = :fileFree")
    , @NamedQuery(name = "Server.findByFileMax", query = "SELECT s FROM Server s WHERE s.fileMax = :fileMax")})
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ip")
    private String ip;
    @Column(name = "startup_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startupTime;
    @Column(name = "scan_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scanTime;
    @Column(name = "os_vers")
    private String osVers;
    @Column(name = "mem_total")
    private String memTotal;
    @Column(name = "mem_free")
    private String memFree;
    @Column(name = "buffers")
    private String buffers;
    @Column(name = "cached")
    private String cached;
    @Column(name = "cpu_load")
    private String cpuLoad;
    @Column(name = "load_average")
    private String loadAverage;
    @Column(name = "cpu_model")
    private String cpuModel;
    @Column(name = "cpu_num")
    private String cpuNum;
    @Column(name = "file_free")
    private Integer fileFree;
    @Column(name = "file_max")
    private Integer fileMax;
    @OneToMany(mappedBy = "servierId")
    private List<Ioc> iocList;
    @OneToMany(mappedBy = "serverId")
    private List<EpicsEnv> epicsEnvList;
    @OneToMany(mappedBy = "serverId")
    private List<ServerEnv> serverEnvList;

    public Server() {
    }

    public Server(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(Date startupTime) {
        this.startupTime = startupTime;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public String getOsVers() {
        return osVers;
    }

    public void setOsVers(String osVers) {
        this.osVers = osVers;
    }

    public String getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(String memTotal) {
        this.memTotal = memTotal;
    }

    public String getMemFree() {
        return memFree;
    }

    public void setMemFree(String memFree) {
        this.memFree = memFree;
    }

    public String getBuffers() {
        return buffers;
    }

    public void setBuffers(String buffers) {
        this.buffers = buffers;
    }

    public String getCached() {
        return cached;
    }

    public void setCached(String cached) {
        this.cached = cached;
    }

    public String getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(String cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getLoadAverage() {
        return loadAverage;
    }

    public void setLoadAverage(String loadAverage) {
        this.loadAverage = loadAverage;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public Integer getFileFree() {
        return fileFree;
    }

    public void setFileFree(Integer fileFree) {
        this.fileFree = fileFree;
    }

    public Integer getFileMax() {
        return fileMax;
    }

    public void setFileMax(Integer fileMax) {
        this.fileMax = fileMax;
    }

    @XmlTransient
    public List<Ioc> getIocList() {
        return iocList;
    }

    public void setIocList(List<Ioc> iocList) {
        this.iocList = iocList;
    }

    @XmlTransient
    public List<EpicsEnv> getEpicsEnvList() {
        return epicsEnvList;
    }

    public void setEpicsEnvList(List<EpicsEnv> epicsEnvList) {
        this.epicsEnvList = epicsEnvList;
    }

    @XmlTransient
    public List<ServerEnv> getServerEnvList() {
        return serverEnvList;
    }

    public void setServerEnvList(List<ServerEnv> serverEnvList) {
        this.serverEnvList = serverEnvList;
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
        if (!(object instanceof Server)) {
            return false;
        }
        Server other = (Server) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shine.db.record.entity.Server[ id=" + id + " ]";
    }
    
}
