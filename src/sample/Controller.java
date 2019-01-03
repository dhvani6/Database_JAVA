package sample;

import dba.DBConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


import java.lang.reflect.InvocationTargetException;

import static javafx.application.Application.launch;

public class Controller implements Initializable {

    @FXML
    private Button update, clear;

    @FXML
    private TextArea sqlArea;

    @FXML
    private Label script;

    private ObservableList<ObservableList> data;

    @FXML
    private TableView tableview;




    private Connection conn = null;
    private PreparedStatement pst = null;

    //MAIN EXECUTOR
    public static void main(String[] args) {
        launch(args);
    }



    public void handleUpdate(javafx.event.ActionEvent event) throws SQLException {
        System.out.println("You've pressed the update button!");
        tableview.getColumns().clear();


        String sql = sqlArea.getText();


        try {
            pst = conn.prepareStatement(sql);


            int i = pst.executeUpdate();

            if (i == 1)
                System.out.println("Data Insert Successful");

        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pst.close();
        }


    }


    public void handleDisplay(javafx.event.ActionEvent event) {
        System.out.println("You've pressed the handle button!");
        tableview.getColumns().clear();



        Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = DBConnection.connection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = sqlArea.getText();
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.NONE,"Invalid SQL Statement", ButtonType.OK);
            alert.setTitle("Invalid");
            alert.showAndWait();
            System.out.println("Error on Building Data");
        }

    }


    public void handleClear(javafx.event.ActionEvent event) {
        System.out.println("You've hit the clear button!");

        sqlArea.setText("");
        tableview.getColumns().clear();

    }















        @Override
        public void initialize (URL url, ResourceBundle rb){

            conn = dba.DBConnection.connection();

        }
    }

