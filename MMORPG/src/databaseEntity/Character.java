/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseEntity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lars
 */
@Entity
@Table(name = "character")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Character.findAll", query = "SELECT c FROM Character c"),
    @NamedQuery(name = "Character.findByName", query = "SELECT c FROM Character c WHERE c.name = :name"),
    @NamedQuery(name = "Character.findByClass1", query = "SELECT c FROM Character c WHERE c.class1 = :class1"),
    @NamedQuery(name = "Character.findByRace", query = "SELECT c FROM Character c WHERE c.race = :race"),
    @NamedQuery(name = "Character.findByLevel", query = "SELECT c FROM Character c WHERE c.level = :level")})
public class Character implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "class")
    private Integer class1;
    @Column(name = "race")
    private Integer race;
    @Column(name = "level")
    private Integer level;
    @JoinColumn(name = "server_address", referencedColumnName = "address")
    @ManyToOne
    private Server serverAddress;
    @JoinColumn(name = "owner", referencedColumnName = "username")
    @ManyToOne
    private User owner;

    public Character() {
    }

    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getClass1() {
        return class1;
    }

    public void setClass1(Integer class1) {
        this.class1 = class1;
    }

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Server getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(Server serverAddress) {
        this.serverAddress = serverAddress;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Character)) {
            return false;
        }
        Character other = (Character) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaseEntity.Character[ name=" + name + " ]";
    }
    
}
