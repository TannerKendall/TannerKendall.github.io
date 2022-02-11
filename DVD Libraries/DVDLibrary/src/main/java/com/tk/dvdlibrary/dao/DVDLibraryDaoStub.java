/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.dvdlibrary.dao;

import com.tk.dvdlibrary.dto.DVD;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tanner Kendall
 */
public class DVDLibraryDaoStub implements DVDLibraryDao{

    private Map<String, DVD> dvds = new HashMap<>();
    
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException {
        DVD newDVD = dvds.put(title, dvd);
        return newDVD;
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException {
        DVD removedDVD = dvds.remove(title);
        return removedDVD;
    }

    @Override
    public DVD editDVD(String newTitle, String oldTitle) throws DVDLibraryDaoException {
        DVD editedDVD = dvds.remove(oldTitle);
        removeDVD(oldTitle);
        addDVD(newTitle, editedDVD);
        dvds.put(newTitle, editedDVD);
        return editedDVD;
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException {
        return new ArrayList<DVD>(dvds.values());
    }

    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException {
        return dvds.get(title);
    }

    @Override
    public List<DVD> findDVDByRating(String mpaaRating) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVD> findDVDByDirector(String director) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVD> findDVDByStudio(String studio) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DVD findNewestDVD(List<DVD> listOfDVDs) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DVD findOldestDVD(List<DVD> listOfDVDs) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal findAverageAgeOfDVDs(List<DVD> listOfDVDs) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVD> findDVDsReleasedInNumOfYears(int numOfYears) throws DVDLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
