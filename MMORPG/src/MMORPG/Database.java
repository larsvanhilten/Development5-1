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
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lars
 */
public class Database {
     @PersistenceContext
     private static EntityManager em;
    public Database(){

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


   
    
}
