/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MMORPG;

import databaseEntity.User;
import databaseEntity.Character;
import java.util.ArrayList;
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
    
    public void registerUser(String username, String password, String firstName, String lastName, String iban){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIban(iban);
        user.setBalance(0.0);
        user.setBanned(false);
        user.setCharacterSlots(5);
        user.setMonthsPayed(0);
        
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
        
    }     
    
    public List<Character> getCharacters(String username){
        List<Character> characters = new ArrayList<>();
        try{
        Query query = em.createNamedQuery("Character.findAll");
        query.setParameter("username", username);
        characters = query.getResultList();
        }catch(Exception e){   
        }
    
        return characters;
    }
    
    public void updateCharacterSlots(String username, double balance, int slots){
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query query = em.createQuery("UPDATE User u SET u.balance = " + balance + " WHERE u.username = :username");
        query.setParameter("username", username);
        System.out.println(username);
        updateTransaction.commit();
    }
    
    public void addCharacter(Character newCharacter, User user){
        Character character = newCharacter;
        
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        try{
        em.persist(character);
        transaction.commit();
        em.getTransaction().begin();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
         
        }
        user.getCharacterCollection().add(character);
        character.getUserCollection().add(user);
        em.merge(character);
        em.merge(user);
        transaction.commit();
    
    }
    
    
}
