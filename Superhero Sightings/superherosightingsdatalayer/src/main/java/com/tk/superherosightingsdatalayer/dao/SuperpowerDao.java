/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface SuperpowerDao {
    
    Superpower getSuperpowerById(int superpowerId);
    
    List<Superpower> getAllSuperpowers();
    
    Superpower addSuperpower(Superpower superpower);
    
    void updateSuperpower(Superpower superpower);
    
    void deleteSuperpowerById(int superpowerId);

    
}
