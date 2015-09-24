/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import MMORPG.Database;
import databaseEntity.User;
import databaseEntity.Character;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    private Button ConnectServerButton;
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
       
       System.out.println(characters.size());
        charactersBox.getItems().clear();
        for (Character character : characters) {
           charactersBox.getItems().add(character.getName());
        }
        
        Character first  = characters.get(0);
        charactersBox.setValue(first.getName());
        
        characterNameLabel.setText("Character name: " + first.getName());
        characterRaceLabel.setText("Race: " + first.getRace());
        characterClassLabel.setText("Class: " + first.getClass1());
        characterLevelLabel.setText("Level: " + first.getLevel());
        characterServerLabel.setText("Server: TBA");
        characterSlotsLabel.setText("Available slots: " + loggedInUser.getCharacterSlots());
        
        
        //raceBox.setDisable(true);
        raceBox.setItems(FXCollections.observableArrayList("Human", "Orc", "Midget", "Pepe", "Fairy"));
        raceBox.setValue("Human");
        classBox.setItems(FXCollections.observableArrayList("Warrior", "Hunter", "Warlock", "Runner", "Magician", "Brute"));
        classBox.setValue("Warrior");
        serverBox.setItems(FXCollections.observableArrayList("Server EU", "Server US", "Server ASIA"));
        serverBox.setValue("Server EU");
    }
    
    public void createCharacter(){
        if(newCharacterButton.getText().equals("New character")){
        characterNameInput.setDisable(false);
        raceBox.setDisable(false);
        classBox.setDisable(false);
        serverBox.setDisable(false);
        newCharacterButton.setText("Create Character");
        }else{
        
        Character newCharacter = new Character();
        newCharacter.setLevel(1);
        newCharacter.setName(characterNameInput.getText());
        newCharacter.setRace(raceBox.getValue().toString());
        newCharacter.setClass1(classBox.getValue().toString());
        
        database.addCharacter(newCharacter, loggedInUser);
        setLabels();
        }
        
        
        
    }
    
    public void deleteCharacter(){
        
    
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

    
}
