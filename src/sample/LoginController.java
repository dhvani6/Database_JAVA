package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import static jdk.nashorn.internal.objects.NativeString.trim;


public class LoginController implements Initializable {

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button login;

    @FXML
    private AnchorPane anchorPane;

    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = dba.DBConnection.connection();

    }

    @FXML
    public void handleLogin(javafx.event.ActionEvent event) throws IOException {


        if(usernameText.getText().trim().equals(getUsername().trim()) && passwordText.getText().trim().equals(getPassword().trim())) {

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.NONE,"Invalid Username or Password", ButtonType.OK);
            alert.setTitle("Invalid");
            alert.showAndWait();
        }
    }
    private String getUsername() {
        String username = "";
        try {
            pst = conn.prepareStatement("SELECT LoginName FROM tblLogin WHERE LoginName = ?");
            pst.setString(1, usernameText.getText());
            rs = pst.executeQuery();

            if (rs.next())
                username = rs.getString(1);
            rs.close();

            return username;
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;

    }
    private String getPassword() {
        String password = "";
        try {
            pst = conn.prepareStatement("SELECT Password FROM tblLogin WHERE Password = ?");
            pst.setString(1, passwordText.getText());
            rs = pst.executeQuery();

            if (rs.next())
                password = rs.getString(1);
            rs.close();

            return password;
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;

    }


}
