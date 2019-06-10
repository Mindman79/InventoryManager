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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Tristan
 */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    Part receivedPart;

    @FXML
    private Label addPart;
    @FXML
    private RadioButton InHouseCheckbox;
    @FXML
    private ToggleGroup Grp1;
    @FXML
    private RadioButton OutsourcedCheckbox;
    @FXML
    private Text Max;
    @FXML
    private Text Inv;
    @FXML
    private Text ID;
    @FXML
    private Text Name;
    @FXML
    private Text PriceCost;
    @FXML
    private TextField PartIDField;
    @FXML
    private TextField PartNameField;
    @FXML
    private TextField PartInvField;
    @FXML
    private TextField PartPriceField;
    @FXML
    private TextField PartMaxOnHand;
    @FXML
    private Text Min;
    @FXML
    private TextField PartMinOnHand;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Text DynamicPartLabel;
    @FXML
    private TextField DynamicPartField;

    private boolean isInHousePart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void InHouseCheckboxHandler(ActionEvent event) {

        isInHousePart = true;
        DynamicPartLabel.setText("Machine ID");

    }

    @FXML
    private void OutsourcedCheckboxHandler(ActionEvent event) {

        isInHousePart = false;
        DynamicPartLabel.setText("Company Name");

    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {

        int id = Integer.parseInt(PartIDField.getText());
        String name = PartNameField.getText();
        double price = Double.parseDouble(PartPriceField.getText());
        int stock = Integer.parseInt(PartInvField.getText());
        int min = Integer.parseInt(PartMinOnHand.getText());
        int max = Integer.parseInt(PartMaxOnHand.getText());

        if (min > max) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum part on hand quantity cannot be above maximum part on hand quantity!");
            Optional<ButtonType> result = alert.showAndWait();
            System.out.println("Minimum part on hand quantity cannot be above maximum part on hand quantity!");

        } else {

            if (isInHousePart == true) {

                int machineid = Integer.parseInt(DynamicPartField.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineid));
                Inventory.deletePart(receivedPart);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } else {

                String companyname = DynamicPartField.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyname));
                Inventory.deletePart(receivedPart);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }

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

    //Receive part from something
    public void receivePart(Part partToReceive) {

        this.receivedPart = partToReceive;
        PartIDField.setText(String.valueOf(partToReceive.getId()));
        PartNameField.setText(partToReceive.getName());
        PartInvField.setText(String.valueOf(partToReceive.getStock()));
        PartPriceField.setText(String.valueOf(partToReceive.getPrice()));
        PartMaxOnHand.setText(String.valueOf(partToReceive.getMax()));
        PartMinOnHand.setText(String.valueOf(partToReceive.getMin()));

        if (partToReceive instanceof InHouse) {

            DynamicPartField.setText(String.valueOf(((InHouse) partToReceive).getMachineid()));
            DynamicPartLabel.setText("Machine ID");
            InHouseCheckbox.setSelected(true);

        }

        if (partToReceive instanceof Outsourced) {

            DynamicPartField.setText(((Outsourced) partToReceive).getCompanyname());
            DynamicPartLabel.setText("Company Name");
            OutsourcedCheckbox.setSelected(true);

        }

    }

}
