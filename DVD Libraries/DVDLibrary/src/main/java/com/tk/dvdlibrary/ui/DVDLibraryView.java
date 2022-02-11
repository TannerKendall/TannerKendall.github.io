/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.dvdlibrary.ui;

import com.tk.dvdlibrary.dto.DVD;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public class DVDLibraryView {
    
    private UserIO io;
    
    public DVDLibraryView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("MAIN MENU");
        io.print("1. Add a DVD to the library");
        io.print("2. Remove DVD from the library");
        io.print("3. Edit information for existing DVD in library");
        io.print("4. List DVDs in collection");
        io.print("5. Display information for existing DVD in library");
        io.print("6. Other..");
        io.print("7. Exit");
            
        return io.readInt("Please select from the above choices.", 1, 7);
    }
    
    public int printOtherMenuAndGetSelection(){
        io.print("");
        io.print("MORE OPTIONS");
        io.print("1. Find all movies released since a certain date");
        io.print("2. Find all movies with a given MPAA Rating");
        io.print("3. Find all movies by a given Director");
        io.print("4. Find all movies released by a given studio");
        io.print("5. Find average age of movies in collection");
        io.print("6. Find newest movie");
        io.print("7. Find oldest movie");
        io.print("8. Find average number of notes associated with movies");
        io.print("9. Back..");
        io.print("");
        
        return io.readInt("Please select from the above choices", 1, 9);
    }
    
    public DVD getNewDVDInfo() {
        String title = io.readString("Please enter the title of the DVD");
        String releaseDate = io.readString("Please enter the release date of the DVD");
        String mpaaRating = io.readString("Please enter the MPAA rating for the movie (G, PG, PG-13, R, or Unrated)");
        String director = io.readString("Please enter the name of the director");
        String studio = io.readString("Please enter the name of the studio");
        String userRating = io.readString("Enter a user rating, or your opinion on the movie");
        
        DVD currentDVD = new DVD(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaaRating(mpaaRating);
        currentDVD.setDirector(director);
        currentDVD.setStudio(studio);
        currentDVD.setUserRating(userRating);
        return currentDVD;
    }
    
    public void displayAddDVDBanner(){
        io.print("");
        io.print("=== ADD DVD ===");
        io.print("");
    }
    
    public void displayAddSuccessBanner() {
        io.readString("DVD successfully added. Please hit enter to continue");
    }
    
    public void displayDVDList(List<DVD> dvdList) {
        
        for (DVD currentDVD : dvdList) {
            String DVDInfo = String.format("\n Title: %s \n ReleaseDate: %s \n MPAA Rating: %s \n"
                    + " Director: %s \n Studio: %s \n User Notes: %s \n",
                    currentDVD.getTitle(),
                    currentDVD.getReleaseDate(),
                    currentDVD.getMpaaRating(),
                    currentDVD.getDirector(),
                    currentDVD.getStudio(),
                    currentDVD.getUserRating());
            io.print(DVDInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner(){
        io.print("");
        io.print("=== DISPLAY ALL DVDs ===");
    }
    
    public void displayDisplayDVDBanner(){
        io.print("");
        io.print("=== Display DVD ===");
        io.print("");
    }
    
    public String getTitleChoice(){
        return io.readString("Please enter the title of the DVD you wish to view.");
    }
    
    public String getRemoveChoice(){
        return io.readString("Please enter the title of the DVD you wish to remove from the library.");
    }
    
    public void displayDVD(DVD dvd){
        if (dvd != null) {
            io.print("");
            io.print("Title: " + dvd.getTitle());
            io.print("Release Date: " + dvd.getReleaseDate());
            io.print("MPAA Rating: " + dvd.getMpaaRating());
            io.print("Director: " + dvd.getDirector());
            io.print("Studio: " + dvd.getStudio());
            io.print("User Rating: " + dvd.getUserRating());
            io.print("");
        } else {
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayRemoveDVDBanner(){
        io.print("=== REMOVE DVD ===");
    }
    
    public void displayRemovalResult(DVD dvdRecord) {
        if(dvdRecord != null) {
            io.print("DVD successfully removed");
        }else{
            io.print("No such DVD");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayEditBanner(){
        io.print("");
        io.print("=== EDIT DVD ===");
        io.print("");
    }
    
    public int printEditMenuAndGetSelection(){
        io.print("");
        io.print("What field would you like to edit for the movie:");
        io.print("1. Title");
        io.print("2. Release Date");
        io.print("3. MPAA Rating");
        io.print("4. Director");
        io.print("5. Studio");
        io.print("6. User Rating");
        io.print("7. *Back to main menu*");
        io.print("");
        return io.readInt("Please select from the above choices.", 1, 7);
    }
    
    public DVD editDVDFields(DVD editedDVD){
        int editSelection = printEditMenuAndGetSelection();

        switch(editSelection) {
            case 1:
                String newTitle = getEditString();
                editedDVD.setTitle(newTitle);
                break;
            case 2:
                editedDVD.setReleaseDate(getEditString());
                break;
            case 3:
                editedDVD.setMpaaRating(getEditString());
                break;
            case 4:
                editedDVD.setDirector(getEditString());
                break;
            case 5:
                editedDVD.setStudio(getEditString());
                break;
            case 6:
                editedDVD.setUserRating(getEditString());
                break;
            case 7:
                break;
        }
        return editedDVD;
    }
    
    public String getEditTitle(){
        return io.readString("Please enter the title of the DVD you wish to edit.");
    }
    
    public String getEditString(){
        return io.readString("Please enter the new information for the selected option above.");
    }
    
    public void displayEditSuccessBanner(){
        io.print("");
        io.print("=== EDIT SUCCESS ===");
        io.print("");
    }
    
    public void displayExitBanner(){
        io.print("Good Bye!!!");
    }
    
    public void displayUnknownCommandBanner(){
        io.print("Unknown Command!!");
    }
    
    public void displayErrorMessage(String errorMsg){
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayCantFindDVD(){
        io.print("This DVD is not in the library currently. Please try again with a DVD from the library, or try again with the correct spelling.");
        io.print("");
    }
    
    public String getSortRating(){
        return io.readString("Please enter rating you wish to sort by:");
    }
    
    public String getSortDirector(){
        return io.readString("Please enter the director you wish to sort by:");
    }
    
    public String getSortStudio(){
        return io.readString("Please enter the studio you wish to sort by:");
    }
    
    public void noDVDsInList(){
        io.print("");
        io.print("There are no DVDs in the list..");
        io.print("");
    }
    
    public void displayAverage(BigDecimal average){
        io.print("");
        io.print("The average age of all DVDs is: " + average + " years old");
        io.print("");
    }
    
    public int getNumOfYears(){
        return io.readInt("How many years back would you like to see the movies released in?");
    }
    
}
