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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
        primaryStage.setTitle("MMORPG Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void onLoginButton() throws IOException{
        String username = usernameInput.getText();
        String password = passwordInput.getText();
           
        if(username.equals("lars") && password.equals("dataflow")){
        
        
        
        }else{
            errorLabel.setText("Wrong username or password");

        }
            //TODO: open new window
        
    }
    
    public void onRegisterLink() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserRegister.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.centerOnScreen();
        stage.setTitle("Databuilder");
        stage.show();
        registerLink.getScene().getWindow().hide();
        
    
    }
    
    
}
