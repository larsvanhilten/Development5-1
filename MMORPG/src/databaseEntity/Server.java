/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseEntity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lars
 */
@Entity
@Table(name = "server")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Server.findAll", query = "SELECT s FROM Server s"),
    @NamedQuery(name = "Server.findByAddress", query = "SELECT s FROM Server s WHERE s.address = :address"),
    @NamedQuery(name = "Server.findByName", query = "SELECT s FROM Server s WHERE s.name = :name"),
    @NamedQuery(name = "Server.findByLocation", query = "SELECT s FROM Server s WHERE s.location = :location"),
    @NamedQuery(name = "Server.findByMaxUsers", query = "SELECT s FROM Server s WHERE s.maxUsers = :maxUsers"),
    @NamedQuery(name = "Server.findByConnectedUsers", query = "SELECT s FROM Server s WHERE s.connectedUsers = :connectedUsers")})
public class Server implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "max_users")
    private Integer maxUsers;
    @Column(name = "connected_users")
    private Integer connectedUsers;
    @JoinTable(name = "store", joinColumns = {
        @JoinColumn(name = "address", referencedColumnName = "address")}, inverseJoinColumns = {
        @JoinColumn(name = "username", referencedColumnName = "username")})
    @ManyToMany
    private Collection<User> userCollection;

    public Server() {
    }

    public Server(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(Integer connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (address != null ? address.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Server)) {
            return false;
        }
        Server other = (Server) object;
        if ((this.address == null && other.address != null) || (this.address != null && !this.address.equals(other.address))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaseEntity.Server[ address=" + address + " ]";
    }
    
}
