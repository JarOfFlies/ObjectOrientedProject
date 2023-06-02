package com.library.Views;

import com.library.Classes.Rental.DisplayProduct;
import com.library.Enums.EnumProductType;
import com.library.Logic.DB.Fetch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ListsView {

    private static ObservableList<DisplayProduct> productsForDisplay;
    private static ObservableList<DisplayProduct> basket;

    public static void CreateScene(Stage primaryStage, boolean isReturns, ObservableList<DisplayProduct> list){

        ArrayList<DisplayProduct> products;

        if (isReturns){
            products = Fetch.FetchLoanedItems();
        }
        else{
            products = Fetch.FetchDisplayProducts();
        }

        productsForDisplay = FXCollections.observableArrayList(products);

        basket = Objects.requireNonNullElseGet(list, () -> FXCollections.observableArrayList(new ArrayList<>()));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Items");
        if (isReturns){scenetitle.setText("Returns");}
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        ToggleGroup radioButtonToggleGroup = new ToggleGroup();

        RadioButton radioButtonAll = new RadioButton("All");
        radioButtonAll.setUserData("All");
        radioButtonAll.setToggleGroup(radioButtonToggleGroup);
        radioButtonAll.setPadding(new Insets(5, 5, 5, 5));
        radioButtonAll.setSelected(true);

        RadioButton radioButtonCards = new RadioButton("Cards");
        radioButtonCards.setUserData("Cards");
        radioButtonCards.setToggleGroup(radioButtonToggleGroup);
        radioButtonCards.setPadding(new Insets(5, 5, 5, 5));

        RadioButton radioButtonDice = new RadioButton("Dice");
        radioButtonDice.setUserData("Dice");
        radioButtonDice.setToggleGroup(radioButtonToggleGroup);
        radioButtonDice.setPadding(new Insets(5, 5, 5, 5));

        RadioButton radioButtonPlaymats = new RadioButton("Playmats");
        radioButtonPlaymats.setUserData("Playmats");
        radioButtonPlaymats.setToggleGroup(radioButtonToggleGroup);
        radioButtonPlaymats.setPadding(new Insets(5, 5, 5, 5));

        RadioButton radioButtonDecks = new RadioButton("Decks");
        radioButtonDecks.setUserData("Decks");
        radioButtonDecks.setToggleGroup(radioButtonToggleGroup);
        radioButtonDecks.setPadding(new Insets(5, 5, 5, 5));

        grid.add(radioButtonAll, 0,1);
        grid.add(radioButtonCards, 1,1);
        grid.add(radioButtonDice, 2,1);
        grid.add(radioButtonDecks, 3,1);
        grid.add(radioButtonPlaymats, 4,1);

        TextField searchBar = new TextField();
        searchBar.setPadding(new Insets(5, 5, 5, 5));

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.setPadding(new Insets(5,5,5, 5));

        HBox searchPane = new HBox(searchBar, searchButton);
        searchPane.setPadding(new Insets(5,5,5,5));
        grid.add(searchPane, 5, 1, 1, 1);

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        checkoutButton.setPadding(new Insets(5,5,5,5));

        Button backButton = new Button("Back");
        backButton.setPadding(new Insets(5,5,5,5));
        HBox backButtonBox = new HBox(backButton, checkoutButton);
        backButtonBox.setSpacing(10);
        backButtonBox.setPadding(new Insets(5,5,5,5));
        grid.add(backButtonBox, 0, 4,2,1);

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

        TableView<DisplayProduct> tableView = new TableView<>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<DisplayProduct, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        TableColumn<DisplayProduct, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        TableColumn<DisplayProduct, Integer> availableColumn = new TableColumn<>("Number Available");

        if (isReturns){
            availableColumn.setText("Number On Loan");
            availableColumn.setCellValueFactory(new PropertyValueFactory<>("NumberOnLoan"));
        }
        else{
            availableColumn.setCellValueFactory(new PropertyValueFactory<>("NumberAvailable"));
        }

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(descriptionColumn);
        tableView.getColumns().add(availableColumn);
        tableView.prefHeightProperty().bind(primaryStage.heightProperty());
        tableView.prefWidthProperty().bind(primaryStage.widthProperty());
        tableView.setItems(productsForDisplay);

        TableView<DisplayProduct> basketTable = new TableView<>();
        basketTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<DisplayProduct, String> basketColumn = new TableColumn<>("Basket");
        if (isReturns){
            basketColumn.setText("To Return");
        }
        basketColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        basketTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        basketTable.getColumns().add(basketColumn);
        basketTable.prefHeightProperty().bind(primaryStage.heightProperty());
        basketTable.prefWidthProperty().bind(primaryStage.widthProperty());
        basketTable.setItems(basket);

        Button addToBasketButton = new Button("->");
        Button removeFromBasketButton = new Button("<-");
        VBox basketButtonsBox = new VBox(addToBasketButton, removeFromBasketButton);
        basketButtonsBox.prefHeightProperty().bind(primaryStage.heightProperty());
        basketButtonsBox.prefWidthProperty().bind(primaryStage.widthProperty());
        basketButtonsBox.setAlignment(Pos.CENTER);
        basketButtonsBox.setSpacing(10);

        tableGrid.add(tableView, 0, 1,1,6);
        tableGrid.add(basketButtonsBox, 1, 3);
        tableGrid.add(basketTable,2,1,1,6);

        grid.add(tableGrid,0,3,7,1);

        addToBasketButton.setOnAction(event -> {
            ObservableList<DisplayProduct> selectedProducts = tableView.getSelectionModel().getSelectedItems();
            for (DisplayProduct selectedProduct : selectedProducts) {
                if (basket.stream().noneMatch(product -> product.Id == selectedProduct.Id && product.Type == selectedProduct.Type)){
                    basket.add(selectedProduct);
                }
            }
        });

        removeFromBasketButton.setOnAction(event -> basket.removeAll(basketTable.getSelectionModel().getSelectedItems()));

        basketTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 ) {
                basket.remove(basketTable.getSelectionModel().getSelectedItem());
            }
        });

        //TODO: Change to navigate to ItemDetailView
        tableView.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2 ) {
//                DisplayProduct selectedProduct = tableView.getSelectionModel().getSelectedItem();
//                if (basket.stream().noneMatch(product -> product.Id == selectedProduct.Id && product.Type == selectedProduct.Type)){
//                    basket.add(selectedProduct);
//                }
//            }

        });

        checkoutButton.setOnAction(event -> {
            CheckoutView.CreateScene(primaryStage, basket, isReturns);
        });

        backButton.setOnAction(event -> HomeView.CreateScene(primaryStage));

        ArrayList<DisplayProduct> finalProducts = products;
        searchButton.setOnAction(e -> FilterTable(finalProducts, radioButtonToggleGroup.getSelectedToggle(), searchBar.getText()));

        radioButtonToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> FilterTable(finalProducts, radioButtonToggleGroup.getSelectedToggle(), searchBar.getText()));

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }

    /**
     * @param products List of all products in the database
     * @param radioButtonToggle Selected radio button in filter toggle group
     * @param searchParam Search entry field text
     *
     * Iterates through products, adding filtered products to productsForDisplay
     */
    private static void FilterTable(ArrayList<DisplayProduct> products, Toggle radioButtonToggle, String searchParam) {
        if (radioButtonToggle != null) {

            productsForDisplay.clear();
            switch (radioButtonToggle.getUserData().toString()) {

                case "Cards":
                    for (DisplayProduct product : products) {
                        if (product.Type == EnumProductType.Card && product.Name.toLowerCase().contains(searchParam.toLowerCase(Locale.ROOT))) {
                            productsForDisplay.add(product);
                        }
                    }
                    break;

                case "Dice":
                    for (DisplayProduct product : products) {
                        if (product.Type == EnumProductType.Dice && product.Name.toLowerCase().contains(searchParam.toLowerCase(Locale.ROOT))) {
                            productsForDisplay.add(product);
                        }
                    }
                    break;

                case "Decks":
                    for (DisplayProduct product : products) {
                        if (product.Type == EnumProductType.Deck && product.Name.toLowerCase().contains(searchParam.toLowerCase(Locale.ROOT))) {
                            productsForDisplay.add(product);
                        }
                    }
                    break;

                case "Playmats":
                    for (DisplayProduct product : products) {
                        if (product.Type == EnumProductType.Playmat && product.Name.toLowerCase().contains(searchParam.toLowerCase(Locale.ROOT))) {
                            productsForDisplay.add(product);
                        }
                    }
                    break;

                default:
                    for (DisplayProduct product : products) {
                        if (product.Name.toLowerCase().contains(searchParam.toLowerCase(Locale.ROOT))) {
                            productsForDisplay.add(product);
                        }
                    }
            }

        }
    }
}
