 /*
  * To change this license header, choose License Headers in Project Properties.
  * To change this template file, choose Tools | Templates
  * and open the template in the editor.
  */
 package model;

 /**
  *
  * @author Tristan
  */
 public class Outsourced extends Part {



     private String companyname;

     public Outsourced(int id, String name, double price, int stock, int min, int max, String companyname) {
         super(id, name, price, stock, min, max);
         this.companyname = companyname;
     }


     public String getCompanyname() {
         return companyname;
     }

     public void setCompanyname(String companyname) {
         this.companyname = companyname;
     }








 }
