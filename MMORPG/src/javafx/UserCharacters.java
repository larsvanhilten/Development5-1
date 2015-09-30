/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import MMORPG.Database;
import databaseEntity.User;
import databaseEntity.Character;
import databaseEntity.Server;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Lars
 */
public class UserCharacters extends Application {
    
    @FXML
    private ChoiceBox<String> charactersBox;
    @FXML
    private TextField characterNameInput;
    @FXML
    private ChoiceBox raceBox;
    @FXML
    private ChoiceBox classBox;
    @FXML
    private ChoiceBox serverBox;
    @FXML
    private Label characterSlotsLabel;
    @FXML
    private Button newCharacterButton;
    @FXML
    private Button managementButton;
    @FXML
    private Label characterNameLabel;
    @FXML
    private Label characterRaceLabel;
    @FXML
    private Label characterClassLabel;
    @FXML
    private Label characterServerLabel;
    @FXML
    private Label characterLevelLabel;
    @FXML
    private Button deleteCharacterButton;
    @FXML
    
    private Button connectServerButton;
    private Database database;
    private List<databaseEntity.Character> characters;
    private Character selectedChar = null;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    private User loggedInUser;
    
    @Override
    public void start(Stage primaryStage) {
       setLabels();
       setEventListener();
        
    }
    
  
    
    public void openManagementWindow() throws IOException, Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserManagement.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("MMORPG Management");
            UserManagement userManagement = loader.<UserManagement>getController();
            userManagement.setLoggedInUser(loggedInUser);
            userManagement.setDatabase(database);
            userManagement.start(stage);
        
            stage.show();
            managementButton.getScene().getWindow().hide();
    
    }

    public void setLabels() {
        characters = database.getCharacters(loggedInUser.getUsername());
       
        charactersBox.getItems().clear();
        for (Character character : characters) {
           charactersBox.getItems().add(character.getName());
        }
        if(characters.size() > 0){
        Character first  = characters.get(0);
        charactersBox.setValue(first.getName());
        
        characterNameLabel.setText("Character name: " + first.getName());
        characterRaceLabel.setText("Race: " + first.getRace());
        characterClassLabel.setText("Class: " + first.getClass1());
        characterLevelLabel.setText("Level: " + first.getLevel());
        characterServerLabel.setText("Server: TBA");
        }
        characterSlotsLabel.setText("Available slots: " + loggedInUser.getCharacterSlots());
        
        
        if(loggedInUser.getCharacterSlots() < 1){
           newCharacterButton.setDisable(true);
        }
        //raceBox.setDisable(true);
        raceBox.setItems(FXCollections.observableArrayList("Human", "Orc", "Midget", "Pepe", "Fairy"));
        raceBox.setValue("Human");
        classBox.setItems(FXCollections.observableArrayList("Warrior", "Hunter", "Warlock", "Runner", "Magician", "Brute"));
        classBox.setValue("Warrior");
        serverBox.setItems(FXCollections.observableArrayList("EU", "US", "ASIA"));
        serverBox.setValue("Server EU");
    }
    
    public void createCharacter(){    
        if(newCharacterButton.getText().equals("New character")){
        characterNameInput.setDisable(false);
        raceBox.setDisable(false);
        classBox.setDisable(false);
        newCharacterButton.setText("Create Character");
        }else{
        String charName = characterNameInput.getText().toUpperCase();
        String charRace = raceBox.getValue().toString();
        String charClass = classBox.getValue().toString();
            
            
        if(charName.equals("")){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("ERROR");
            alertError.setContentText("Please enter a name for the character!");
            Optional<ButtonType> resultError = alertError.showAndWait();
            return;
        }
        
        if(database.checkCharacter(charName)){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("ERROR");
            alertError.setContentText("This character name is already in use!");
            Optional<ButtonType> resultError = alertError.showAndWait();
            return;
        }
        
        int charLevel = randomLevel();
        
        Character newCharacter = new Character();
        newCharacter.setLevel(charLevel);
        newCharacter.setName(charName);
        newCharacter.setRace(charRace);
        newCharacter.setClass1(charClass);
        
        
        
        
        database.addCharacter(newCharacter, loggedInUser);
        database.updateCharacterSlots(loggedInUser.getUsername(), loggedInUser.getCharacterSlots() - 1);
        loggedInUser.setCharacterSlots(loggedInUser.getCharacterSlots() - 1);
        setLabels();
        }
        
        
        
    }
    
    public void deleteCharacter(){
        if(!(charactersBox.getValue() == null)){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete character");
        alert.setHeaderText("You are about to delete the character named: " + charactersBox.getValue());
        alert.setContentText("Are you sure?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            database.deleteCharacter(charactersBox.getValue());
            database.updateCharacterSlots(loggedInUser.getUsername(), loggedInUser.getCharacterSlots() + 1);
            loggedInUser.setCharacterSlots(loggedInUser.getCharacterSlots() + 1);
            setLabels();
        
             }
        }
    }

    
    private void setEventListener() {
        charactersBox.valueProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               String name = charactersBox.getValue();
               for (Character character : characters) {
                   if(character.getName().equals(name)){
                      selectedChar = character;
                      characterNameLabel.setText("Character name: " + selectedChar.getName());
                      characterRaceLabel.setText("Race: " + selectedChar.getRace());
                      characterClassLabel.setText("Class: " + selectedChar.getClass1());
                      characterLevelLabel.setText("Level: " + selectedChar.getLevel());
                      characterServerLabel.setText("Server: TBA"); 
                   }
                }
                          
           }
       });
    }
    
    
    public void connectToServer(){
        String serverName = serverBox.getValue().toString();
        Server server = database.getServer(serverName);
        database.connectToServer(loggedInUser, server);
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Connected to the server");
        alert.setHeaderText("You are now connected to the server.");
        alert.setContentText("Press the 'Disconnect' button if you wish to disconnect.");

        ButtonType disconnectButton = new ButtonType("Disconnect");

        alert.getButtonTypes().setAll(disconnectButton);

        Optional<ButtonType> result = alert.showAndWait();
        
        //drop row
        database.deleteConnection(loggedInUser, server);
        
        
    }

    private int randomLevel() {
        Random r = new Random();
        int Low = 1;
        int High = 101;
        int randomNumber = r.nextInt(High-Low) + Low;
        return randomNumber;
    }

    
}
