/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.servicelayer;

import com.tk.guessthenumber.models.Game;
import com.tk.guessthenumber.models.Round;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall
 */
@Service
public interface GuessTheNumberServiceLayer {
    
    public int createGame();
    
    public Game addGame(Game game);
    
    public Round guess(Round round);
    
    public List<Game> getAllGames();
    
    public Game getGameById(int gameId);
    
    public List<Round> getRoundsByGameId(int gameId);
    
    public String getResult(String guess, String answer);
    
}
