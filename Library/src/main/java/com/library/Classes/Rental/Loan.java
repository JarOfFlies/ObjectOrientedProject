package com.library.Classes.Rental;

import com.library.Enums.EnumProductType;
import com.library.Logic.DB.Accounts;

import java.sql.Date;

public class Loan {

    public Loan (int id, int productId, String type, int numberLoaned, Date dueDate){
        this.Id = id;
        this.ProductId = productId;
        this.ProductType = EnumProductType.valueOf(type);
        this.NumberLoaned = numberLoaned;
        this.DueDate = dueDate;
    }

    public Loan (Date dueDate, int numberLoaned){
        this.DueDate = dueDate;
        this.NumberLoaned = numberLoaned;
    }

    public Loan (Date dateLoaned, Date dueDate, int employeeId, int customerId, int numberLoaned){
        this.DateLoaned = dateLoaned;
        this.DueDate = dueDate;
        this.EmployeeId = employeeId;
        this.CustomerId = customerId;
        this.NumberLoaned = numberLoaned;
    }

    public int Id;

    public Date DateLoaned;

    public Date DueDate;

    public int CustomerId;

    public int EmployeeId;

    public int ProductId;

    public EnumProductType ProductType;

    public int NumberLoaned;

    public boolean IsActive;

    private String EmployeeSurname;

    private String CustomerEmail;

    public int getNumberLoaned(){
        return NumberLoaned;
    }

    public Date getDueDate(){
        return DueDate;
    }

    public Date getDateLoaned(){
        return DateLoaned;
    }

    public String getEmployee(){
        if (EmployeeSurname == null){
            EmployeeSurname = Accounts.FetchEmployee(EmployeeId).Surname;
        }
        return EmployeeSurname;
    }

    public String getCustomer(){
        if (CustomerEmail == null){
            CustomerEmail = Accounts.FetchCustomer(CustomerId).Email;
        }
        return CustomerEmail;
    }
}
