/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Lars
 */
public class UserRegister extends Application {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordVerificationInput;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button backButton;
    
    @Override
    public void start(Stage primaryStage) {}
    
    public void onRegisterButton(){
        
        if(usernameInput.getText().equals("") || passwordInput.getText().equals("")){
            errorLabel.setText("There are empty fields!");
            return;
        }
    
        if(!passwordInput.getText().equals(passwordVerificationInput.getText())){
            errorLabel.setText("The passwords don't match!");
            return;
        
        }
        
        //TODO: register in database
    
    
    }
    
    public void onBackButton() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
        Stage stage = new Stage();
        stage.setTitle("MMORPG Login");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        backButton.getScene().getWindow().hide();
        
    }

}
