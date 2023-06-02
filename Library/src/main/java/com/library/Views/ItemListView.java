package com.library.Views;

import com.library.Classes.Rental.DisplayProduct;
import com.library.Enums.EnumProductType;
import com.library.Logic.DB.Fetch;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ItemListView {

    public static void CreateScene(Stage primaryStage, EnumProductType type){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Items");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0);

        TableView<DisplayProduct> basketTable = new TableView<>();
        TableColumn<DisplayProduct, String> basketColumn = new TableColumn<>("Basket");
        basketColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        basketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        basketTable.getColumns().add(basketColumn);
        basketTable.prefHeightProperty().bind(primaryStage.heightProperty());
        basketTable.prefWidthProperty().bind(primaryStage.widthProperty());
        grid.add(basketTable,0, 1);

        basketTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 ) {
                DisplayProduct item = basketTable.getSelectionModel().getSelectedItem();
                item.Type = type;
                ItemDetailView.CreateDisplayProductScene(primaryStage, item);
            }
        });

        switch (type) {
            case Card -> basketTable.setItems(FXCollections.observableArrayList(Fetch.FetchCards()));
            case Dice -> basketTable.setItems(FXCollections.observableArrayList(Fetch.FetchDice()));
            case Deck -> basketTable.setItems(FXCollections.observableArrayList(Fetch.FetchDecks()));
            case Playmat -> basketTable.setItems(FXCollections.observableArrayList(Fetch.FetchPlaymats()));
        }

        Button Backbtn = new Button("Back");
        HBox BackbtnBox = new HBox(10);
        BackbtnBox.setAlignment(Pos.BOTTOM_RIGHT);
        BackbtnBox.getChildren().add(Backbtn);
        grid.add(BackbtnBox, 0, 2);

        Backbtn.setOnAction(e -> {
            ItemSelectionView.CreateScene(primaryStage);
        });

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }
}
