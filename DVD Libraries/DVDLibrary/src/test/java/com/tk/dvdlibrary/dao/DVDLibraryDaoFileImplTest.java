/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.dvdlibrary.dao;

import com.tk.dvdlibrary.dto.DVD;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tanner Kendall
 */
public class DVDLibraryDaoFileImplTest {
    
    DVDLibraryDao testDao;
    
    public DVDLibraryDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testlibrary.txt";
        //use fileWriter to quickly blank the file
        new FileWriter(testFile);
        //testDao = new DVDLibraryDaoFileImpl(testFile);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("dvdLibraryDao", DVDLibraryDaoFileImpl.class);
    }
    
    @Test
    public void testAddGetDVD() throws Exception {
        //Create our method test inputs
        String title = "The Avengers";
        DVD dvd = new DVD(title);
        dvd.setReleaseDate("01/02/2002");
        dvd.setMpaaRating("R");
        dvd.setDirector("Joss Whedon");
        dvd.setStudio("Marvel");
        dvd.setUserRating("Good");
        
        //add DVD to the dao
        testDao.addDVD(title, dvd);
        //Get DVD from the DAO
        DVD retrievedDVD = testDao.getDVD(title);
        
        //Check data is equal
        assertEquals(dvd.getTitle(), retrievedDVD.getTitle(), "Checking Title");
        assertEquals(dvd.getReleaseDate(), retrievedDVD.getReleaseDate(), "Checking Release Date");
        assertEquals(dvd.getMpaaRating(), retrievedDVD.getMpaaRating(), "Checking MPAA rating");
        assertEquals(dvd.getDirector(), retrievedDVD.getDirector(), "Checking Director");
        assertEquals(dvd.getStudio(), retrievedDVD.getStudio(), "Checking Studio.");
        assertEquals(dvd.getUserRating(), retrievedDVD.getUserRating(), "Checking user rating.");
    }
    
    @Test
    public void testAddGetAllDVDs() throws Exception {
        //Create first DVD
        DVD firstDVD = new DVD("The Avengers");
        firstDVD.setReleaseDate("01/02/2002");
        firstDVD.setMpaaRating("R");
        firstDVD.setDirector("Joss Whedon");
        firstDVD.setStudio("Marvel");
        firstDVD.setUserRating("Good");
        
        //Create second DVD
        DVD secondDVD = new DVD("The Prestige");
        secondDVD.setReleaseDate("05/06/2002");
        secondDVD.setMpaaRating("PG");
        secondDVD.setDirector("Christopher Nolan");
        secondDVD.setStudio("Warner Bros");
        secondDVD.setUserRating("Amazing");
        
        //add both DVDs to the dao
        testDao.addDVD(firstDVD.getTitle(), firstDVD);
        testDao.addDVD(secondDVD.getTitle(), secondDVD);
        
        //Retrieve list of DVDs
        List<DVD> allDVDs = testDao.getAllDVDs();
        
        //First check general contents of the list
        assertNotNull(allDVDs, "The list of DVDs must not be null");
        assertEquals(2, allDVDs.size(), "The size of the list should be 2");
        
        //Then the specifics
        assertTrue(testDao.getAllDVDs().contains(firstDVD), "The list should contain The Avengers");
        assertTrue(testDao.getAllDVDs().contains(secondDVD), "The list should contain the Prestige");
        
    }
    
    @Test
    public void testRemoveDVD() throws Exception {
        //Create two new DVDs
        //Create first DVD
        DVD firstDVD = new DVD("The Avengers");
        firstDVD.setReleaseDate("01/02/2002");
        firstDVD.setMpaaRating("R");
        firstDVD.setDirector("Joss Whedon");
        firstDVD.setStudio("Marvel");
        firstDVD.setUserRating("Good");
        
        //Create second DVD
        DVD secondDVD = new DVD("The Prestige");
        secondDVD.setReleaseDate("05/06/2002");
        secondDVD.setMpaaRating("PG");
        secondDVD.setDirector("Christopher Nolan");
        secondDVD.setStudio("Warner Bros");
        secondDVD.setUserRating("Amazing");
        
        //add both to the DAO
        testDao.addDVD(firstDVD.getTitle(), firstDVD);
        testDao.addDVD(secondDVD.getTitle(), secondDVD);
        
        //remove the first DVD - The Avengers
        DVD removedDVD = testDao.removeDVD(firstDVD.getTitle());
        
        //check that the correct DVD was removed
        assertEquals(removedDVD, firstDVD, "The removed DVD should be The Avengers");
        
        //Get all DVDs
        List<DVD> allDVDs = testDao.getAllDVDs();
        
        //First check the general contents of the list
        assertNotNull(allDVDs, "All DVDs list should not be null");
        assertEquals(1, allDVDs.size(), "The size of the list should be 1");
        
        //Then specifics
        assertFalse(allDVDs.contains(firstDVD), "All DVDs should not include The Avengers");
        assertTrue(allDVDs.contains(secondDVD), "All DVDs should contain The Prestige");
        
        //remove the second DVD
        removedDVD = testDao.removeDVD(secondDVD.getTitle());
        
        //Check that the correct DVD was removed
        assertEquals(removedDVD, secondDVD, "The removed DVD should be The Prestige");
        
        //Retrieve list of DVDs again and check the list
        allDVDs = testDao.getAllDVDs();
        
        //Check the contents of the list, it should be empty
        assertTrue(allDVDs.isEmpty(), "The retrieved list of DVDs should be empty");
        
        //Try to 'get' both DVDs by their old title - they should be null
        DVD retrievedDVD = testDao.getDVD(firstDVD.getTitle());
        assertNull(retrievedDVD, "The Avengers was removed, should be null.");
        
        retrievedDVD = testDao.getDVD(secondDVD.getTitle());
        assertNull(retrievedDVD, "The Prestige was removed, should be null.");
        
    }
    
    @Test
    public void testEditDVD() throws Exception {
        //Create originalStrings to compare changed fields
        String originalTitle = "The Avengers";
        String originalReleaseDate = "05/06/2020";
        String originalMpaaRating = "PG";
        String originalDirector = "Joss Whedon";
        String originalStudio = "Marvel";
        String originalUserRating = "Great";
        
        //Create edit title
        String newTitle = "The Prestige";
        
        //Create DVD object
        DVD dvd = new DVD(originalTitle);
        dvd.setReleaseDate(originalReleaseDate);
        dvd.setMpaaRating(originalMpaaRating);
        dvd.setDirector(originalDirector);
        dvd.setStudio(originalStudio);
        dvd.setUserRating(originalUserRating);
        
        //Test current fields
        assertEquals(dvd.getTitle(), originalTitle, "Checking Title");
        assertEquals(dvd.getReleaseDate(), originalReleaseDate, "Checking Release Date");
        assertEquals(dvd.getMpaaRating(), originalMpaaRating, "Checking MPAA Rating");
        assertEquals(dvd.getDirector(), originalDirector, "Checking Director");
        assertEquals(dvd.getStudio(), originalStudio, "Checking Studio");
        assertEquals(dvd.getUserRating(), originalUserRating, "Checking UserRating");
        
        //add OG DVD
        testDao.addDVD(originalTitle, dvd);
        
        //Create list
        List<DVD> allDVDs = testDao.getAllDVDs();
        
        //test list contains DVD with ORIGINAL TITLE
        assertNotNull(allDVDs, "The list of DVDs should not be null");
        assertEquals(1, allDVDs.size(), "The list of DVDs should contain 1 object");
        
        //Test list contains DVD with ORIGINAL TITLE
        assertTrue(testDao.getAllDVDs().contains(dvd), "The list should include The Avengers");
        
        //Set new title for dvd
        dvd.setTitle(newTitle);
        //create new DVD from edit, that is the same as our OG DVD
        DVD editedDVD = testDao.editDVD(newTitle, originalTitle);
        //remove our NEW COPY of our DVD from the DAO and List
        testDao.removeDVD(originalTitle);
        allDVDs = testDao.getAllDVDs();
        allDVDs.remove(editedDVD);
        
        //test list contains Original DVD with NEW TITLE
        assertNotNull(allDVDs, "The list of DVDs should not be null");
        assertEquals(1, allDVDs.size(), "The list of DVDs should still contain 1 object");
        
        //test list contains DVD with NEW TITLE
        assertTrue(testDao.getAllDVDs().contains(editedDVD));
        assertEquals(dvd.getTitle(), newTitle, "The new title should be The Prestige");
        
    }

    
}