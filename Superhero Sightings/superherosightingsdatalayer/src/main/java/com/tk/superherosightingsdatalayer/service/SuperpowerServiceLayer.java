/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.service;

import com.tk.superherosightingsdatalayer.dao.LocationDao;
import com.tk.superherosightingsdatalayer.dao.OrganizationDao;
import com.tk.superherosightingsdatalayer.dao.SightingDao;
import com.tk.superherosightingsdatalayer.dao.SuperheroDao;
import com.tk.superherosightingsdatalayer.dao.SuperpowerDao;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Service
public class SuperpowerServiceLayer {
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    public Superpower createSuperpower(String name, String description){
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        return superpower;
    }
    
    public List<Superhero> getSuperheroesForSuperpower(Superpower superpower){
        return superheroDao.getSuperheroesForSuperpower(superpower);
    }
    
    public Superpower getSuperpowerById(int id){
        return superpowerDao.getSuperpowerById(id);
    }
    
    public List<Superpower> getAllSuperpowers(){
        return superpowerDao.getAllSuperpowers();
    }
    
    public Superpower addSuperpower(Superpower superpower){
        return superpowerDao.addSuperpower(superpower);
    }
    
    public void updateSuperpower(Superpower superpower){
        superpowerDao.updateSuperpower(superpower);
    }
    
    public void deleteSuperpowerById(int id){
        superpowerDao.deleteSuperpowerById(id);
    }
    
}
