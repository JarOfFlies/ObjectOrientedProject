package com.library.Logic.Loan;

import com.library.Classes.Rental.DisplayProduct;
import com.library.Logic.DB.Fetch;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class Pricing {

    public static int GetReturnPrice(ObservableList<DisplayProduct> basket){
        int cost = 0;
        Date today = new Date(new java.util.Date().getTime());
        for (DisplayProduct item : basket){
            long diff = ChronoUnit.DAYS.between(item.DueDate.toLocalDate(), today.toLocalDate());
            if(diff > 0){
                cost += diff;
            }
            System.out.println(diff);
        }
        return cost;
    }

    public static double GetLoanPrice(ObservableList<DisplayProduct> basket){

        double cost = 0;
        for (DisplayProduct item : basket) {
            switch (item.Type) {
                case Card -> {
                    switch (Fetch.FetchCardRarity(item.Id)) {
                        case Common -> cost += (Integer.parseInt(item.NumberToLoan) * 0.5);
                        case Uncommon -> cost += Integer.parseInt(item.NumberToLoan);
                        case Rare -> cost += (Integer.parseInt(item.NumberToLoan) * 2);
                        case Mythic -> cost += (Integer.parseInt(item.NumberToLoan) * 3);
                    }
                }
                case Dice -> cost += Integer.parseInt(item.NumberToLoan);
                case Playmat -> cost += (Integer.parseInt(item.NumberToLoan) * 5);
                case Deck -> cost += 5;
            }
        }

        return cost;
    }
}
