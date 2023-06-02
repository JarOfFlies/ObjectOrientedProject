package com.library.Logic.DB;

import com.library.Classes.People.Employee;
import com.library.Views.LoginView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    public static Boolean ValidateLogin(String loginUsername, String loginPassword){

        Connection connection = DB.CreateConnection();
        boolean isValid = false;

        try{

            ResultSet results = DB.Fetch(connection, String.format("SELECT Password, Id, Surname, Role FROM libraryschema.employees WHERE Username = '%s';", loginUsername));

            while(results.next()){

                try {
                    String result = results.getString(1);

                    if (result.equals(loginPassword)){
                        isValid = true;
                        LoginView.Employee = new Employee(results.getInt(2), results.getString(3), results.getString(4));
                    }
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        catch (SQLException ignored){
        }

        DB.CloseConnection(connection);

        return isValid;
    }
}
