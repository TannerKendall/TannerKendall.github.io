/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dao;

import com.tk.vendingmachine.dto.Snack;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    
    public static String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";
    
    private Map<Integer, Snack> snacks = new HashMap<>();
    
    public VendingMachineDaoFileImpl(){
        INVENTORY_FILE = "inventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String inventoryTextFile){
        INVENTORY_FILE = inventoryTextFile;
    }
    
    @Override
    public List<Snack> listSnacks() throws VendingMachineDaoException, VendingMachinePersistanceException {
        loadInventory();
        return new ArrayList<Snack>(snacks.values());
    }

    @Override
    public void purchaseSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException {
        
        loadInventory();
        Snack purchasedSnack = snacks.get(selectionNum);
        purchasedSnack.purchaseSnack();
        writeInventory();
        
    }
    
    @Override
    public Snack getSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException {
        loadInventory();
        return snacks.get(selectionNum);
    }
    
    private Snack unmarshallSnack(String snackAsText) {
        
        // snackAsText is expecitng a line read in from our file.
        // Then we split that line on the DELIMITER, in this case "::",
        // leaving an array of strings stored in snackTokens.
        // pattern: Name::cost::numLeft::selectionNum
        
        String[] snackTokens = snackAsText.split(DELIMITER);
        
        // title at 0 index
        String name = snackTokens[0];
        
        // then use title to create new Snack object to satisfy requirements of DVD constructor
        Snack snackFromFile = new Snack(name);
        
        // fill out remaning tokens
        snackFromFile.setCost(snackTokens[1]);
        snackFromFile.setInventoryOfItem(Integer.parseInt(snackTokens[2]));
        snackFromFile.setItemId(Integer.parseInt(snackTokens[3]));
        
        // return Snack object
        return snackFromFile;
        
    }
    
    private void loadInventory() throws VendingMachinePersistanceException {
        
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistanceException("-_- Could not load inventory data into memory.", e);
        }
        
        // currentLine will hold most recent line read from file
        String currentLine;
        
        // currentSnack holds most recent Snack unmarshalled
        Snack currentSnack;
        
        // go through INVENTORY_FILE line by line, decoding each into a Snack object by calling
        //unmarshallSnack method.
        // while we still have more lines in the file
        while(scanner.hasNextLine()){
            // get next line
            currentLine = scanner.nextLine();
            // unmarshall into a Snack
            currentSnack = unmarshallSnack(currentLine);
            // use title as map key for our Snack object
            snacks.put(currentSnack.getItemId(), currentSnack);
        }
        scanner.close();
    }
    
    private String marshallSnack(Snack snack){
        
        //get out each property in correct order and concatenate with DELIMITER as a spacer
        String snackAsText = snack.getName() + DELIMITER;
        
        snackAsText += snack.getCost() + DELIMITER;
        snackAsText += snack.getInventoryOfItem() + DELIMITER;
        snackAsText += snack.getItemId();
        
        // skip delimiter on last property
        
        return snackAsText;
    }
    
    /**
     * Writes all Snacks in the roster out to INVENTORY_FILE. see loadInventory for file format.
     * 
     * @throws VendingMachine DaoException if an error occurs writing file
     */
    private void writeInventory() throws VendingMachineDaoException, VendingMachinePersistanceException {
        // not handling IOException but translating to an app specific exception
        // and simple throwing it to the code that called us.
        // It is the responsibility of the calling code to handle an errors that occur.
        
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistanceException("Could not save Snack data.", e);
        }
        
        // Write out Snack objects to the INVENTORY_FILE.
        // reuse getAllSnacks method
        
        String snackAsText;
        try{
            List<Snack> snackList = this.listSnacks();

            for(Snack currentSnack : snackList){
                //turn Snack into String
                snackAsText = marshallSnack(currentSnack);
                // write the Snack object to file
                out.println(snackAsText);
                out.flush();
            }
        }catch (VendingMachineDaoException e){
           throw new VendingMachineDaoException("Could not load list") ;
        }
        
        out.close();
    }

    @Override
    public void addSnack(int selectionNum, Snack snack) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
