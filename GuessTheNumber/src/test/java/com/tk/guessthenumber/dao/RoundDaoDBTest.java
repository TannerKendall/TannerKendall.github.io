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
import static org.junit.Assert.assertNotNull;
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
public class RoundDaoDBTest {
    
    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    public RoundDaoDBTest() {
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
     * Test of addRound method, of class RoundDaoDB.
     */
    @Test
    public void testAddGetAllRounds() {
        
        int gameId = 1;
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game.setGameId(gameId);
        game = gameDao.addGame(game);
        
        Round round1 = new Round();
        round1.setGuess("8765");
        round1.setResult("e:0:p:0");
        round1.setGameId(game.getGameId());
        roundDao.addRound(round1);
        
        Round round2 = new Round();
        round2.setGuess("1234");
        round2.setResult("e:4:p:0");
        round2.setGameId(game.getGameId());
        roundDao.addRound(round2);
        
        List<Round> rounds = roundDao.getAllRoundsByGame(game.getGameId());
        
        assertEquals(2, rounds.size());
        
        assertNotNull(roundDao.getRoundById(round1.getRoundId()));
        
    }
    
}
