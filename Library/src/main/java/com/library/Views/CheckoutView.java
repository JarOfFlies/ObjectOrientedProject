package com.library.Views;

import com.library.Classes.Rental.DisplayProduct;
import com.library.Logic.DB.Fetch;
import com.library.Logic.DB.LoanReturn;
import com.library.Logic.Loan.Pricing;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.sql.Date;
import java.text.DecimalFormat;

public class CheckoutView {

    private static ObservableList<DisplayProduct> basket;

    public static void CreateScene(Stage primaryStage, ObservableList<DisplayProduct> products, boolean isReturn){

        basket = products;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Checkout");
        if (isReturn){scenetitle.setText("Returns");}
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label priceLabel = new Label("");

        TableView<DisplayProduct> tableView = new TableView<>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<DisplayProduct, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tableView.getColumns().add(nameColumn);
        tableView.prefHeightProperty().bind(primaryStage.heightProperty());
        tableView.prefWidthProperty().bind(primaryStage.widthProperty());

        tableView.setEditable( true );
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>()
        {
            @Override
            public TableCell call( TableColumn p )
            {
                return new ComboBoxCell();
            }
        };

        TableColumn toLoanColumn = new TableColumn( "To Loan" );
        toLoanColumn.setMinWidth( 100 );
        toLoanColumn.setCellValueFactory( new PropertyValueFactory<DisplayProduct, String>( "NumberToLoan" ) );
        toLoanColumn.setCellFactory( cellFactory );
        toLoanColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<DisplayProduct, String>>()
                {
                    @Override
                    public void handle( TableColumn.CellEditEvent<DisplayProduct, String> t )
                    {
                        ((DisplayProduct) t.getTableView().getItems().get(
                                t.getTablePosition().getRow() )).setNumberToLoan( t.getNewValue() );

                        if (isReturn){
                            priceLabel.setText("Price: \u00A3" + Pricing.GetReturnPrice(basket) + ".00");
                        }
                        else{
                            priceLabel.setText("Price: \u00A3" + new DecimalFormat("#.##").format(Pricing.GetLoanPrice(basket)));
                        }
                    }
                }
        );

        if (!isReturn){
            tableView.getColumns().add(toLoanColumn);
        }
        else{
            TableColumn<DisplayProduct, Date> dueDateColumn = new TableColumn<>("Due Date");
            dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
            tableView.getColumns().add(dueDateColumn);

            TableColumn<DisplayProduct, Integer> availableColumn = new TableColumn<>("Number On Loan");
            availableColumn.setCellValueFactory(new PropertyValueFactory<>("NumberOnLoan"));
            tableView.getColumns().add(availableColumn);
        }

        tableView.setItems(basket);

        grid.add(tableView,0,3,2,1);

        if (isReturn){
            priceLabel.setText("Price: \u00A3" + Pricing.GetReturnPrice(basket) + ".00");
        }
        else{
            priceLabel.setText("Price: \u00A3" + new DecimalFormat("#.##").format(Pricing.GetLoanPrice(basket)));
        }

        VBox priceButtonBox = new VBox(priceLabel);
        priceButtonBox.setSpacing(10);
        priceButtonBox.setPadding(new Insets(5,5,5,5));
        grid.add(priceButtonBox, 0, 4);

        Button checkoutButton = new Button("Loan");
        if (isReturn){
            checkoutButton.setText("Return");
        }
        checkoutButton.setAlignment(Pos.BOTTOM_RIGHT);
        checkoutButton.setPadding(new Insets(5,5,5,5));
        VBox loanButtonBox = new VBox(checkoutButton);
        loanButtonBox.setSpacing(10);
        loanButtonBox.setPadding(new Insets(5,5,5,5));
        loanButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(loanButtonBox, 1, 5);

        Button backButton = new Button("Back");
        backButton.setPadding(new Insets(5,5,5,5));
        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setSpacing(10);
        backButtonBox.setPadding(new Insets(5,5,5,5));
        grid.add(backButtonBox, 0, 5);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 ) {
                DisplayProduct selectedProduct = tableView.getSelectionModel().getSelectedItem();
                if (!basket.contains(selectedProduct)){
                    basket.add(selectedProduct);
                }
            }
        });

        checkoutButton.setOnAction(event -> {
            if (isReturn){
                LoanReturn.ReturnItems(basket);
            }
            else{
                LoanReturn.LoanItems(basket);
            }
            HomeView.CreateScene(primaryStage);
        });

        backButton.setOnAction(event -> ListsView.CreateScene(primaryStage, isReturn, basket));

        primaryStage.setScene(new Scene(grid, 1280, 720));
    }

    static class ComboBoxCell extends TableCell<DisplayProduct, String>
    {

        private ComboBox<String> comboBox;


        public ComboBoxCell()
        {
            comboBox = new ComboBox<>();
        }


        @Override
        public void startEdit()
        {
            if ( !isEmpty() )
            {
                super.startEdit();

                comboBox.setItems( getTableView().getItems().get( getIndex() ).getAvailableList() );
                comboBox.getSelectionModel().select( getItem() );

                comboBox.focusedProperty().addListener( new ChangeListener<Boolean>()
                {
                    @Override
                    public void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue )
                    {
                        if ( !newValue )
                        {
                            commitEdit( comboBox.getSelectionModel().getSelectedItem() );
                        }
                    }
                } );

                setText( null );
                setGraphic( comboBox );
            }
        }


        @Override
        public void cancelEdit()
        {
            super.cancelEdit();

            setText( ( String ) getItem() );
            setGraphic( null );
        }


        @Override
        public void updateItem( String item, boolean empty )
        {
            super.updateItem( item, empty );

            if ( empty )
            {
                setText( null );
                setGraphic( null );
            }
            else
            {
                if ( isEditing() )
                {
                    setText( null );
                    setGraphic( comboBox );
                }
                else
                {
                    setText( getItem() );
                    setGraphic( null );
                }
            }
        }
    }
}