package com.library.Logic.DB;

import com.library.Classes.People.Customer;
import com.library.Classes.People.Employee;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Accounts {

    /**
     * Fetches the surname of the employee with the given EmployeeId
     * @param id Employee Id
     * @return Instance of the Employee class with surname and Id
     */
    public static Employee FetchEmployee(int id){
        Employee employee = null;
        Connection connection = DB.CreateConnection();
        ResultSet resultSet = DB.Fetch(connection, "SELECT Surname FROM libraryschema.Employees WHERE Id =" + id);
        try {
            while (resultSet.next()){
                String surname = resultSet.getString(1);
                employee = new Employee(id, surname);
            }
        }
        catch (SQLException sqlEx){

        }

        DB.CloseConnection(connection);
        return  employee;
    }

    /**
     * Fetches the email of the customer with the given CustomerId
     * @param id Customer Id
     * @return Instance of the customer class with Email and Id
     */
    public static Customer FetchCustomer(int id){
        Customer customer = null;
        Connection connection = DB.CreateConnection();
        ResultSet resultSet = DB.Fetch(connection, "SELECT Email FROM libraryschema.Customers WHERE Id =" + id);
        try {
            while (resultSet.next()){
                String email = resultSet.getString(1);
                customer = new Customer(id, email);
            }
        }
        catch (SQLException sqlEx){

        }

        DB.CloseConnection(connection);
        return  customer;

    }

    /**
     * Fetches the Id of the customer with the given Email
     * @param email Customer Email
     * @return Customer Id for the customer with a given email
     */
    public static Customer FetchCustomer(String email){
        Customer customer = null;
        Connection connection = DB.CreateConnection();
        ResultSet resultSet = DB.Fetch(connection, "SELECT Id FROM libraryschema.Customers WHERE Email = '" + email + "';");
        try {
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                customer = new Customer(id, email);
            }
        }
        catch (SQLException sqlEx){

        }

        DB.CloseConnection(connection);
        return  customer;

    }
}
