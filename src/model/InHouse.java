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
public class InHouse extends Part {

    private int machineid;


    public InHouse(int id, String name, double price, int stock, int min, int max, int machineid) {
        super(id, name, price, stock, min, max);
        this.machineid = machineid;
    }

    public int getMachineid() {
        return machineid;
    }

    public void setMachineid(int machineid) {
        this.machineid = machineid;
    }




}
