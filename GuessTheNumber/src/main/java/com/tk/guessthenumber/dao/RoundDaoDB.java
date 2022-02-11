/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.dao;

import com.tk.guessthenumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tanner Kendall
 */
@Repository
public class RoundDaoDB implements RoundDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Round addRound(Round round) {
        
        final String INSERT_ROUND = "INSERT INTO round(gameId, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getGameId(), round.getGuess(), round.getResult());
        
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        
        return getRoundById(newRoundId);
        
    }

    @Override
    public List<Round> getAllRoundsByGame(int gameId) {
        try{
            
            final String SELECT_ROUNDS_BY_GAME = "SELECT * FROM round WHERE gameId = ? ORDER BY timeofguess;";
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAME, new RoundMapper(), gameId);
            return rounds;
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Round getRoundById(int roundId) {
        try{
            
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE roundId = ?;";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
            
        }catch(DataAccessException e){
            return null;
        }
    }
    
    //for testing
    @Override
    @Transactional
    public boolean deleteByRoundId(int id) {
        final String sql = "DELETE FROM round WHERE roundId = ?";
        return jdbc.update(sql, id) > 0;
    }
    
    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            
            Timestamp ts = rs.getTimestamp("timeOfGuess");
            round.setTimeOfGuess(ts.toLocalDateTime());
            
            return round;
        }
         
    }
    
}
