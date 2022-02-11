/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.ui;

import com.tk.vendingmachine.dto.Snack;
import com.tk.vendingmachine.service.InsufficientFundsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io){
        this.io = io;
    }
    
    public void displayMachine(){
        System.out.println(" _______________________________________________________\n" +
                            "/  ___________________________________________________  \\\n" +
                            "| /                                                   \\ |\n" +
                            "| |   ----------        ----------        ----------  | |\n" +
                            "| |  |  Doritos |      |  Fritos  |      |  Lays's  | | |\n" +
                            "| |  |          |      |          |      |          | | |\n" +
                            "| |  |   $1.75  |      |   $1.50  |      |   $1.25  | | |\n" +
                            "| |  |    [1]   |      |    [2]   |      |    [3]   | | |\n" +
                            "| |   ----------        ----------        ----------  | |\n" +
                            "| |   ----------        ----------        ----------  | |\n" +
                            "| |  | Skittles |      | Snickers |      |   M&Ms   | | |\n" +
                            "| |  |          |      |          |      |          | | |\n" +
                            "| |  |   $2.25  |      |   $2.75  |      |   $2.50  | | |\n" +
                            "| |  |    [4]   |      |    [5]   |      |    [6]   | | |\n" +
                            "| |   ----------        ----------        ----------  | |\n" +
                            "| |   ----------        ----------        ----------  | |\n" +
                            "| |  |  Granola |      |   Jerky  |      |   Fruit  | | |\n" +
                            "| |  |          |      |          |      |   Snack  | | |\n" +
                            "| |  |   $3.75  |      |   $5.50  |      |   $4.25  | | |\n" +
                            "| |  |    [7]   |      |    [8]   |      |    [9]   | | |\n" +
                            "| |   ----------        ----------        ----------  | |\n" +
                            "|  \\_________________________________________________/  |\n" +
                            "|   _________                                           |\n" +
                            "|  /[1][2][3]\\                                          |\n" +
                            "|  |[4][5][6]|                            CASH  COINS   |\n" +
                            "|  |[7][8][9]|                           ======  ===    |\n" +
                            "|  \\_________/                                          |\n" +
                            "|                                                       |\n" +
                            "\\_______________________________________________________/");
    }
    
    public int printMenuAndGetSelection(){
        io.print("");
        io.print("===MAIN MENU===");
        io.print("1. Purchase Item");
        io.print("2. List Items");
        io.print("3. Exit");
        io.print("");
        return io.readInt("Please select from the above..", 1, 3);
    }
    
    public void displayErrorMessage(String errorMsg){
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayUnknownCommandMessage() {
        io.print("===UNKNOWN COMMAND===");
    }
    
    public void displaySnackList(List<Snack> snackList) {
        
        for (Snack currentSnack : snackList) {
            String snackInfo = String.format("\n Name: %s | Cost: %s | Quantity: %s |"
                    + " Selection Number: %s\n",
                    currentSnack.getName(),
                    currentSnack.getCost(),
                    currentSnack.getInventoryOfItem(),
                    currentSnack.getItemId());
            io.print(snackInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner(){
        io.print("");
        io.print("=== DISPLAY ALL SNACKS ===");
    }
    
    public int getPurchaseSelection(){
        return io.readInt("Please select which snack to buy", 1, 9);
    }
    
    //public void displayThankYouBanner(){
    //    io.print("===THANK YOU FOR YOUR PURCHASE===");
    //    io.print("");
    //}
    
    //public void proofOfPurchase(Snack purchasedSnack){
    //    io.print("");
    //    io.print("You bought " + purchasedSnack.getName() + "!");
    //    io.print("");
    //}
    
    public BigDecimal displayDepositFundsRequest() throws NumberFormatException {
        
        String deposit = io.readString("Please enter how much money you would like to deposit.. (min: 0.00)");
        
        try{
            BigDecimal depositBD = new BigDecimal(deposit);
        return depositBD;
        }catch(NumberFormatException e){
            displayWrongCurrency(); 
        }
        
        return new BigDecimal("0.00");
    }
    
    //public String displayDepositString(){
    //    String deposit = io.readString("Please enter how much money you would like to deposit.. (min: 0.00, max: 10.00)");
    //    return deposit;
    //}
    
    public void giveChange(String change) {
        io.print("");
        io.print(change);
        io.print("");
    }
    
    public void displayInsufficientFunds(){
        io.print("");
        io.print("Please deposit more funds..");
        io.print("");
    }
    
    public void displayWrongCurrency(){
        io.print("");
        io.print("Your initial deposit was not taken by the machine. Please use recognizable currency.");
    }
    
}
