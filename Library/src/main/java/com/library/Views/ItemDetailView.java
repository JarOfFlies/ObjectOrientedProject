package com.library.Views;

import com.library.Classes.Rental.*;
import com.library.Enums.EnumProductType;
import com.library.Logic.DB.Fetch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Date;
import java.util.ArrayList;

public class ItemDetailView {

    private static ObservableList<DeckCards> decklist = FXCollections.observableArrayList(new ArrayList<>());

    public static void CreateDisplayProductScene(Stage primaryStage, DisplayProduct product){

        Card card = null;
        Deck deck = null;

        switch (product.Type) {
            case Card -> card = Fetch.FetchCard(product.Id);
            case Dice -> product = Fetch.FetchDie(product.Id);
            case Playmat -> product = Fetch.FetchPlaymat(product.Id);
            case Deck -> deck = Fetch.FetchDeck(product.Id);
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Item Info");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);

        TextField nameTextField = new TextField();
        nameTextField.setText(product.getName());
        grid.add(nameTextField, 1, 1);

        Label descriptionLabel = new Label("Description:");
        grid.add(descriptionLabel, 0, 2);

        TextField descriptionTextField = new TextField();
        descriptionTextField.setText(product.getDescription());
        grid.add(descriptionTextField, 1, 2);

        Label totalLabel = new Label("Total:");

        TextField totalTextField = new TextField();
        totalTextField.setText(String.valueOf(product.NumberTotal));

        Label availableLabel = new Label("Available:");
        grid.add(availableLabel, 0, 4);

        Label availableTextField = new Label();
        availableTextField.setText(String.valueOf(product.getNumberAvailable()));
        availableTextField.setPadding(new Insets(0,0,0,5));
        grid.add(availableTextField, 1, 4);

        Label loanLabel = new Label("Loaned Items");
        grid.add(loanLabel, 0, 5);

        TableView<Loan> tableView = new TableView<>();

        TableColumn<Loan, Date> dateLoanedColumn = new TableColumn<>("Date Loaned");
        dateLoanedColumn.setCellValueFactory(new PropertyValueFactory<>("DateLoaned"));
        tableView.getColumns().add(dateLoanedColumn);

        TableColumn<Loan, Date> dueDateColumn = new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        tableView.getColumns().add(dueDateColumn);

        TableColumn<Loan, String> employeeColumn = new TableColumn<>("Employee");
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("Employee"));
        tableView.getColumns().add(employeeColumn);

        TableColumn<Loan, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        tableView.getColumns().add(customerColumn);

        TableColumn<Loan, Integer> loanedColumn = new TableColumn<>("Number Loaned");
        loanedColumn.setCellValueFactory(new PropertyValueFactory<>("NumberLoaned"));
        tableView.getColumns().add(loanedColumn);

        tableView.prefHeightProperty().bind(primaryStage.heightProperty());
        tableView.prefWidthProperty().bind(primaryStage.widthProperty());
        grid.add(tableView, 0, 6, 2,1);

        GridPane tableGrid = new GridPane();
        tableGrid.setAlignment(Pos.CENTER);
        tableGrid.setPadding(new Insets(5, 5, 5, 5));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(45);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(45);
        tableGrid.getColumnConstraints().addAll(col1,col2,col3);

        //TODO : Deck Builder

//        TableView<DisplayProduct> CardTableView = new TableView<>();
//        CardTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        TableColumn<DisplayProduct, String> nameColumn = new TableColumn<>("Name");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        CardTableView.getColumns().add(nameColumn);
//        CardTableView.prefHeightProperty().bind(primaryStage.heightProperty());
//        CardTableView.prefWidthProperty().bind(primaryStage.widthProperty());

        TableView<DeckCards> basketTable = new TableView<>();
        basketTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<DeckCards, String> basketColumn = new TableColumn<>("Name");
        basketColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        basketTable.getColumns().add(basketColumn);
        TableColumn<DeckCards, Integer> numberColumn = new TableColumn<>("NumberInDeck");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("NumberInDeck"));
        basketTable.getColumns().add(numberColumn);
        basketTable.prefHeightProperty().bind(primaryStage.heightProperty());
        basketTable.prefWidthProperty().bind(primaryStage.widthProperty());
        basketTable.setItems(decklist);

//        VBox basketButtonsBox = new VBox();
//        basketButtonsBox.prefHeightProperty().bind(primaryStage.heightProperty());
//        basketButtonsBox.prefWidthProperty().bind(primaryStage.widthProperty());
//        basketButtonsBox.setAlignment(Pos.CENTER);
//        basketButtonsBox.setSpacing(10);

//        tableGrid.add(CardTableView, 0, 1,1,6);
//        tableGrid.add(basketButtonsBox, 1, 3);
        tableGrid.add(basketTable,0,1);

        basketTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 ) {
                decklist.remove(basketTable.getSelectionModel().getSelectedItem());
            }
        });

//        CardTableView.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2 ) {
//                DisplayProduct selectedProduct = CardTableView.getSelectionModel().getSelectedItem();
//                if (decklist.stream().noneMatch(deckcard -> deckcard.CardId == selectedProduct.Id)){
//                    decklist.add(new DeckCards(selectedProduct.Id, selectedProduct.Name, 1));
//                }
//            }
//        });

        //TODO : Deck Builder

        ImageView itemImageView = null;

        if (product.Type == EnumProductType.Deck & deck != null){
            nameTextField.setText(deck.getName());
            descriptionTextField.setText(deck.getDescription());
            totalTextField.setText(String.valueOf(deck.NumberTotal));
            availableTextField.setText(String.valueOf(deck.getNumberAvailable()));
            tableView.setItems(FXCollections.observableArrayList(deck.Loans));
            itemImageView = deck.GetImageURL();

//            CardTableView.setItems(FXCollections.observableArrayList(Fetch.FetchCards()));
            if (deck.Decklist.size() > 0){
                decklist.addAll(deck.Decklist);
            }
            grid.add(tableGrid,0,7,2,1);
        }

        if (product.Type == EnumProductType.Card & card != null){
            nameTextField.setText(card.getName());
            descriptionTextField.setText(card.getDescription());
            totalTextField.setText(String.valueOf(card.NumberTotal));
            availableTextField.setText(String.valueOf(card.getNumberAvailable()));
            tableView.setItems(FXCollections.observableArrayList(card.Loans));
            itemImageView = card.GetImageURL();

            grid.add(totalLabel, 0, 3);
            grid.add(totalTextField, 1, 3);
        }

        if (card == null & deck == null){
            tableView.setItems(FXCollections.observableArrayList(product.Loans));
            itemImageView = product.GetImageURL();

            grid.add(totalLabel, 0, 3);
            grid.add(totalTextField, 1, 3);
        }


        itemImageView.setPreserveRatio(true);
        itemImageView.setFitHeight(100);
        itemImageView.setFitWidth(100);
        grid.add(itemImageView, 1, 8);

        Button saveBtn = new Button("Save Changes");
        HBox hbsaveBtn = new HBox(10);
        hbsaveBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbsaveBtn.getChildren().add(saveBtn);
        grid.add(hbsaveBtn, 1, 9);

        Button printBtn = new Button("Print item info");
        HBox hbprintBtn = new HBox(10);
        hbprintBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbprintBtn.getChildren().add(printBtn);
        grid.add(hbprintBtn, 1, 10);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 0, 9, 2, 1);

        Button btn = new Button("Back");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 11);

        DisplayProduct finalProduct = product;
        Card finalCard = card;
        Deck finalDeck = deck;
        saveBtn.setOnAction(e -> {
            if (Integer.parseInt(totalTextField.getText()) < finalProduct.NumberOnLoan ){
                actionTarget.setText("Total number of product cannot be lower than number on loan");
                actionTarget.setFill(Color.RED);
            }
            else
            {
                switch (finalProduct.Type) {
                    case Card -> {
                        finalCard.Name = nameTextField.getText();
                        finalCard.Description = descriptionTextField.getText();
                        finalCard.NumberTotal = Integer.parseInt(totalTextField.getText());
                        finalCard.Update();
                    }
                    case Dice, Playmat -> {
                        finalProduct.Name = nameTextField.getText();
                        finalProduct.Description = descriptionTextField.getText();
                        finalProduct.NumberTotal = Integer.parseInt(totalTextField.getText());
                        finalProduct.Update();
                    }
                    case Deck -> {
                        finalDeck.Name = nameTextField.getText();
                        finalDeck.Description = descriptionTextField.getText();
                        finalDeck.Decklist = new ArrayList<>(decklist);
                        finalDeck.Update();
                    }
                }
            }
        });

        btn.setOnAction(e -> ItemListView.CreateScene(primaryStage, finalProduct.Type));

        printBtn.setOnAction(e -> {
            if (finalCard == null & finalDeck == null){
                finalProduct.PrintDetails();
            }
            else{
                if (finalDeck != null){
                    finalDeck.PrintDetails();
                }
                else{
                    finalCard.PrintDetails();
                }
            }
        });

        //TODO: If user is an Admin and accessing from ItemListView, allow editing. Otherwise no editing and add to basket.
        if (LoginView.Employee.isAdmin()){

        }

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }
}
