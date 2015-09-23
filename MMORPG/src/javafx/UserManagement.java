/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import MMORPG.Database;
import databaseEntity.User;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Lars
 */
public class UserManagement extends Application {
    
    private Database database;
    private User loggedInUser;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    @FXML
    private Label nameLabel;    
    @FXML
    private Label usernameLabel;    
    @FXML
    private Label fundsLabel; 
    @FXML
    private Label characterslotLabel;
    @FXML
    private Label subcriptionLabel;
    
    @Override
    public void start(Stage primaryStage) throws Exception {  
        setLabels();
    }
    
    public void setLabels(){
        System.out.println("x");
        usernameLabel.setText("Username: " +loggedInUser.getUsername());
        nameLabel.setText("Name: " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        fundsLabel.setText(loggedInUser.getBalance().toString());
        characterslotLabel.setText(loggedInUser.getCharacterSlots().toString());       
    }
}
