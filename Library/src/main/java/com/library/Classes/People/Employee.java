package com.library.Classes.People;

import com.library.Enums.EnumEmployeeRole;
import com.library.Logic.DB.DB;

import java.sql.Connection;

public class Employee extends PeopleBase {

    public Employee(int id, String surname){
        this.Id = id;
        this.Surname = surname;
    }

    public Employee(int id, String surname, String role){
        this.Id = id;
        this.Surname = surname;
        this.Role = EnumEmployeeRole.valueOf(role);
    }

    public Employee(int id, String forename, String surname, String email, int phoneNumber, String role){
        this.Id = id;
        this.Forename = forename;
        this.Surname = surname;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.Role = EnumEmployeeRole.valueOf(role);
    }

    public Employee(String forename, String surname, String email, int phoneNumber, String role, String username, String password){
        this.Forename = forename;
        this.Surname = surname;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.Role = EnumEmployeeRole.valueOf(role);
        this.Username = username;
        this.Password = password;
    }

    public EnumEmployeeRole Role;

    public String Username;

    public String Password;

    public boolean isAdmin(){
        return Role == EnumEmployeeRole.Admin;
    }

    @Override
    public void CreateAccount() {
        Connection connection = DB.CreateConnection();
        String query = "INSERT INTO `libraryschema`.`employees` (`Forename`, `Surname`, `Email`, `PhoneNumber`, `Role`, `Username`, `Password`) VALUES ( '" + Forename
                + "', '" + Surname + "', '" + Email + "', " + PhoneNumber + ", '" + Role.name() + "', '" + Username + "', '" + Password + "');";
        DB.Update(connection, query);
        DB.CloseConnection(connection);
    }
}
