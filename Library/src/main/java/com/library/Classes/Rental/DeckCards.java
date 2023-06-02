package com.library.Classes.Rental;

public class DeckCards {

    public DeckCards(int cardId,  int number) {
        this.CardId = cardId;
        this.NumberInDeck = number;
    }

    public DeckCards(int cardId, String name, int number) {
        this.CardId = cardId;
        this.Name = name;
        this.NumberInDeck = number;
    }

    public int CardId;

    public String Name;

    public int NumberInDeck;

    public int getCardId(){
        return this.CardId;
    }

    public String getName(){
        return this.Name;
    }

    public int getNumberInDeck(){
        return this.NumberInDeck;
    }
}
