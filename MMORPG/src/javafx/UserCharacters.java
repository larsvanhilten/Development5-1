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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Lars
 */
public class UserCharacters extends Application {
    
    @FXML
    private ChoiceBox charactersBox;
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
    private Button deleteCharacterButton;
    @FXML
    private Button ConnectServerButton;
    
    private Database database;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    private User loggedInUser;
    
    @Override
    public void start(Stage primaryStage) {
  
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
    


    
}
