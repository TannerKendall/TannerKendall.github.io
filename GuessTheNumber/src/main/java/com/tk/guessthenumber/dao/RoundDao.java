/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.dao;

import com.tk.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface RoundDao {
    
    public Round addRound(Round round);
    
    public List<Round> getAllRoundsByGame(int gameId);
    
    public Round getRoundById(int roundId);
    
    public boolean deleteByRoundId(int id);
    
}
