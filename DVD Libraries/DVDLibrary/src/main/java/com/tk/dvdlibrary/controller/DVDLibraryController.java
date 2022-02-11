/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.dvdlibrary.controller;

import com.tk.dvdlibrary.dao.DVDLibraryDao;
import com.tk.dvdlibrary.dao.DVDLibraryDaoException;
import com.tk.dvdlibrary.dto.DVD;
import com.tk.dvdlibrary.ui.DVDLibraryView;
import com.tk.dvdlibrary.ui.UserIO;
import com.tk.dvdlibrary.ui.UserIOConsoleImpl;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public class DVDLibraryController {
    
    private DVDLibraryView view;
    private DVDLibraryDao dao;
    private UserIO io = new UserIOConsoleImpl();
    
    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }
    
    public void run() {
        
        boolean keepGoing = true;
        int menuSelection;
        
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        addDVD();
                        break;
                    case 2:
                        removeDVD();
                        break;
                    case 3:
                        editDVD();
                        break;
                    case 4:
                        listDVDs();
                        break;
                    case 5:
                        viewDVD();
                        break;
                    case 6:
                        menuSelection = view.printOtherMenuAndGetSelection();
                        switch(menuSelection) {
                            case 1:
                                findDVDsReleasedInNumOfYears();
                                break;
                            case 2:
                                sortByMPAARating();
                                break;
                            case 3:
                                sortByDirector();
                                break;
                            case 4:
                                sortByStudio();
                                break;
                            case 5:
                                findAverageAgeOfDVDs();
                                break;
                            case 6:
                                findNewestDVD();
                                break;
                            case 7:
                                findOldestDVD();
                                break;
                            case 8:
                                io.print("Find average number of notes associated with movies");
                                break;
                            case 9:
                                io.print("Back");
                                break;
                        }
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
            
        }catch(DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    
    private void addDVD() throws DVDLibraryDaoException {
        view.displayAddDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getTitle(), newDVD);
        view.displayAddSuccessBanner();
    }
    
    private void listDVDs() throws DVDLibraryDaoException {
        view.displayDisplayAllBanner();
        List<DVD> dvdList = dao.getAllDVDs();
        view.displayDVDList(dvdList);
    }
    
    private void viewDVD() throws DVDLibraryDaoException {
        view.displayDisplayDVDBanner();
        String title = view.getTitleChoice();
        DVD dvd = dao.getDVD(title);
        view.displayDVD(dvd);
    }
    
    private void removeDVD() throws DVDLibraryDaoException {
        view.displayRemoveDVDBanner();
        String title = view.getRemoveChoice();
        DVD removedDVD = dao.removeDVD(title);
        view.displayRemovalResult(removedDVD);
    }
    
    private void editDVD() throws DVDLibraryDaoException{
        
        try{
            
            view.displayEditBanner();
            
            DVD editedDVD = dao.getDVD(view.getEditTitle());
            
            String prevTitle = editedDVD.getTitle();
            
            view.editDVDFields(editedDVD);
            
            String newTitle = editedDVD.getTitle();
            
            dao.editDVD(newTitle, prevTitle);
            
            view.displayEditSuccessBanner();
            
        } catch (NullPointerException n){
            
            view.displayCantFindDVD();
            
        }
    }
    
    private void sortByMPAARating() throws DVDLibraryDaoException {
        List<DVD> dvdsByRating = dao.findDVDByRating(view.getSortRating());
        if(dvdsByRating.isEmpty()){
            view.noDVDsInList();
        } else {
            view.displayDVDList(dvdsByRating);
        }
    }
    
    private void sortByDirector() throws DVDLibraryDaoException {
        List<DVD> dvdsByDirector = dao.findDVDByDirector(view.getSortDirector());
        if(dvdsByDirector.isEmpty()){
            view.noDVDsInList();
        } else {
            view.displayDVDList(dvdsByDirector);
        }
    }
    
    private void sortByStudio() throws DVDLibraryDaoException {
        List<DVD> dvdsByStudio = dao.findDVDByStudio(view.getSortStudio());
        if(dvdsByStudio.isEmpty()){
            view.noDVDsInList();
        } else {
            view.displayDVDList(dvdsByStudio);
        }
    }
    
    private void findNewestDVD() throws DVDLibraryDaoException{
        DVD newestDVD = dao.findNewestDVD(dao.getAllDVDs());
        if (newestDVD != null){
            view.displayDVD(newestDVD);
        } else {
            view.noDVDsInList();
        }
    }
    
    private void findOldestDVD() throws DVDLibraryDaoException{
        DVD oldestDVD = dao.findOldestDVD(dao.getAllDVDs());
        if (oldestDVD != null){
            view.displayDVD(oldestDVD);
        } else {
            view.noDVDsInList();
        }
    }
    
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage(){
        view.displayExitBanner();
    }
    
    private void findAverageAgeOfDVDs()throws DVDLibraryDaoException{
        view.displayAverage(dao.findAverageAgeOfDVDs(dao.getAllDVDs()));
    }
    
    private void findDVDsReleasedInNumOfYears() throws DVDLibraryDaoException{
        List<DVD> dvdsInYears = dao.findDVDsReleasedInNumOfYears(view.getNumOfYears());
        if(dvdsInYears.isEmpty()){
            view.noDVDsInList();
        } else {
            view.displayDVDList(dvdsInYears);
        }
    }
    
}
