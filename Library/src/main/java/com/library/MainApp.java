package com.library;

import com.library.Views.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            System.out.println("Loading JDBC Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC driver successfully loaded!");
        }
        catch(ClassNotFoundException cnfEx){
            throw new RuntimeException(cnfEx);
        }

        primaryStage.setTitle("Library Loan System");

        LoginView.CreateScene(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}

