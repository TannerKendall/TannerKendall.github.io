/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.dao;

import com.tk.guessthenumber.TestApplicationConfiguration;
import com.tk.guessthenumber.models.Game;
import com.tk.guessthenumber.models.Round;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tanner Kendall
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoDBTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public GameDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        List<Round> rounds = roundDao.getAllRoundsByGame(1);
        for(Round round : rounds) {
            roundDao.deleteByRoundId(round.getRoundId());
        }
        
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteByGameId(game.getGameId());
        }
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addGame method, of class GameDaoDB.
     */
    @Test
    public void testAddGetAllGames() {
        
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setFinished(false);
        gameDao.addGame(game1);
        
        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setFinished(false);
        gameDao.addGame(game2);
        
        List<Game> games = gameDao.getAllGames();
        
        //assertEquals(2, games.size());
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
        
    }

    /**
     * Test of updateGame method, of class GameDaoDB.
     */
    @Test
    public void testGetUpdateGame() {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
        
        game.setFinished(true);
        
        gameDao.updateGame(game);
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
    }

}
