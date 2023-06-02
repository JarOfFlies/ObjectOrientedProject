package com.library.Classes.Rental;

import com.library.Enums.EnumCardRarity;
import com.library.Enums.EnumProductType;
import com.library.Logic.DB.DB;
import javafx.scene.image.ImageView;

import java.sql.Connection;

public class Card extends RentalBase {

    public Card(){

    }

    public Card(int id, String name, String description, int total, String rarity, int loaned, int inDecks){

        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.NumberTotal = total;
        this.Rarity = EnumCardRarity.valueOf(rarity);
        this.NumberOnLoan = loaned;
        this.NumberInDecks = inDecks;
    }

    public Card(int id, String name){
        this.Id = id;
        this.Name = name;
    }

    public EnumCardRarity Rarity;
    
    public int NumberInDecks;

    public void AddCardToDeck(Connection connection, int deckId, int number){

        String query = "INSERT INTO libraryschema.deckcards(`CardId`, `DeckId`, `Number`) VALUES (" + Id + ", " + deckId + ", " + number + ");";

        DB.Update(connection, query);
    }

    @Override
    public int getNumberAvailable(){
        return NumberTotal - (NumberInDecks + NumberOnLoan);
    }

    @Override
    public ImageView GetImageURL() {
        return getImageURL(EnumProductType.Card);
    }


    @Override
    protected void Loan(Connection connection, int customerId, int employeeId) {

//        String query = "INSERT INTO `libraryschema`.`loans`(`Date`,`DueDate`,`CustomerId`,`EmployeeId`, `ProductId`, `ProductType`, `NumberLoaned`, `IsActive`) VALUES ('"
//                + Dates.GetCurrentDate() + "', '" + Dates.GetDueDate() + "', " + customerId + ", " + employeeId + ", " + Id + ", '" + EnumProductType.Card.name() + "', " + getNumberToLoan() + ", 1);";
//
//        DB.Update(connection, query);
    }

    @Override
    String GetProductInformation() {
        String productInfo = "Name: " + Name + "\n" +
                "Description: " + Description + "\n" +
                "Rarity: " + Rarity.name() + "\n" +
                "Total Number Owned: " + NumberTotal + "\n" +
                "Number Available: " + getNumberAvailable() + "\n" +
                "Number In Decks: " + NumberInDecks + "\n" +
                "Number On Loan: " + NumberOnLoan + "\n\n" +
                "Loans\n";

        for (Loan loan: Loans) {

            productInfo = productInfo.concat("DUE DATE: " + loan.DueDate + " - Number Loaned: " + loan.NumberLoaned + "\n");
        }
        return productInfo;
    }


    @Override
    public void Return(Connection connection, int customerId) {

    }

    @Override
    public void Update() {
        UpdateProduct("cards");
    }
}
