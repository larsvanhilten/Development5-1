/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import MMORPG.Database;
import databaseEntity.User;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.sessions.Session;


/**
 *
 * @author Lars
 */
public class UserLogin extends Application {
    
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label registerLink;
    
    private static Database database;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO set image
        
        Parent root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
        
        primaryStage.setTitle("MMORPG Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        database = new Database();
  
        //TODO: enable button when database connection is establisid; error?
        //loginButton.setDisable(true);
        //loginButton.setDisable(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void onLoginButton() throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
           
        if(username.equals("") || password.equals("")){
            errorLabel.setText("Please enter your password or username!");
            return;
        
        }      
            
        User user = database.findUser(username);
        
        String resultUsername = user.getUsername();
        String resultPassword = user.getPassword();

        
        if(resultUsername == null || resultPassword == null){
            errorLabel.setText("Wrong username or password!");
            return;
        }
        
        if(resultUsername.equals(username) && resultPassword.equals(password)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserCharacters.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("MMORPG Characters");
            UserCharacters userCharacters = loader.<UserCharacters>getController();
            userCharacters.setLoggedInUser(user);
            userCharacters.setDatabase(database);
            userCharacters.start(stage);
            stage.show();
            registerLink.getScene().getWindow().hide();
        
        
        }
           
        
    }
    
    public void onRegisterLink() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserRegister.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("MMORPG Register");
        UserRegister userRegister = loader.<UserRegister>getController();
        userRegister.setDatabase(database);
        stage.show();
        
        registerLink.getScene().getWindow().hide();
        
    
    }

}
