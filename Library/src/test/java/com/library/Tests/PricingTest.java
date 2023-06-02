package com.library.Tests;

import com.library.Classes.Rental.DisplayProduct;
import com.library.Enums.EnumProductType;
import com.library.Logic.Loan.Pricing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PricingTest {

    ObservableList<DisplayProduct> basket = null;

    @Test
    void getReturnPrice_OneDayLate() {
        ArrayList<DisplayProduct> array = new ArrayList<DisplayProduct>();
        DisplayProduct one = new DisplayProduct();
        one.Type = EnumProductType.Dice;
        one.DueDate  = Date.valueOf(new Date(new java.util.Date().getTime()).toLocalDate().plusDays(-1).toString());
        array.add(one);
        basket = FXCollections.observableArrayList(array);
        assertEquals(1, Pricing.GetReturnPrice(basket));
    }

    @Test
    void getReturnPrice_OneWeekLate() {
        ArrayList<DisplayProduct> array = new ArrayList<DisplayProduct>();
        DisplayProduct one = new DisplayProduct();
        one.Type = EnumProductType.Dice;
        one.DueDate  = Date.valueOf(new Date(new java.util.Date().getTime()).toLocalDate().plusDays(-7).toString());
        array.add(one);
        basket = FXCollections.observableArrayList(array);
        assertEquals(7, Pricing.GetReturnPrice(basket));
    }

    @Test
    @DisplayName("Test Loan Price For Dice")
    void getDiceLoanPrice() {
        ArrayList<DisplayProduct> array = new ArrayList<DisplayProduct>();
        DisplayProduct one = new DisplayProduct();
        one.Type = EnumProductType.Dice;
        one.NumberToLoan = "3";
        array.add(one);
        basket = FXCollections.observableArrayList(array);
        assertEquals(3, Pricing.GetLoanPrice(basket));
    }

    @Test
    @DisplayName("Test Loan Price For Playmat")
    void getPlaymatLoanPrice() {
        ArrayList<DisplayProduct> array = new ArrayList<DisplayProduct>();
        DisplayProduct one = new DisplayProduct();
        one.Type = EnumProductType.Playmat;
        one.NumberToLoan = "3";
        array.add(one);
        basket = FXCollections.observableArrayList(array);
        assertEquals(15, Pricing.GetLoanPrice(basket));
    }

    @Test
    @DisplayName("Test Loan Price For Decks")
    void getDeckLoanPrice() {
        ArrayList<DisplayProduct> array = new ArrayList<DisplayProduct>();
        DisplayProduct one = new DisplayProduct();
        one.Type = EnumProductType.Deck;
        array.add(one);
        basket = FXCollections.observableArrayList(array);
        assertEquals(5, Pricing.GetLoanPrice(basket));
    }
}