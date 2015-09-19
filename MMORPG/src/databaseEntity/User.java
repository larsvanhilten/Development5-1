/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lars
 */
@Entity
@Table(name = "\"user\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByBalance", query = "SELECT u FROM User u WHERE u.balance = :balance"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByIban", query = "SELECT u FROM User u WHERE u.iban = :iban"),
    @NamedQuery(name = "User.findByCharacterSlots", query = "SELECT u FROM User u WHERE u.characterSlots = :characterSlots"),
    @NamedQuery(name = "User.findByLastPayment", query = "SELECT u FROM User u WHERE u.lastPayment = :lastPayment"),
    @NamedQuery(name = "User.findByMonthsPayed", query = "SELECT u FROM User u WHERE u.monthsPayed = :monthsPayed"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByBanned", query = "SELECT u FROM User u WHERE u.banned = :banned")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance")
    private Double balance;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "iban")
    private String iban;
    @Column(name = "character_slots")
    private Integer characterSlots;
    @Column(name = "last_payment")
    @Temporal(TemporalType.DATE)
    private Date lastPayment;
    @Column(name = "months_payed")
    private Integer monthsPayed;
    @Column(name = "password")
    private String password;
    @Column(name = "banned")
    private Boolean banned;
    @OneToMany(mappedBy = "owner")
    private Collection<Character> characterCollection;
    
    private static EntityManager em;

    public User() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MMORPGPU");
        em = emf.createEntityManager();
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Integer getCharacterSlots() {
        return characterSlots;
    }

    public void setCharacterSlots(Integer characterSlots) {
        this.characterSlots = characterSlots;
    }

    public Date getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(Date lastPayment) {
        this.lastPayment = lastPayment;
    }

    public Integer getMonthsPayed() {
        return monthsPayed;
    }

    public void setMonthsPayed(Integer monthsPayed) {
        this.monthsPayed = monthsPayed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection() {
        return characterCollection;
    }

    public void setCharacterCollection(Collection<Character> characterCollection) {
        this.characterCollection = characterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "databaseEntity.User[ username=" + username + " ]";
    }
    
      public List<User> selectQuery(String selectQuery){
        Query query = em.createQuery(selectQuery);
        List<User> result = query.getResultList();
        return result;
        
        //TODO: not working?
    }
      
    public User findUser(String username) {
        User user = new User();
        try{
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        user = (User) query.getSingleResult();
        }catch(Exception e){   

        }
        return user;
    }

    public void persist(Object object) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
        em.persist(object); 
        transaction.commit();
        em.getTransaction().begin();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
      
      
    
}
