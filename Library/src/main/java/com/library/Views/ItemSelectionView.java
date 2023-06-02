package com.library.Views;

import com.library.Enums.EnumProductType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ItemSelectionView {

    public static void CreateScene(Stage primaryStage){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Item Types");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0);

        Button cardsBtn = new Button("Cards");
        HBox hbcardsBtn = new HBox(10);
        hbcardsBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbcardsBtn.getChildren().add(cardsBtn);
        grid.add(hbcardsBtn, 0, 1);

        cardsBtn.setOnAction(e -> ItemListView.CreateScene(primaryStage, EnumProductType.Card));

        Button decksBtn = new Button("Decks");
        HBox hbdecksBtn = new HBox(10);
        hbdecksBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbdecksBtn.getChildren().add(decksBtn);
        grid.add(hbdecksBtn, 0, 2);

        decksBtn.setOnAction(e -> ItemListView.CreateScene(primaryStage, EnumProductType.Deck));

        Button diceBtn = new Button("Dice");
        HBox diceBtnBox = new HBox(10);
        diceBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        diceBtnBox.getChildren().add(diceBtn);
        grid.add(diceBtnBox, 0, 3);

        diceBtn.setOnAction(e -> ItemListView.CreateScene(primaryStage, EnumProductType.Dice));

        Button playmatsBtn = new Button("Playmats");
        HBox playmatsBtnBox = new HBox(10);
        playmatsBtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        playmatsBtnBox.getChildren().add(playmatsBtn);
        grid.add(playmatsBtnBox, 0, 4);

        playmatsBtn.setOnAction(e -> ItemListView.CreateScene(primaryStage, EnumProductType.Playmat));

        Button Backbtn = new Button("Back");
        HBox BackbtnBox = new HBox(10);
        BackbtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        BackbtnBox.getChildren().add(Backbtn);
        grid.add(BackbtnBox, 0, 5);

        Backbtn.setOnAction(e -> {
            HomeView.CreateScene(primaryStage);
        });

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }
}
