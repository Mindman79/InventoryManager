/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Tristan
 */
public class Inventory {

    //Create observable lists
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();



//--------------------------------------------------------------------------------------------

    public static void addPart(Part part) {


        allParts.add(part);



    }

//--------------------------------------------------------------------------------------------

    public static void addProduct(Product product) {

        allProducts.add(product);

    }

//--------------------------------------------------------------------------------------------

    public Part lookUpPart(int partID) {

        for(Part part : getAllParts()) {

            if(part.getId() == partID)
                return part;

        }

        return null;


    }

//--------------------------------------------------------------------------------------------

    public Product lookUpProduct(int productID) {

        for(Product product : getAllProducts()) {

            if(product.getId() == productID)
                return product;

        }

        return null;


    }

//--------------------------------------------------------------------------------------------

    public ObservableList<Part> lookUpPart(String partName) {

        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for(Part part : getAllParts()) {

            if(part.getName().contains(partName)) {
                foundParts.add(part);


            }


        }
        return foundParts;

    }



    //--------------------------------------------------------------------------------------------

    public ObservableList<Product> lookUpProduct(String productName) {

        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for(Product product : getAllProducts()) {

            if(product.getName().contains(productName)) {
                foundProducts.add(product);


            }


        }
        return foundProducts;

    }


//--------------------------------------------------------------------------------------------


    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }


//--------------------------------------------------------------------------------------------



    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }


//--------------------------------------------------------------------------------------------


    public static void deletePart(Part part) {

        allParts.remove(part);

    }

//--------------------------------------------------------------------------------------------

    public static void deleteProduct(Product product) {

        allProducts.remove(product);

    }





//--------------------------------------------------------------------------------------------



    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

//--------------------------------------------------------------------------------------------

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }



//--------------------------------------------------------------------------------------------





    public static int getProductsCount() {
        return allProducts.size() + 1;

    }

    public static int getPartsCount() {
        return allParts.size() + 1;

    }


}
