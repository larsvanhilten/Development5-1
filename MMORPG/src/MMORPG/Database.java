/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MMORPG;

import databaseEntity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author Lars
 */
public class Database {
     @PersistenceContext(unitName = "MMORPGPU", type = PersistenceContextType.EXTENDED)
     private static EntityManager em;
     
    public Database(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MMORPGPU");
        em = emf.createEntityManager();
    }
    
    public User findUser(String username) {
        User user = new User();
        try{
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        user = (User) query.getSingleResult();
        }catch(Exception e){   
        }
        System.out.println(user);
        return user;
    }
    
    public User registerUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);       
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        try{
        em.persist(user);
        transaction.commit();
        em.getTransaction().begin();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        em.close();
        }
        return user;
    }     
}
