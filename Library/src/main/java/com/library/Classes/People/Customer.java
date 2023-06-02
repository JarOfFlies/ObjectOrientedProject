package com.library.Classes.People;

import com.library.Classes.Rental.Loan;
import com.library.Logic.DB.DB;
import java.sql.Connection;
import java.util.ArrayList;

public class Customer extends PeopleBase {

    public Customer(int id, String email){
        this.Id = id;
        this.Email = email;
    }

    public Customer(String forename, String surname, String email, int phoneNumber){
        this.Forename = forename;
        this.Surname = surname;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
    }

    public Customer(int id, String forename, String surname, String email, int phoneNumber, ArrayList<Loan> loans){
        this.Id = id;
        this.Forename = forename;
        this.Surname = surname;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.Loans = loans;
    }

    public ArrayList<Loan> Loans;

    @Override
    public void CreateAccount() {
        Connection connection = DB.CreateConnection();
        String query = "INSERT INTO `libraryschema`.`customers` (`Forename`, `Surname`, `Email`, `PhoneNumber`) VALUES('" + Forename + "', '" + Surname + "', '"
                + Email + "', " + PhoneNumber + ");";
        DB.Update(connection, query);
        DB.CloseConnection(connection);
    }
}
