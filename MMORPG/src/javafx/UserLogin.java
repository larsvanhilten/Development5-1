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
    
    private Database database;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO set image
        
        Parent root = FXMLLoader.load(getClass().getResource("UserLogin.fxml"));
        
        primaryStage.setTitle("MMORPG Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
  
        //TODO: enable button when database connection is establisid; error?
        //loginButton.setDisable(true);
        database = new Database();
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
           
       /* ReadAllQuery query = new ReadAllQuery(User.class);
        
        ExpressionBuilder builder = query.getExpressionBuilder();
        
        query.setSelectionCriteria(builder.get("username"));
        
        
        
        Session clientSession = (Session) .acquireClientSession();
        
        List test = (List) clientSession.executeQuery(query);*/
        
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        
        List<User> result = user.selectQuery("SELECT s FROM User s");

        for(int x = 0; x < result.size(); x++){
            System.out.println(result.get(x));       
        }
        
        //just some random testing
        if(username.equals("lars") && password.equals("dataflow")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserCharacters.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene((Pane) loader.load()));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("MMORPG Characters");
            stage.show();
            registerLink.getScene().getWindow().hide();
        
        
        }else{
            errorLabel.setText("Wrong username or password!");

        }
            //TODO: open new window
        
    }
    
    public void onRegisterLink() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserRegister.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("MMORPG Register");
        stage.show();
        registerLink.getScene().getWindow().hide();
        
    
    }

}
