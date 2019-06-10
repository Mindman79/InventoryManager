/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Tristan
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    Product receivedProduct;

    @FXML
    private TextField ProductIDField;
    @FXML
    private TextField ProductNameField;
    @FXML
    private TextField ProductInvField;
    @FXML
    private TextField ProductPriceField;
    @FXML
    private TextField ProductMaxOnHand;
    @FXML
    private TextField ProductMinOnHand;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TableView<Part> PartsTableTop;
    @FXML
    private TableColumn<Part, Integer> PartIDColTop;
    @FXML
    private TableColumn<Part, String> PartNameColTop;
    @FXML
    private TableColumn<Part, Integer> PartInvColTop;
    @FXML
    private TableColumn<Part, Double> PartPriceColTop;
    @FXML
    private TableView<Part> PartsTableBottom;
    @FXML
    private TableColumn<Part, Integer> PartIDColBottom;
    @FXML
    private TableColumn<Part, String> PartNameColBottom;
    @FXML
    private TableColumn<Part, Integer> PartInvColBottom;
    @FXML
    private TableColumn<Part, Double> PartPriceColBottom;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button AddButton;
    @FXML
    private TextField SearchField;
    @FXML
    private Button SearchButton;

    // List of parts associated with this project
    private ObservableList<Part> productParts = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PartsTableTop.setItems(Inventory.getAllParts());
        PartIDColTop.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColTop.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColTop.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColTop.setCellValueFactory(new PropertyValueFactory<>("price"));



        int productAutoID = Inventory.getProductsCount();
        ProductIDField.setText("AUTO GEN: " + productAutoID);
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {

        int id = Inventory.getProductsCount();
        String name = ProductNameField.getText();
        double price = Double.parseDouble(ProductPriceField.getText());
        int stock = Integer.parseInt(ProductInvField.getText());
        int min = Integer.parseInt(ProductMinOnHand.getText());
        int max = Integer.parseInt(ProductMaxOnHand.getText());

        if (min > max) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum product on hand quantity cannot be above maximum product on hand quantity!");
            Optional<ButtonType> result = alert.showAndWait();
            System.out.println("Minimum product on hand quantity cannot be above maximum product on hand quantity!");

        } else {

            //Product newProduct = new Product(id, name, price, stock, min, max);


            //newProduct.addAssociatedParts(productParts);



            //newProduct.setAssociatedParts(productParts);

            //Inventory.addProduct(newProduct);
            //Inventory.deleteProduct(receivedProduct);

            receivedProduct.setId(id);
            receivedProduct.setName(name);
            receivedProduct.setPrice(price);
            receivedProduct.setStock(stock);
            receivedProduct.setMax(max);
            receivedProduct.setMin(min);

            Parent productToSave = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene scene = new Scene(productToSave);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();

        }

    }

    @FXML
    private void CancelButtonHandler(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all fields, proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    @FXML
    private void DeleteButtonhandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part, proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part partToDelete = PartsTableBottom.getSelectionModel().getSelectedItem();
            receivedProduct.getAssociatedParts().remove(partToDelete);




        }

    }

    @FXML
    private void AddButtonHandler(ActionEvent event) {

        Part addPartToProduct = PartsTableTop.getSelectionModel().getSelectedItem();
        receivedProduct.addAssociatedParts(addPartToProduct);


    }

    @FXML
    private void SearchButtonHandler(ActionEvent event) {

        String searchString = SearchField.getText();
        for (Part partToSearch : Inventory.getAllParts()) {

            if ((searchString.matches("(?i:.*" + partToSearch.getName() + ".*)")) || (searchString.equals(new Integer(partToSearch.getId()).toString()))) {

                PartsTableTop.getSelectionModel().select(partToSearch);
                //updateProductPartsTable();

            }

        }

    }

    public void updateProductPartsTable() {

        PartsTableTop.setItems(Inventory.getAllParts());

    }

    //Receive product from something
    public void receiveProduct(Product productToReceive) {

        receivedProduct = productToReceive;
        PartsTableBottom.setItems(productToReceive.getAssociatedParts());
        PartIDColBottom.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColBottom.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvColBottom.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColBottom.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductIDField.setText(String.valueOf(productToReceive.getId()));
        ProductNameField.setText(productToReceive.getName());
        ProductInvField.setText(String.valueOf(productToReceive.getStock()));
        ProductPriceField.setText(String.valueOf(productToReceive.getPrice()));
        ProductMaxOnHand.setText(String.valueOf(productToReceive.getMax()));
        ProductMinOnHand.setText(String.valueOf(productToReceive.getMin()));

        //produ.getAssociatedParts();

    }

}
