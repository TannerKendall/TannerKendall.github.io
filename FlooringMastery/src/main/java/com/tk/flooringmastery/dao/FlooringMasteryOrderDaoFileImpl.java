/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao{

    public static final String DELIMITER = ",";
    public static final String HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddYYYY");
    public static final String ORDER_DIRECTORY = "Orders/";
    private Map<Integer, Order> ordersMap = new HashMap<>();
    private List<Order> orders;

    
    @Override
    public List<Order> getAllOrdersByDate(LocalDate ordersDate) throws FlooringMasteryPersistanceException {
        try{
            return loadOrderFile(ordersDate);
        }catch(FlooringMasteryPersistanceException e){
            throw new FlooringMasteryPersistanceException("ERROR: CANNOT LOAD FILE DATA", e);
        }
    }

    @Override
    public Order addOrder(int orderNum, Order order) throws FlooringMasteryPersistanceException {
        try{
            ordersMap.clear();
            loadOrderFile(order.getOrderDate());
            Order newOrder = ordersMap.put(orderNum, order);
            writeOrderFile(ordersMap.values().stream().collect(toList()), order.getOrderDate());
            return newOrder;
        }catch(FlooringMasteryPersistanceException e){
            throw new FlooringMasteryPersistanceException("ERROR: COULD NOT ADD ORDER", e);
        }
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException {
        try{
            ordersMap.clear();
            loadOrderFile(orderDate);
            Order removedOrder = ordersMap.remove(orderNumber);
            writeOrderFile(ordersMap.values().stream().collect(toList()), orderDate);
            return removedOrder;
        }catch(FlooringMasteryPersistanceException e){
            throw new FlooringMasteryPersistanceException("ERROR: COULD NOT REMOVE ORDER", e);
        }
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order updatedOrder) throws FlooringMasteryPersistanceException {
        try{
            ordersMap.clear();
            loadOrderFile(orderDate);
            
            Order editedOrder = getOrder(orderDate, orderNumber);
            
            if(editedOrder == null){
                throw new FlooringMasteryPersistanceException("COULD NOT LOAD ORDER");
            } else{
                removeOrder(editedOrder.getOrderDate(), editedOrder.getOrderNumber());
                addOrder(updatedOrder.getOrderNumber(), updatedOrder);
                writeOrderFile(ordersMap.values().stream().collect(toList()), orderDate);
                return updatedOrder;
            }
        
        }catch(FlooringMasteryPersistanceException e){
           return null; 
        }
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistanceException {
        try{
            loadOrderFile(orderDate);
            return ordersMap.get(orderNum);
        }catch(FlooringMasteryPersistanceException e){
            throw new FlooringMasteryPersistanceException("ERROR: CANNOT LOAD FILE DATA", e);
        }
    }

    private Order unmarshallOrder(String orderAsText){
        
        // orderAsText is expecitng a line read in from our file.
        // Then we split that line on the DELIMITER, in this case ",",
        // leaving an array of strings stored in taxTokens.
        // pattern: OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,
        // LaborCostPerSquareFoot, MaterialCost,LaborCost,Tax,Total
        
        String[] orderTokens = orderAsText.split(DELIMITER);
        
        int orderNumber = Integer.parseInt(orderTokens[0]);
        
        Order orderFromFile = new Order(orderNumber);
        
        orderFromFile.setCustomerName(orderTokens[1]);
        
        orderFromFile.setState(orderTokens[2]);
        
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setProductType(orderTokens[4]);
        
        orderFromFile.setArea(new BigDecimal(orderTokens[5]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setTax(new BigDecimal(orderTokens[10]).setScale(2, RoundingMode.HALF_UP));
        
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]).setScale(2, RoundingMode.HALF_UP));
        
        return orderFromFile;
    }
    
    private List<Order> loadOrderFile(LocalDate ordersDate) throws FlooringMasteryPersistanceException{
        //clear map
        ordersMap.clear();
        Scanner sc;
        //file name will be ordersDate
        String fileName = ORDER_DIRECTORY + "Orders_" + ordersDate.format(dtf) + ".txt";
        //new file, directory name + fileName
        //File file = new File(String.format(ORDER_DIRECTORY + "Orders_%s.txt", fileName));
        File file = new File(fileName);
        //create List to be returned
        List<Order> orderList = new ArrayList<>();
        //check if file exists
        if(file.isFile()){
            try{
                //init scanner
                sc = new Scanner(new BufferedReader(new FileReader(file)));
            } catch(FileNotFoundException e){
                throw new FlooringMasteryPersistanceException("ERROR: CANNOT READ FILE" + fileName, e);
            }
            sc.nextLine();
            //loop through each line and unmarshall orders
            while(sc.hasNextLine()){
                //sc.skip(HEADER);
                Order currentOrder = unmarshallOrder(sc.nextLine());
                currentOrder.setOrderDate(ordersDate);
                orderList.add(currentOrder);
                ordersMap.put(currentOrder.getOrderNumber(), currentOrder);
            }
            sc.close();
        }
        return orderList;
    }
    
    private String marshallOrder(Order order){
        
        //get out each property in correct order and concatenate with DELIMITER as a spacer
        String orderAsText = order.getOrderNumber() + DELIMITER;
        
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getMaterialCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getLaborCost().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getTax().setScale(2, RoundingMode.HALF_UP) + DELIMITER;
        orderAsText += order.getTotal().setScale(2, RoundingMode.HALF_UP);
        
        // skip delimiter on last property
        
        return orderAsText;
    }
    
    private void writeOrderFile(List<Order> orderList, LocalDate ordersDate) throws FlooringMasteryPersistanceException{
        
        String date = ordersDate.format(dtf);
        //create file variable with name, to check if it exists
        File file = new File(ORDER_DIRECTORY + "/Orders_" + date + ".txt");
        //init printwriter
        PrintWriter out;
        
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                throw new FlooringMasteryPersistanceException("ERROR: CANNOT CREATE NEW FILE", e);
            }
        }
        
        try{
            out = new PrintWriter(new FileWriter(file, false));
        }catch (IOException e){
            throw new FlooringMasteryPersistanceException("ERROR: CANNOT WRITE TO FILE", e);
        }
        
        //print header for file
        out.println(HEADER);
        
            //iterate through list and write to file
        for(Order currentOrder: orderList){
            out.println(marshallOrder(currentOrder));
            out.flush();
        }
        //close writer
        out.close();
        
    }
    
}
