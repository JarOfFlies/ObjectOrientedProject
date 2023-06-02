package com.library.Views;

import com.library.Classes.People.Customer;
import com.library.Classes.People.Employee;
import com.library.Logic.DB.Accounts;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class CreateAccountView {

    public static void CreateScene(Stage primaryStage, boolean isCreateEmployee){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Create Account");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label forenameLabel = new Label("Forename:");
        grid.add(forenameLabel, 0, 1);

        TextField forenameTextField = new TextField();
        grid.add(forenameTextField, 1, 1);

        Label surnameLabel = new Label("Surname:");
        grid.add(surnameLabel, 0, 2);

        TextField surnameTextField = new TextField();
        grid.add(surnameTextField, 1, 2);

        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 3);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);

        Label phoneNumberLabel = new Label("Phone Number:");
        grid.add(phoneNumberLabel, 0, 4);

        DecimalFormat format = new DecimalFormat( "#.0" );

        TextField phoneNumberTextField = new TextField();
        phoneNumberTextField.setTextFormatter( new TextFormatter<>(c ->
        {
            if ( c.getControlNewText().isEmpty() )
            {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );

            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
            {
                return null;
            }
            else
            {
                return c;
            }
        }));
        grid.add(phoneNumberTextField, 1, 4);

        Label roleLabel = new Label("Role:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().add("Admin");
        roleComboBox.getItems().add("Basic");

        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Password:");
        TextField passwordTextField = new TextField();


        if (isCreateEmployee){
            grid.add(roleLabel, 0, 5);
            grid.add(roleComboBox, 1, 5);

            grid.add(usernameLabel, 0, 6);
            grid.add(usernameTextField, 1, 6);

            grid.add(passwordLabel, 0, 7);
            grid.add(passwordTextField, 1, 7);
        }

        Button createAccountBtn = new Button("Create Account");
        HBox hbCreateAccountBtn = new HBox(10);
        hbCreateAccountBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbCreateAccountBtn.getChildren().add(createAccountBtn);
        grid.add(hbCreateAccountBtn, 1, 8);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 0, 8, 2, 1);

        Button btn = new Button("Back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 9);

        createAccountBtn.setOnAction(e -> {
            if (isCreateEmployee){
                 new Employee(forenameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), Integer.parseInt(phoneNumberTextField.getText()),
                        roleComboBox.getSelectionModel().getSelectedItem(), usernameTextField.getText(), passwordTextField.getText()).CreateAccount();
            }
            else{
                new Customer(forenameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), Integer.parseInt(phoneNumberTextField.getText())).CreateAccount();
            }
            actionTarget.setText("Account Created");
        });

        btn.setOnAction(e -> HomeView.CreateScene(primaryStage));

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }
}
