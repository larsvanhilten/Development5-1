/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MMORPG;

import databaseEntity.User;
import databaseEntity.Character;
import databaseEntity.Server;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    
    public void updateCharacterSlots(String username, int slots){
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query query = em.createQuery("UPDATE User u SET u.characterSlots = :slots WHERE u.username = :username");
        query.setParameter("slots", slots);
        query.setParameter("username", username);
        query.executeUpdate();
        System.out.println(username);
        updateTransaction.commit();
    }
    
    public void updateBalance(String username, double balance){
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query query = em.createQuery("UPDATE User u SET u.balance = :balance WHERE u.username = :username");
        query.setParameter("balance", balance);
        query.setParameter("username", username);
        query.executeUpdate();
        updateTransaction.commit();
    }
    
    public void updateSubscription(String username, int monthsPayed){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query query = em.createQuery("UPDATE User u SET u.monthsPayed = :monthsPayed, u.lastPayment = :lastPayment WHERE u.username = :username");
        query.setParameter("monthsPayed", monthsPayed);
        query.setParameter("lastPayment", date);
        query.setParameter("username", username);
        query.executeUpdate();
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
    
    public void deleteCharacter(String characterName){
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query query = em.createQuery("DELETE FROM Character WHERE name = :characterName");
        query.setParameter("characterName", characterName);
        query.executeUpdate();
        updateTransaction.commit();
    
    
    }
    
    public boolean checkCharacter(String characterName){
        boolean charExists = false;
        try{
        Query query = em.createNamedQuery("Character.findByName");
        query.setParameter("name", characterName);
        Character character = (Character) query.getSingleResult();
        
        if(character.getName().equals(characterName.toUpperCase())){
            charExists = true;
        }
        
        }catch(Exception e){   
        }
        
        
        return charExists;
    
    }
    
    public Server getServer(String serverName){
        try{
        Query query = em.createNamedQuery("Server.findByName");
        query.setParameter("name", serverName);
        Server server = (Server) query.getSingleResult();
        return server;
        }catch(Exception e){   
        }
       
        return null;
        
    }
    //Server.findByName
    
    public void connectToServer(User user, Server server){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        user.getServerCollection().add(server);
        server.getUserCollection().add(user);
        em.merge(server);
        em.merge(user);
        transaction.commit();
        updateConnectedUsers(server, 1);
    }
    
    public void deleteConnection(User user, Server server){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        user.getServerCollection().remove(server);
        server.getUserCollection().remove(user);
        em.merge(user);
        em.merge(server);
        transaction.commit();
        updateConnectedUsers(server, 0);
    
    }
    
    private void updateConnectedUsers(Server server, int updateCount){
        EntityTransaction updateTransaction = em.getTransaction();
        updateTransaction.begin();
        Query query = em.createQuery("UPDATE Server s SET s.connectedUsers = :userCount WHERE s.name = :name");
        int userCount = server.getConnectedUsers() + updateCount;
        String serverName = server.getName();
        query.setParameter("userCount", userCount);
        query.setParameter("name", serverName);
        query.executeUpdate();
        updateTransaction.commit();
        
    }
    
    private String randomString(){
        Random r = new Random();
        int Low = 1000;
        int High = 99999999;
        int randomNumber = r.nextInt(High-Low) + Low;
        
        String generatedString = Integer.toString(randomNumber);
         return generatedString;
    }
    
        private int randomLevel() {
        Random r = new Random();
        int Low = 1;
        int High = 101;
        int randomNumber = r.nextInt(High-Low) + Low;
        return randomNumber;
    }
    
    public void addDummyData(){

        EntityTransaction transaction = em.getTransaction();

        for(int i = 0; i < 20000; i++){
        transaction.begin();
            
        User user = new User();
        user.setUsername(randomString());
        user.setPassword(randomString());  
        user.setFirstName(randomString());
        user.setLastName(randomString());
        user.setIban(randomString());
        user.setBalance(0.0);
        user.setBanned(false);
        user.setCharacterSlots(5);
        user.setMonthsPayed(0);
        
        Character character = new Character();
        character.setName(randomString());
        character.setClass1(randomString());
        character.setRace(randomString());
        character.setLevel(randomLevel());
        
        em.persist(user);
        em.persist(character);
                transaction.commit();
                
                transaction.begin();
        user.getCharacterCollection().add(character);
        character.getUserCollection().add(user);
        em.merge(user);
        em.merge(character);
        transaction.commit();
        System.out.println(i);
        }

        em.close();
        }
    }



