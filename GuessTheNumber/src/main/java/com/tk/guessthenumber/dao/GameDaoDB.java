/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.dao;

import com.tk.guessthenumber.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class GameDaoDB implements GameDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    @Transactional
    public Game addGame(Game game) {
        
        final String CREATE_GAME = "INSERT INTO game(answer) VALUES(?);";
        jdbc.update(CREATE_GAME, game.getAnswer());
        
        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);
        return game;
        
    }

    @Override
    public void updateGame(Game game) {
        
        final String UPDATE_GAME = "UPDATE game SET finished = ? WHERE gameId = ?;";
        jdbc.update(UPDATE_GAME, game.isFinished(), game.getGameId());
        
    }

    @Override
    public List<Game> getAllGames() {
        
        final String SELECT_ALL_GAMES = "SELECT * FROM game;";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
        
    }

    @Override
    public Game getGameById(int gameId) {
        
        try{
        
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameId = ?;";
            return jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), gameId);
        
        }catch(DataAccessException e) {
            return null;
        }
    }
    
    //for testing
    @Override
    public boolean deleteByGameId(int id) {
        
        final String sql1 = "DELETE FROM round WHERE gameId = ?";
        boolean deleted = jdbc.update(sql1, id) > 0;
        final String sql = "DELETE FROM game WHERE gameId = ?";
        return jdbc.update(sql, id) > 0;
    }
    
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
        
    }
    
}
