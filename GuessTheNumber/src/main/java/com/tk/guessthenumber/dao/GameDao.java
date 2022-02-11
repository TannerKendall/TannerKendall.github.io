/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.dao;

import com.tk.guessthenumber.models.Game;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface GameDao {
    
    public Game addGame(Game game);
    
    public void updateGame(Game game);
    
    public List<Game> getAllGames();
    
    public Game getGameById(int gameId);
    
    public boolean deleteByGameId(int id);
    
}
