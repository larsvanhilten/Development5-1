/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import MMORPG.Database;
import databaseEntity.User;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
    private Label firstNameLabel;  
    @FXML
    private Label lastNameLabel;  
    @FXML
    private Label usernameLabel;
    @FXML
    private Label ibanLabel;
    @FXML
    private Label fundsLabel; 
    @FXML
    private Label characterslotLabel;
    @FXML
    private Label subscriptionStatusLabel;
    @FXML
    private ChoiceBox addFundsBox;
    @FXML 
    private ChoiceBox addSubsBox;
    @FXML
    private ChoiceBox addSlotsChoice;
    
    @Override
    public void start(Stage primaryStage) throws Exception {  
        checkSubscription();
        setLabels();
    }
    public void checkSubscription(){
        if(loggedInUser.getMonthsPayed() != 0){
            subscriptionStatusLabel.setText("Subscription: Active");
        }
        else{
            subscriptionStatusLabel.setText("Subscription: None");
        }
    }
    
    public void setLabels(){
        usernameLabel.setText(loggedInUser.getUsername());
        firstNameLabel.setText(loggedInUser.getFirstName());
        lastNameLabel.setText(loggedInUser.getLastName());
        ibanLabel.setText(loggedInUser.getIban());
        fundsLabel.setText("Balance: €" + loggedInUser.getBalance().toString());
        characterslotLabel.setText("Slots: " + loggedInUser.getCharacterSlots().toString());  
        
        addSlotsChoice.setItems(FXCollections.observableArrayList("1","2","3","4","5"));
        addSlotsChoice.setValue("1");

        addSubsBox.setItems(FXCollections.observableArrayList("1 Month", "2 Months", "3 Months", "1 Year"));
        addSubsBox.setValue("1 Month");
        
        addFundsBox.setItems(FXCollections.observableArrayList("€5,-", "€10,-", "€20,-"));
        addFundsBox.setValue("€5,-");
    }    
    
    public void confirmBuySlot() {
        String a = addSlotsChoice.getValue().toString();
        int slotCount = Integer.parseInt(a);
        double totalPrice = slotCount * 1.50;
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm purchase");
        alert.setHeaderText("Buy " + slotCount  + " slots " + "for: €" + totalPrice + "0");
        alert.setContentText("Are you sure?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            if(checkFunds(totalPrice)){
                double updatedBalance = loggedInUser.getBalance() - totalPrice;
                int updatedSlots = loggedInUser.getCharacterSlots() + slotCount;
                database.updateBalance(loggedInUser.getUsername(), updatedBalance);
                database.updateCharacterSlots(loggedInUser.getUsername(), updatedSlots);
                loggedInUser.setBalance(updatedBalance);
                loggedInUser.setCharacterSlots(updatedSlots);
                setLabels();
            }
            else{
                Alert alertError = new Alert(AlertType.ERROR);
                alertError.setTitle("ERROR");
                alertError.setContentText("The transaction could not be completed due to insufficient balance.");
                Optional<ButtonType> resultError = alertError.showAndWait();
            }
        }
    }
    
    public void confirmBuyFunds(){
        String a = addFundsBox.getValue().toString();
        int fundsAdded = Integer.parseInt(a);
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm purchase");
        alert.setHeaderText("You are about to add: €" + fundsAdded);
        alert.setContentText("Are you sure?");
    }
    
    public boolean checkFunds(double totalPrice){
        double funds = loggedInUser.getBalance();
        return funds - totalPrice >= 0;
    }
    
}
