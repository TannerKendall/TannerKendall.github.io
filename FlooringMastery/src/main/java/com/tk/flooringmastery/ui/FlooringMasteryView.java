/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.ui;

import com.tk.flooringmastery.dto.Order;
import com.tk.flooringmastery.dto.Product;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryView {
    private UserIO io;
    
    public FlooringMasteryView(UserIO io){
        this.io = io;
    }
    
    public void displayTsgBanner() {
        io.print("---------------------------------------------------------------------------\n" +
                "|  _________  ______   _______      ______   ______   ______    ______    |\n" +
                "| /________/\\/_____/\\ /______/\\    /_____/\\ /_____/\\ /_____/\\  /_____/\\   |\n" +
                "| \\__.::.__\\/\\::::_\\/_\\::::__\\/__  \\:::__\\/ \\:::_ \\ \\\\:::_ \\ \\ \\:::_ \\ \\  |\n" +
                "|    \\::\\ \\   \\:\\/___/\\\\:\\ /____/\\  \\:\\ \\  __\\:\\ \\ \\ \\\\:(_) ) )_\\:(_) \\ \\ |\n" +
                "|     \\::\\ \\   \\_::._\\:\\\\:\\\\_  _\\/   \\:\\ \\/_/\\\\:\\ \\ \\ \\\\: __ `\\ \\\\: ___\\/ |\n" +
                "|      \\::\\ \\    /____\\:\\\\:\\_\\ \\ \\    \\:\\_\\ \\ \\\\:\\_\\ \\ \\\\ \\ `\\ \\ \\\\ \\ \\   |\n" +
                "|       \\__\\/    \\_____\\/ \\_____\\/     \\_____\\/ \\_____\\/ \\_\\/ \\_\\/ \\_\\/   |\n" +
                "|                                                                         |\n" +
                "---------------------------------------------------------------------------");
    }
    
    public int printMenuAndGetSelection(){
        
        io.print("");
        io.print("**************************************");
        io.print(" * << Flooring Program >>");
        io.print(" * 1. Display Orders");
        io.print(" * 2. Add an Order");
        io.print(" * 3. Edit an Order");
        io.print(" * 4. Remove an Order");
        io.print(" * 5. Export All Data");
        io.print(" * 6. Exit");
        io.print("**************************************");
        io.print("");
        
        return io.readInt("Please select from the above choices", 1, 6);
         
    }
    
    public void displayBanner(String action){
        io.print("");
        io.print("===== " + action + " =====");
        io.print("");
    }
   
    /**
     * displays if user selects NO upon confirmation
     * @param no 
     */
    public void displayIfNo(String no){
        io.print("");
        io.print(no);
        io.print("");
    }
    
    public void displayNoOrdersList(){
        io.print("");
        io.print("There are no orders set for that date.");
    }
    
    public void displayProducts(ArrayList<Product> productList){
        for (Product currentProduct : productList) {
            int index = productList.indexOf(currentProduct) + 1;
            String productInfo = String.format("\n" + index +". Type: %s | CostPerSqFt: %s | LaborCostPerSqFt: %s |",
                    currentProduct.getProductType(),
                    currentProduct.getCostPerSquareFoot(),
                    currentProduct.getLaborCostPerSquareFoot());
            io.print("");
            io.print(productInfo);
            io.print("");
        }
    }
    
    public int displayProductListAndGetSelection(ArrayList<Product> productList){
        displayProducts(productList);
        
        return io.readInt("Please select a product by number..", 1, productList.size()) - 1;
    }
    
    public int displayProductListAndEdit(ArrayList<Product> productList, Order order){
        
        displayProducts(productList);
        
        int userSelection;
        String userInput;
        
        do{
            userInput = io.readString("Select a new product. Current Product: " + order.getProductType() + ".");
            
            try{
                userSelection = Integer.parseInt(userInput);
                
                if(userSelection > 0 && userSelection <= productList.size()){
                    return userSelection;
                }
            }catch(NumberFormatException e){
                
            }
            
        }while(!userInput.equals(""));
        
        return 0;
    }
    
    public void displayTaxes(HashMap<String, BigDecimal> taxRates){
        
        io.print("=========================");
        io.print("|STATES|        |TAXRATES|");
        taxRates.forEach((k, v) -> io.print("| " + k + "        " + v + "% |"));
        io.print("=========================");
        
    }
    
    public String displayTaxesAndGetSelection(HashMap<String, BigDecimal> taxRates){
        displayTaxes(taxRates);
        String userSelection;
        
        do{
            userSelection = io.readString("Please select a state.. (eg: CA, KY..)(Case sensitive)");
        }while(!taxRates.containsKey(userSelection));
        return userSelection;
    }

    public String displayTaxesAndEdit(HashMap<String, BigDecimal> taxRates, String state){
        displayTaxes(taxRates);
        
        String userSelection = "";
        
        do{
            userSelection = io.readString("Please Select a state. Current State: " + state + ".");
        }while(!(taxRates.containsKey(userSelection) || userSelection.equals("")));
        return userSelection;
    }
    
    public void displayOrder(Order order){
        io.print("");
        io.print("**************************************");
        io.print("* Order Number: " + order.getOrderNumber());
        io.print("* Customer Name: " + order.getCustomerName());
        io.print("* State: " + order.getState());
        io.print("* Tax Rate: " + order.getTaxRate());
        io.print("* Product Type: " + order.getProductType());
        io.print("* Area: " + order.getArea());
        io.print("* Cost Per SqFt: " + order.getCostPerSquareFoot());
        io.print("* Labor Cost Per SqFt: " + order.getLaborCostPerSquareFoot());
        io.print("* Material Cost: " + order.getMaterialCost());
        io.print("* Labor Cost: " + order.getLaborCost());
        io.print("* Tax: " + order.getTax());
        io.print("* Total: " + order.getTotal());
        io.print("**************************************");
        io.print("");
    }
    
    public void displayOrderList(List<Order> orderList){
        if(!orderList.isEmpty()){
            for(Order currentOrder: orderList){
                displayOrder(currentOrder);
            }
        } else{
            io.print("NO ORDERS FOR REQUESTED DATE.");
        }
    }
    
    public Order addOrder(ArrayList<Product> productList, HashMap<String, BigDecimal> taxRates){
        boolean valid = false;
        BigDecimal area = new BigDecimal("0.00");
        LocalDate date = getNewOrderDate();
        Order order = new Order();
        order.setOrderDate(date);
        
        String customerName = null;
        while(valid == false){
            customerName = io.readString("Please enter customer name: ");
            if(customerName.equals("")){
                io.print("Please enter a valid name. ");
                io.print("");
            } else{
                valid = true;
            }
        }
        order.setCustomerName(customerName);
        
        String stateAbbreviation = displayTaxesAndGetSelection(taxRates);
        BigDecimal taxRate = taxRates.get(stateAbbreviation);
        int productSelection = displayProductListAndGetSelection(productList);
        Product product = productList.get(productSelection);
        
        order.setState(stateAbbreviation);
        order.setTaxRate(taxRate);
        order.setProductType(product.getProductType());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        
        valid = false;
        
        while(valid == false){
            area =io.readBigDecimal("Please enter Area in square feet");
            
            if(area.compareTo(new BigDecimal("0.00")) == -1 || area.compareTo(new BigDecimal("0.00")) == 0){
                io.print("");
                io.print("Cannot have negative area. Please use a valid number greater than 0.");
                io.print("");
            }else{
                valid = true;
            }
        }
        order.setArea(area);
        
        order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
        order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
        order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
        order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));
        
        io.print("");
        return order;
    }
    
    public Order getConfirmationToAdd(Order order){
        while(true){
           String userSelection = io.readString("Would you like to submit this order? (y/n)(Order info will not be saved if no): ");
           if(userSelection.equalsIgnoreCase("y")){
               io.print("");
               io.print("ADDING ORDER.");
               io.print("");
               return order;
           }else if(userSelection.equalsIgnoreCase("n")){
               return null;
           }
        }
    }
    
    public Order getConfirmationToRemove(Order order){
        while(true){
           String userSelection = io.readString("Would you like to remove this order? (y/n): ");
           if(userSelection.equalsIgnoreCase("y")){
               io.print("");
               io.print("REMOVING ORDER.");
               io.print("");
               return order;
           }else if(userSelection.equalsIgnoreCase("n")){
               return null;
           }
        }
    }
    
    public int getOrderToEdit(List<Order> ordersForDate, int maxOrderNumber){
        
        int userSelection;
        
        List<Integer> orderNumbers;
        
        do{
            
            userSelection = io.readInt("Please select which order to edit. (Order Number)", 1, maxOrderNumber);
        
            orderNumbers = ordersForDate.stream().map(Order::getOrderNumber).collect(Collectors.toList());
        }while(!orderNumbers.contains(userSelection));
        
        return userSelection;
    }
    
    public Order editOrder(Order order, ArrayList<Product> productList, HashMap<String, BigDecimal> taxRates){
        
        String updateConfirm = null;
        BigDecimal area = order.getArea();
        boolean yesOrNo = true;
        
        io.print("Press enter to leave field as is..");
        
        String customerName = io.readString("Please enter customer name. Current Name: " + order.getCustomerName() + ".");
        
        String state = displayTaxesAndEdit(taxRates, order.getState());
        
        int productSelection = displayProductListAndEdit(productList, order);
        
        try{
            area = new BigDecimal(io.readString("Please enter Area. Current Area: " + order.getArea() + "sqft."));
        }catch(NumberFormatException e){
            //
        }
        
        while(yesOrNo){
            updateConfirm = io.readString("Would you like to confirm the changes? (y/n)");
            if(updateConfirm.equalsIgnoreCase("y")){
                break;
            } else if(updateConfirm.equalsIgnoreCase("n")){
                break;
            } else{
                //do nothing
            }
        }
        
        if(!customerName.equalsIgnoreCase("")){
            order.setCustomerName(customerName);
        }
        
        if(!state.equalsIgnoreCase("")){
            order.setState(state);
            order.setTaxRate(taxRates.get(state));
        }
        
        if(productSelection != 0){
            Product product = productList.get(productSelection);
            order.setProductType(product.getProductType());
            order.setCostPerSquareFoot(product.getCostPerSquareFoot());
            order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        }
        
        if(area.toString().length() != 0){
            order.setArea(area);
        }
        
        if(updateConfirm.equalsIgnoreCase("y")){
            order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
            order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
            order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
            order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));

        }else if(updateConfirm.equalsIgnoreCase("n")){
            //return originalOrder;
            return null;
        }
        
        return order;
        
    }
    
    public int getOrderNumber(){
        int orderNumber = io.readInt("Please enter order number");
        if(orderNumber == 0){
            orderNumber = io.readInt("Please enter a valid order number to proceed");
        }
        return orderNumber;
    }
    
    public LocalDate getDate(){
        LocalDate date = io.readLocalDate("Please enter the date of the Order/Orders.. (MM/dd/yyyy)");
        return date;
    }
    
    public LocalDate getNewOrderDate(){
        LocalDate now = LocalDate.now();
        LocalDate newDate;
        do{
            newDate = getDate();
            if(newDate.isBefore(now)){
                io.print("");
                io.print("Please enter a future date.");
                io.print("");
            }
        }while(newDate.isBefore(now));
        return newDate;
    }
    
    public void featureNotAdded(){
        io.print("");
        io.print("FEATURE NOT ADDED YET");
        io.print("");
    }
    
}
