/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author Tanner Kendall
 */
public class Snack {

    private String name;
    private BigDecimal cost;
    private int inventoryOfItem;
    private int itemId;
    
    public Snack(String name){
        this.name = name;
    }
    
    public Snack(String name, BigDecimal cost, int numLeft, int selectionNum) {
        this.name = name;
        this.cost = cost;
        this.inventoryOfItem = numLeft;
        this.itemId = selectionNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(String costString) {
        BigDecimal costBD = new BigDecimal(costString);
        this.cost = costBD.setScale(2, RoundingMode.HALF_UP);
    }

    public int getInventoryOfItem() {
        return inventoryOfItem;
    }

    public void setInventoryOfItem(int inventoryOfItem) {
        this.inventoryOfItem = inventoryOfItem;
    }
    
    public void purchaseSnack(){
        this.inventoryOfItem = inventoryOfItem - 1;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.cost);
        hash = 47 * hash + this.inventoryOfItem;
        hash = 47 * hash + this.itemId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Snack other = (Snack) obj;
        if (this.inventoryOfItem != other.inventoryOfItem) {
            return false;
        }
        if (this.itemId != other.itemId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        return true;
    }

    
    
}
