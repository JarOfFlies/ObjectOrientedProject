package com.library.Classes.Rental;

import com.library.Enums.EnumProductType;
import com.library.Logic.DB.DB;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * RentalBase
 */
public abstract class RentalBase {

    public int Id;

    public String Name;

    public String Description;
    
    public int NumberTotal;

    public int NumberOnLoan;

    public ArrayList<Loan> Loans;

    public int getNumberAvailable() {

        return NumberTotal - getNumberOnLoan();
    }

    public int getNumberOnLoan() {
        if (Loans != null){
            NumberOnLoan = 0;
            for (Loan loan : Loans){
                NumberOnLoan += loan.NumberLoaned;
            }
        }
        return NumberOnLoan;
    }

    public String getName(){
        return Name;
    }

    public String getDescription(){
        return Description;
    }

    public void PrintDetails(){

        try {
            File myObj = new File(Name + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                PrintWriter writer = new PrintWriter(myObj);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(Name + ".txt");

            myWriter.write(GetProductInformation());

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    protected ImageView getImageURL(EnumProductType type){
        ImageView imageView = null;
        try{
            imageView = new ImageView(getName() + ".jpg");
        } catch (Exception e) {
            imageView = new ImageView(type.name() + ".jpg");
        }
        return imageView;
    }

    protected void UpdateProduct(String table){
        Connection connection = DB.CreateConnection();
        String query = "UPDATE `libraryschema`.`" + table + "`\n" +
                "SET\n" +
                "`Name` = '" + Name + "',\n" +
                "`Description` = '" + Description + "',\n" +
                "`Total` = " + NumberTotal + "\n" +
                "WHERE `Id` = " + Id + ";";
        DB.Update(connection, query);
        DB.CloseConnection(connection);
    }

    abstract ImageView GetImageURL();

    abstract void Loan(Connection connection, int customerId, int employeeId);

    abstract String GetProductInformation();

    abstract void Return(Connection connection, int customerId);

    abstract void Update();
}