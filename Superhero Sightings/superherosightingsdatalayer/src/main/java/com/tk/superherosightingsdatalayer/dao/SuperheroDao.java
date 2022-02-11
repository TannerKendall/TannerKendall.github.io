/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface SuperheroDao {
    
    Superhero getSuperheroById(int superheroId);
    
    List<Superhero> getAllSuperheroes();
    
    Superhero addSuperhero(Superhero superhero);
    
    Superhero updateSuperhero(Superhero superhero);
    
    void deleteSuperheroById(int superheroId);
    
    List<Superhero> getSuperheroesForSuperpower(Superpower superpower);
    
    Superhero getSuperheroForSighting(Sighting sighting);
    
    List<Superhero> getSuperHeroesForLocation(Location location);
    
    List<Sighting> getSightingsForSuperhero(int superheroId);
    
}
