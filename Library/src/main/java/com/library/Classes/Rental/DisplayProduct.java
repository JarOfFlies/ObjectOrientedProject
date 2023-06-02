package com.library.Classes.Rental;

import com.library.Enums.EnumProductType;
import com.library.Logic.DB.DB;
import com.library.Logic.Loan.Dates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Locale;

public class DisplayProduct extends RentalBase {

    public DisplayProduct(int id, String name){
        this.Id = id;
        this.Name = name;
    }

    public DisplayProduct(){
    }

    public DisplayProduct(int id, String name, String description, EnumProductType productType, int total, int loaned){
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.Type = productType;
        this.NumberTotal = total;
        this.NumberOnLoan = loaned;
        this.AvailableList = FXCollections.observableArrayList(new ArrayList<>());
        int i = 1;
        while (i <= (NumberTotal - NumberOnLoan)) {
            AvailableList.add(String.valueOf(i));
            i += 1;
        }
        this.NumberToLoan = String.valueOf(0);
    }

    public DisplayProduct(int id, String name, String description, EnumProductType productType, int loaned, Date dueDate){
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.Type = productType;
        this.NumberOnLoan = loaned;
        this.DueDate = dueDate;
    }

    public DisplayProduct(int id, String name, String description, EnumProductType productType, int total, ArrayList<Loan> loans){
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.Type = productType;
        this.NumberTotal = total;
        int loaned = 0;
        for (Loan loan : loans){
            loaned += loan.NumberLoaned;
        }
        this.NumberOnLoan = loaned;
    }

    public EnumProductType Type;

    public String NumberToLoan;

    public ObservableList<String> AvailableList;

    public Date DueDate;

    public ObservableList<String> getAvailableList(){
        return AvailableList;
    }

    public String getNumberToLoan(){
        return NumberToLoan;
    }

    public void setAvailableList( ObservableList<String> availableList )
    {
        this.AvailableList = availableList;
    }

    public void setNumberToLoan(String number){
        this.NumberToLoan = number;
    }

    public Date getDueDate(){
        return DueDate;
    }

    @Override
    public ImageView GetImageURL() {
        return getImageURL(Type);
    }

    @Override
    public void Loan(Connection connection, int customerId, int employeeId) {

        String query = "INSERT INTO `libraryschema`.`loans`(`Date`,`DueDate`,`CustomerId`,`EmployeeId`, `ProductId`, `ProductType`, `NumberLoaned`, `IsActive`) VALUES ('"
                + Dates.GetCurrentDate() + "', '" + Dates.GetDueDate() + "', " + customerId + ", " + employeeId + ", " + Id + ", '" + Type.name() + "', " + getNumberToLoan() + ", 1);";

        DB.Update(connection, query);
    }

    @Override
    public String GetProductInformation() {

        String productInfo = "Name: " + Name + "\n" +
                "Description: " + Description + "\n" +
                "Total Number Owned: " + NumberTotal + "\n" +
                "Number Available: " + getNumberAvailable() + "\n" +
                "Number On Loan: " + NumberOnLoan + "\n\n" +
                "Loans\n";

        if (Loans != null){
            for (Loan loan: Loans) {

                productInfo = productInfo.concat("DUE DATE: " + loan.DueDate + " - Number Loaned: " + loan.NumberLoaned + "\n");
            }
        }
        return productInfo;
    }

    @Override
    public void Return(Connection connection, int customerId){

        String query = "UPDATE `libraryschema`.`loans` SET `IsActive` = 0 WHERE `CustomerId` = " + customerId + " AND `ProductId` = " + Id + " AND `ProductType` = '" + Type.name() + "';";

        DB.Update(connection, query);
    }

    @Override
    public void Update() {
        if (Type == EnumProductType.Dice){
            UpdateProduct(Type.name().toLowerCase(Locale.ROOT));
        }
        else{
            UpdateProduct(Type.name().toLowerCase(Locale.ROOT) + "s");
        }
    }
}
