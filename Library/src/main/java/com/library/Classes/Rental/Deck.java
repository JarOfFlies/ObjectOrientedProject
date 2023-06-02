package com.library.Classes.Rental;

import com.library.Enums.EnumProductType;
import com.library.Logic.DB.DB;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.util.ArrayList;

public class Deck extends RentalBase {

    public Deck() {
        this.Decklist = new ArrayList<DeckCards>();
    }

    public Deck(int id, String name, String description){
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.NumberTotal = 1;
    }

    public ArrayList<DeckCards> Decklist;

    public void AddCardToDeck(DeckCards card) {

        if (Decklist == null){
            Decklist = new ArrayList<>();
        }
        Decklist.add(card);
    }

    public ArrayList<DeckCards> GetDecklist(){
        return this.Decklist;
    }

    @Override
    protected void Loan(Connection connection, int customerId, int employeeId) {

//        String query = "INSERT INTO `libraryschema`.`loans`(`Date`,`DueDate`,`CustomerId`,`EmployeeId`, `ProductId`, `ProductType`, `NumberLoaned`, `IsActive`) VALUES ('"
//                + Dates.GetCurrentDate() + "', '" + Dates.GetDueDate() + "', " + customerId + ", " + employeeId + ", " + Id + ", '" + EnumProductType.Deck.name() + "', 1, 1);";
//
//        DB.Update(connection, query);
    }

    @Override
    String GetProductInformation() {
        String productInfo = "Name: " + Name + "\n" +
                "Description: " + Description + "\n" +
                "Total Number Owned: " + NumberTotal + "\n" +
                "Number Available: " + getNumberAvailable() + "\n" +
                "Number On Loan: " + NumberOnLoan + "\n\n" +
                "Loans\n";

        for (Loan loan: Loans) {

            productInfo = productInfo.concat("DUE DATE: " + loan.DueDate + " - Number Loaned: " + loan.NumberLoaned + "\n");
        }
        return productInfo;
    }

    @Override
    public ImageView GetImageURL() {
        return getImageURL(EnumProductType.Deck);
    }

    @Override
    public void Return(Connection connection, int customerId) {

    }

    @Override
    public void Update() {
        Connection connection = DB.CreateConnection();
        String query = "UPDATE `libraryschema`.`decks`\n" +
                "SET\n" +
                "`Name` = '" + Name + "',\n" +
                "`Description` = '" + Description + "',\n" +
                "`Total` = " + NumberTotal + "\n" +
                "WHERE `Id` = " + Id + ";";
        DB.Update(connection, query);
//        DB.Update(connection, "DELETE FROM `libraryschema`.`deckcards`\n" +
//                "WHERE DeckId = " + Id);
//        for (DeckCards card : Decklist){
//            DB.Update(connection, "INSERT INTO `libraryschema`.`deckcards`\n" +
//                    "(`CardId`,\n" +
//                    "`DeckId`,\n" +
//                    "`Number`)\n" +
//                    "VALUES(\n" +
//                    card.CardId + ",\n" +
//                    Id+ ",\n" +
//                    "1);");
//        }
        DB.CloseConnection(connection);
    }
}
