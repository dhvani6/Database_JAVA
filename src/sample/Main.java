package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginscreen.fxml"));
        primaryStage.setTitle("Mad Code Interface");
        primaryStage.setScene(new Scene(root, 650, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
