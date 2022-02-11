/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Superpower;
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
public class SuperpowerDaoDB implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superpower getSuperpowerById(int superpowerId) {
        
        try{
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superpower WHERE id = ?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), superpowerId);
        }catch(DataAccessException ex){
            return null;
        }
        
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM superpower";
        return jdbc.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
        
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        
        final String INSERT_SUPERPOWER = "INSERT INTO superpower(name, description) VALUES(?,?)";
        jdbc.update(INSERT_SUPERPOWER,
                superpower.getName(),
                superpower.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);
        return superpower;
        
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        
        final String UPDATE_SUPERPOWER = "UPDATE superpower SET name = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superpower.getName(),
                superpower.getDescription(),
                superpower.getId());
        
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int superpowerId) {
        
        final String DELETE_FROM_HERO_SUPERPOWER = "DELETE FROM hero_superpower WHERE superpowerId = ?";
        jdbc.update(DELETE_FROM_HERO_SUPERPOWER, superpowerId);
        
        final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, superpowerId);
        
    }
    
    public static final class SuperpowerMapper implements RowMapper<Superpower>{

        @Override
        public Superpower mapRow(ResultSet rs, int rowNum) throws SQLException {
            Superpower power = new Superpower();
            power.setId(rs.getInt("id"));
            power.setName(rs.getString("name"));
            power.setDescription(rs.getString("description"));
            return power;
        }
        
    }
    
}
