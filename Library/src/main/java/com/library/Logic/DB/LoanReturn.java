package com.library.Logic.DB;

import com.library.Classes.Rental.DisplayProduct;
import com.library.Views.HomeView;
import com.library.Views.LoginView;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class LoanReturn {

    public static void LoanItems(ObservableList<DisplayProduct> items){
        Connection connection = DB.CreateConnection();
        for (DisplayProduct item : items){
            item.Loan(connection, HomeView.Customer.Id, LoginView.Employee.Id);
        }
        DB.CloseConnection(connection);
    }

    public static void ReturnItems(ObservableList<DisplayProduct> items){
        Connection connection = DB.CreateConnection();
        for (DisplayProduct item : items){
            item.Return(connection, HomeView.Customer.Id);
        }
        DB.CloseConnection(connection);
    }
}
