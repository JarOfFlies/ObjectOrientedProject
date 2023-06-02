package com.library.Logic.DB;

import com.library.Classes.Rental.*;
import com.library.Enums.EnumCardRarity;
import com.library.Enums.EnumProductType;
import com.library.Views.HomeView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.library.Enums.EnumProductType.Playmat;

public class Fetch {

    /**
     * Used to fetch individual card from database based on provided ID
     */
    public static Card FetchCard(int id) {

        Card card = new Card();

        Connection connection = DB.CreateConnection();

        try {
            ResultSet results = DB.Fetch(connection, "SELECT  cards.Name, cards.Description, cards.Total, cards.Rarity, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0),(SELECT COALESCE(SUM(libraryschema.deckcards.Number), 0) FROM libraryschema.deckcards WHERE libraryschema.deckcards.Id = cards.Id)\n" +
                    "FROM libraryschema.cards cards \n" +
                    "LEFT JOIN libraryschema.loans\n" +
                    "ON libraryschema.cards.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Card' AND libraryschema.loans.IsActive = 1\n" +
                    "LEFT JOIN libraryschema.deckcards \n" +
                    "ON libraryschema.cards.Id = libraryschema.deckcards.CardId\n" +
                    "WHERE cards.Id = " + id + ";");

            while (results.next()) {
                card.Name = results.getString(1);
                card.Description = results.getString(2);
                card.NumberTotal = results.getInt(3);
                card.NumberOnLoan = results.getInt(5);
                card.NumberInDecks = results.getInt(6);
                card.Rarity = EnumCardRarity.valueOf(results.getString(4));

            }
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet loanResults = DB.Fetch(connection, "SELECT Date, DueDate, EmployeeId, CustomerId, NumberLoaned FROM libraryschema.Loans WHERE ProductId = " + id + " AND ProductType = '" + EnumProductType.Card.name() + "' AND IsActive = 1;");

            ArrayList<Loan> loans = new ArrayList<>();
            while (loanResults.next()) {
                loans.add(new Loan(loanResults.getDate(1), loanResults.getDate(2), loanResults.getInt(3), loanResults.getInt(4), loanResults.getInt(5)));
            }
            card.Loans = loans;
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return card;
    }

    /**
     * Used to fetch individual card's rarity from database based from provided ID
     */
    public static EnumCardRarity FetchCardRarity(int id) {

        EnumCardRarity rarity = EnumCardRarity.Common;

        Connection connection = DB.CreateConnection();

        try {
            ResultSet results = DB.Fetch(connection, "SELECT Rarity FROM libraryschema.cards WHERE Id =" + id);

            while (results.next()) {
                rarity = EnumCardRarity.valueOf(results.getString(1));
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return rarity;
    }

    /**
     * Used to fetch list of all Cards in database for display
     */
    public static ArrayList<DisplayProduct> FetchCards() {

        ArrayList<DisplayProduct> cards = new ArrayList<>();

        Connection connection = DB.CreateConnection();

        ResultSet results = DB.Fetch(connection, "SELECT Id, Name FROM libraryschema.cards;");

        try {
            while (results.next()) {

                cards.add(new DisplayProduct(results.getInt(1), results.getString(2)));
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return cards;
    }

    /**
     * Used to fetch an individual deck from database based on provided ID
     */
    public static Deck FetchDeck(int id) {

        Deck deck = null;

        Connection connection = DB.CreateConnection();

        ResultSet results = DB.Fetch(connection, "SELECT deck.Name, deck.Description, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0) " +
                "FROM libraryschema.decks deck " +
                "LEFT JOIN libraryschema.loans " +
                "ON deck.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Deck' AND libraryschema.loans.IsActive = 1 " +
                "WHERE deck.Id = " + id + ";");

        try {
            while (results.next()) {
                String name = results.getString(1);
                String description = results.getString(2);

                deck = new Deck(id, name, description);
            }
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet loanResults = DB.Fetch(connection, "SELECT Date, DueDate, EmployeeId, CustomerId, NumberLoaned FROM libraryschema.Loans WHERE ProductId = " + id + " AND ProductType = '" + EnumProductType.Deck.name() + "' AND IsActive = 1;");

            ArrayList<Loan> loans = new ArrayList<>();
            while (loanResults.next()) {
                loans.add(new Loan(loanResults.getDate(1), loanResults.getDate(2), loanResults.getInt(3), loanResults.getInt(4), loanResults.getInt(5)));
            }
            deck.Loans = loans;
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet decklistResults = DB.Fetch(connection, "SELECT deckcards.Id, libraryschema.cards.Name, deckcards.Number FROM libraryschema.deckcards deckcards LEFT JOIN libraryschema.cards ON libraryschema.cards.Id = deckcards.CardId WHERE deckcards.DeckId = " + id);

            deck.Decklist = new ArrayList<>();
            while (decklistResults.next()) {
                deck.AddCardToDeck(new DeckCards(decklistResults.getInt(1), decklistResults.getString(2), decklistResults.getInt(3)));
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return deck;
    }

    /**
     * Used to fetch list of all Decks in database for display
     */
    public static ArrayList<DisplayProduct> FetchDecks() {

        ArrayList<DisplayProduct> decks = new ArrayList<>();

        Connection connection = DB.CreateConnection();

        ResultSet results = DB.Fetch(connection, "SELECT Id, Name, Description FROM libraryschema.decks;");

        try {
            while (results.next()) {
                int id = results.getInt(1);
                String name = results.getString(2);
                DisplayProduct deck = new DisplayProduct(id, name);
                decks.add(deck);
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return decks;
    }

    /**
     * Used to fetch an individual die from the database based on provided ID
     */
    public static DisplayProduct FetchDie(int id) {

        return FetchDisplayProduct("SELECT dice.Name, dice.Description, dice.Total, COALESCE(SUM(libraryschema.loans.NumberLoaned),0) " +
                "FROM libraryschema.dice dice " +
                "LEFT JOIN libraryschema.loans " +
                "ON dice.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Dice' AND libraryschema.loans.IsActive = 1 " +
                "WHERE dice.Id = " + id + ";", id, EnumProductType.Dice);
    }

    /**
     * Used to fetch list of all Dice in database for display
     */
    public static ArrayList<DisplayProduct> FetchDice() {

        ArrayList<DisplayProduct> dice = new ArrayList<>();

        Connection connection = DB.CreateConnection();

        ResultSet results = DB.Fetch(connection, "SELECT Id, Name FROM libraryschema.dice;");

        try {
            while (results.next()) {
                int id = results.getInt(1);
                String name = results.getString(2);
                DisplayProduct die = new DisplayProduct(id, name);
                dice.add(die);
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return dice;
    }

    /**
     * Used to fetch DisplayProducts using provided query
     */
    private static DisplayProduct FetchDisplayProduct(String query, int id, EnumProductType type) {

        DisplayProduct displayProduct = new DisplayProduct();

        Connection connection = DB.CreateConnection();

        ResultSet results = DB.Fetch(connection, query);

        try {
            while (results.next()) {
                displayProduct.Id = id;
                displayProduct.Name = results.getString(1);
                displayProduct.Description = results.getString(2);
                displayProduct.NumberTotal = results.getInt(3);
                displayProduct.Type = type;
                displayProduct.NumberToLoan = String.valueOf(results.getInt(4));
            }
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet loanResults = DB.Fetch(connection, "SELECT Date, DueDate, EmployeeId, CustomerId, NumberLoaned FROM libraryschema.Loans WHERE ProductId = " + id + " AND ProductType = '" + type.name() + "' AND IsActive = 1;");

            ArrayList<Loan> loans = new ArrayList<>();
            while (loanResults.next()) {
                loans.add(new Loan(loanResults.getDate(1), loanResults.getDate(2), loanResults.getInt(3), loanResults.getInt(4), loanResults.getInt(5)));
            }
            displayProduct.Loans = loans;
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return displayProduct;
    }

    /**
     * Used to fetch list of all items in database for display
     */
    public static ArrayList<DisplayProduct> FetchDisplayProducts() {

        ArrayList<DisplayProduct> products = new ArrayList<>();

        Connection connection = DB.CreateConnection();


        try {
            ResultSet results = DB.Fetch(connection, "SELECT cards.Id, cards.Name, cards.Description, cards.Total, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0),(SELECT COALESCE(SUM(libraryschema.deckcards.Number), 0) FROM libraryschema.deckcards WHERE libraryschema.deckcards.Id = cards.Id)\n" +
                    "                    FROM libraryschema.cards cards \n" +
                    "                    LEFT JOIN libraryschema.loans\n" +
                    "                    ON libraryschema.cards.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Card' AND libraryschema.loans.IsActive = 1\n" +
                    "                    LEFT JOIN libraryschema.deckcards \n" +
                    "                    ON libraryschema.cards.Id = libraryschema.deckcards.CardId\n" +
                    "                    GROUP BY cards.Id;");

            while (results.next()) {
                DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), EnumProductType.Card, results.getInt(4), results.getInt(5) + results.getInt(6));
                products.add(product);
            }
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet results = DB.Fetch(connection, "SELECT dice.Id, dice.Name, dice.Description, dice.Total, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0) " +
                    "FROM libraryschema.dice dice " +
                    "LEFT JOIN libraryschema.loans " +
                    "ON dice.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Dice' AND libraryschema.loans.IsActive = 1 " +
                    "GROUP BY dice.Id ;");

            while (results.next()) {
                DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), EnumProductType.Dice, results.getInt(4), results.getInt(5));
                products.add(product);
            }
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet results = DB.Fetch(connection, "SELECT deck.Id, deck.Name, deck.Description, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0) " +
                    "FROM libraryschema.decks deck " +
                    "LEFT JOIN libraryschema.loans " +
                    "ON deck.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Deck' AND libraryschema.loans.IsActive = 1 " +
                    "GROUP BY deck.Id;");

            while (results.next()) {
                DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), EnumProductType.Deck, 1, results.getInt(4));
                products.add(product);
            }
        } catch (SQLException sqlEx) {

        }

        try {
            ResultSet results = DB.Fetch(connection, "SELECT playmats.Id, playmats.Name, playmats.Description, playmats.Total, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0) " +
                    "FROM libraryschema.playmats playmats " +
                    "LEFT JOIN libraryschema.loans " +
                    "ON playmats.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Playmat' AND libraryschema.loans.IsActive = 1 " +
                    "GROUP BY playmats.Id ;");

            while (results.next()) {
                DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), Playmat, results.getInt(4), results.getInt(5));
                products.add(product);
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return products;
    }

    /**
     * Used to fetch individual playmat from database based on provided ID
     */
    public static DisplayProduct FetchPlaymat(int id) {


        return FetchDisplayProduct("SELECT playmats.Name, playmats.Description, playmats.Total, COALESCE(SUM(libraryschema.loans.NumberLoaned), 0) " +
                "FROM libraryschema.playmats playmats " +
                "LEFT JOIN libraryschema.loans " +
                "ON playmats.Id = libraryschema.loans.ProductId AND libraryschema.loans.ProductType = 'Playmat' AND libraryschema.loans.IsActive = 1 " +
                "WHERE playmats.Id = " + id + ";", id, Playmat);

    }

    /**
     * Used to fetch list of all Playmats in database for display
     */
    public static ArrayList<DisplayProduct> FetchPlaymats() {

        ArrayList<DisplayProduct> playmats = new ArrayList<>();

        Connection connection = DB.CreateConnection();

        ResultSet results = DB.Fetch(connection, "SELECT Id, Name FROM libraryschema.playmats;");

        try {
            while (results.next()) {
                int id = results.getInt(1);
                String name = results.getString(2);
                DisplayProduct playmat = new DisplayProduct(id, name);
                playmats.add(playmat);
            }
        } catch (SQLException sqlEx) {

        }

        DB.CloseConnection(connection);
        return playmats;
    }

    /**
     * Used to fetch list of all items loaned by a customer for display
     */
    public static ArrayList<DisplayProduct> FetchLoanedItems() {

        ArrayList<DisplayProduct> products = new ArrayList<>();
        ArrayList<Loan> loans = new ArrayList<>();

        Connection connection = DB.CreateConnection();

        try {
            ResultSet loanResults = DB.Fetch(connection, "SELECT Id, ProductId, ProductType, NumberLoaned, DueDate FROM libraryschema.Loans WHERE CustomerId = " + HomeView.Customer.Id + " AND IsActive = 1;");

            while (loanResults.next()) {
                loans.add(new Loan(loanResults.getInt(1), loanResults.getInt(2), loanResults.getString(3), loanResults.getInt(4), loanResults.getDate(5)));
            }
        } catch (SQLException sqlEx) {

        }
        for (Loan loan : loans) {
            switch (loan.ProductType) {
                case Card -> {
                    try {
                        ResultSet results = DB.Fetch(connection, "SELECT Id, Name, Description \n" +
                                "FROM libraryschema.cards \n" +
                                "WHERE Id =" + loan.ProductId);

                        while (results.next()) {
                            DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), EnumProductType.Card, loan.NumberLoaned, loan.DueDate);
                            products.add(product);
                        }
                    } catch (SQLException sqlEx) {

                    }

                }
                case Dice -> {
                    try {
                        ResultSet results = DB.Fetch(connection, "SELECT Id, Name, Description \n" +
                                "FROM libraryschema.dice \n" +
                                "WHERE Id =" + loan.ProductId);

                        while (results.next()) {
                            DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), EnumProductType.Dice, loan.NumberLoaned, loan.DueDate);
                            products.add(product);
                        }
                    } catch (SQLException sqlEx) {

                    }
                }
                case Playmat -> {
                    try {
                        ResultSet results = DB.Fetch(connection, "SELECT Id, Name, Description \n" +
                                "FROM libraryschema.playmats \n" +
                                "WHERE Id =" + loan.ProductId);

                        while (results.next()) {
                            DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), Playmat, loan.NumberLoaned, loan.DueDate);
                            products.add(product);
                        }
                    } catch (SQLException sqlEx) {

                    }

                }
                case Deck -> {
                    try {
                        ResultSet results = DB.Fetch(connection, "SELECT Id, Name, Description \n" +
                                "FROM libraryschema.decks \n" +
                                "WHERE Id =" + loan.ProductId);

                        while (results.next()) {
                            DisplayProduct product = new DisplayProduct(results.getInt(1), results.getString(2), results.getString(3), EnumProductType.Deck, loan.NumberLoaned, loan.DueDate);
                            products.add(product);
                        }
                    } catch (SQLException sqlEx) {


                    }
                }
            }
        }
        DB.CloseConnection(connection);
        return products;
    }
}