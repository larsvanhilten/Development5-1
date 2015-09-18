/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MMORPG;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Lars
 */
public class Database {
        EntityManagerFactory emf; //= Persistence.createEntityManagerFactory("MMORPGPU");
        EntityManager em;// = emf.createEntityManager();
    public Database(){
    
        //TODO fix select query: make entity managers work
    }
    
    public List<String> selectQuery(String selectQuery){

        Query query = em.createQuery(selectQuery);
        List<String> result = query.getResultList();
        
        return result;
    }

   
    
}
