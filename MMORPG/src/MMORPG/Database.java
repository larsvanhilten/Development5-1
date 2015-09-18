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
        private EntityManagerFactory emf;
        private EntityManager em;
    public Database(){
        emf = Persistence.createEntityManagerFactory("MMORPGPU");
        em  = emf.createEntityManager();
    }
    
    public List<String> selectQuery(String selectQuery){

        Query query = em.createQuery(selectQuery);
        List<String> result = query.getResultList();
        return result;
        
        //TODO: not working?
    }


   
    
}
