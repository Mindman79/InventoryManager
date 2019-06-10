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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import static model.Inventory.getAllParts;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Tristan
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    private Button Exit;

    @FXML
    private Button DeletePartButton;

    @FXML
    private Button SearchPartsButton;

    @FXML
    private TextField SearchPartsField;

    @FXML
    private TableColumn<Part, Integer> PartID;

    @FXML
    private TableColumn<Part, String> PartName;

    @FXML
    private TableColumn<Part, Integer> PartInvLevel;

    @FXML
    private TableColumn<Part, Double> PartPrice;

    @FXML
    private Button AddPartButton;

    @FXML
    private Button ModifyPartButton;

    @FXML
    private Button ExitButton;

    @FXML
    private Button SearchProductsButton;

    @FXML
    private TextField SearchProductField;

    @FXML
    private TableColumn<Product, Integer> ProductID;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TableColumn<Product, Integer> ProductInvLevel;

    @FXML
    private TableColumn<Product, Double> ProductPrice;

    @FXML
    private Button AddProductButton;

    @FXML
    private Button ModifyProductButton;

    @FXML
    private Button DeleteProductButton;

    @FXML
    private TableView<Part> PartsTable;

    @FXML
    private TableView<Product> ProductTable;

    static boolean isAdded;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        if (!isAdded) {

            //Preset Parts data
            InHouse part1 = new InHouse(1, "Socket", 1.99, 4, 0, 10, 1);
            InHouse part2 = new InHouse(2, "Screw", 0.99, 500, 0, 2000, 2);
            InHouse part3 = new InHouse(3, "Nail", 0.99, 4, 0, 1000, 3);
            InHouse part4 = new InHouse(4, "Seat", 29.99, 18, 0, 8, 4);
            InHouse part5 = new InHouse(5, "Bolt", 1.99, 4, 0, 10, 5);

            Inventory.addPart(part1);
            Inventory.addPart(part2);
            Inventory.addPart(part3);
            Inventory.addPart(part4);
            Inventory.addPart(part5);

            //Preset Product data
            Product product1 = new Product(1, "Yamaha", 19999.00, 5, 1, 5);
            Product product2 = new Product(2, "Suzuki", 17999.00, 2, 1, 3);
            Product product3 = new Product(3, "Honda", 9999.00, 6, 1, 5);
            Product product4 = new Product(4, "Kawasaki", 16999.00, 5, 1, 7);
            Product product5 = new Product(5, "Harley", 59999.00, 9, 1, 9);

            Inventory.addProduct(product1);
            Inventory.addProduct(product2);
            Inventory.addProduct(product3);
            Inventory.addProduct(product4);
            Inventory.addProduct(product5);

            isAdded = true;

        }

        PartsTable.setItems(Inventory.getAllParts());
        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductTable.setItems(Inventory.getAllProducts());
        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    private void DeletePartButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected part, proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Part partToDelete = PartsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partToDelete);
            updatePartTableView();

        }

    }

    @FXML
    private void SearchPartsButtonHandler(ActionEvent event) {

        String searchString = SearchPartsField.getText();
        for (Part partToSearch : Inventory.getAllParts()) {

            if ((searchString.matches("(?i:.*" + partToSearch.getName() + ".*)")) || (searchString.equals(new Integer(partToSearch.getId()).toString()))) {

                PartsTable.getSelectionModel().select(partToSearch);
                //updatePartTableView();

            }

        }

    }

    @FXML
    private void SearchPartsFieldHandler(ActionEvent event) {

    }

    @FXML
    private void AddPartButtonHandler(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    private void ModifyPartButtonHandler(ActionEvent event) throws IOException {

        FXMLLoader modifyPartLoader = new FXMLLoader();
        modifyPartLoader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        modifyPartLoader.load();

        //Get another controller
        ModifyPartController MPController = modifyPartLoader.getController();

        //Connect to receive method in ModifyPartController
        MPController.receivePart(PartsTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = modifyPartLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    private void exitButtonHandler(ActionEvent event) {

        Platform.exit();

    }

    @FXML
    private void SearchProductsButtonHandler(ActionEvent event) {

        String searchString = SearchProductField.getText();
        for (Product productToSearch : Inventory.getAllProducts()) {

            if ((searchString.matches("(?i:.*" + productToSearch.getName() + ".*)")) || (searchString.equals(new Integer(productToSearch.getId()).toString()))) {

                ProductTable.getSelectionModel().select(productToSearch);
                //updateProductTableView();

            }

        }

    }

    @FXML
    private void SearchProductFieldHandler(ActionEvent event) {
    }

    @FXML
    private void AddProductButtonHandler(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    private void ModifyProductButtonHandler(ActionEvent event) throws IOException {

        FXMLLoader modifyProductLoader = new FXMLLoader();
        modifyProductLoader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        modifyProductLoader.load();

        //Get another controller
        ModifyProductController MPController = modifyProductLoader.getController();

        //Connect to receive method in ModifyProductController
        MPController.receiveProduct(ProductTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = modifyProductLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    private void DeleteProductButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected product, proceed?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Product productToDelete = ProductTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(productToDelete);
            updateProductTableView();

        }

    }

    public void updatePartTableView() {

        PartsTable.setItems(Inventory.getAllParts());
    }

    public void updateProductTableView() {

        ProductTable.setItems(Inventory.getAllProducts());
    }

    @FXML
    private void SearchPartsButtonHandler(KeyEvent event) {

    }

}
