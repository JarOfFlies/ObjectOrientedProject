package com.library.Views;

import com.library.Logic.DB.Accounts;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeView {

    public static com.library.Classes.People.Customer Customer;

    public static void CreateScene(Stage primaryStage){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Home Page");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0);

        Label customerLabel = new Label("Customer Email: ");
        TextField customerTextField = new TextField();
        if (Customer != null){
            customerTextField.setText(Customer.Email);
        }

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 8);

        HBox customerInputBox = new HBox(customerLabel, customerTextField);
        customerInputBox.setSpacing(10);
        customerInputBox.setPadding(new Insets(5));
        grid.add(customerInputBox, 0, 1);

        Button itemBtn = new Button("Loan");
        HBox hbItemBtn = new HBox(10);
        hbItemBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbItemBtn.getChildren().add(itemBtn);
        grid.add(hbItemBtn, 0, 2);

        itemBtn.setOnAction(e -> {
            if (!customerTextField.getText().isEmpty()){
                Customer = Accounts.FetchCustomer(customerTextField.getText());
                ListsView.CreateScene(primaryStage, false, null);
            }
            else{
                actiontarget.setText("No Customer Info Included");
            }
        });

        Button returnbtn = new Button("Returns");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(returnbtn);
        grid.add(hbBtn, 0, 3);

        returnbtn.setOnAction(e -> {
            if (!customerTextField.getText().isEmpty()){
                Customer = Accounts.FetchCustomer(customerTextField.getText());
                ListsView.CreateScene(primaryStage, true, null);
            }
            else{
                actiontarget.setText("No Customer Info Included");
            }
        });

        Button CreateCustomerbtn = new Button("Create Customer Account");
        HBox CreateCustomerbtnBox = new HBox(10);
        CreateCustomerbtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        CreateCustomerbtnBox.getChildren().add(CreateCustomerbtn);

        CreateCustomerbtn.setOnAction(e -> {
            CreateAccountView.CreateScene(primaryStage, false);
        });

        Button CreateEmployeebtn = new Button("Create Employee Account");
        HBox CreateEmployeebtnBox = new HBox(10);
        CreateEmployeebtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        CreateEmployeebtnBox.getChildren().add(CreateEmployeebtn);

        CreateEmployeebtn.setOnAction(e -> {
            CreateAccountView.CreateScene(primaryStage, true);
        });

        Button Itembtn = new Button("Items");
        HBox ItembtnBox = new HBox(10);
        ItembtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        ItembtnBox.getChildren().add(Itembtn);

        Itembtn.setOnAction(e -> {
            ItemSelectionView.CreateScene(primaryStage);
        });

        Button logoutbtn = new Button("Logout");
        HBox logoutbtnBox = new HBox(10);
        logoutbtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        logoutbtnBox.getChildren().add(logoutbtn);

        logoutbtn.setOnAction(e -> {
            customerTextField.setText("");
            LoginView.Employee = null;
            LoginView.CreateScene(primaryStage);
        });

        grid.add(logoutbtnBox, 0, LoginView.Employee.isAdmin() ? 7 : 4);

        if (LoginView.Employee.isAdmin()){
            grid.add(CreateCustomerbtnBox, 0, 4);
            grid.add(CreateEmployeebtnBox, 0, 5);
            grid.add(ItembtnBox, 0, 6);
        }

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }
}