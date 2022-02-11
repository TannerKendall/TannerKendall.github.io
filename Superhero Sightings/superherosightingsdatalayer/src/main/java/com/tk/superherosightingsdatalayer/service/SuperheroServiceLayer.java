/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.service;

import com.tk.superherosightingsdatalayer.dao.LocationDao;
import com.tk.superherosightingsdatalayer.dao.OrganizationDao;
import com.tk.superherosightingsdatalayer.dao.SuperheroDao;
import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Organization;
import com.tk.superherosightingsdatalayer.entities.Sighting;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import com.tk.superherosightingsdatalayer.entities.Superpower;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Service
public class SuperheroServiceLayer {
    
    //private final String IMAGE_DIRECTORY = "src/main/resources/static/images";
    //private final String IMAGE_EXT = ".jpg";
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao orgDao;
    
    public Superhero createSuperhero(String name, String description, boolean isHero, ArrayList<Superpower> superpowers){
        
        Superhero superhero = new Superhero();
        
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setIsHero(isHero);
        superhero.setSuperpowers(superpowers);
        superhero.setSightings(new ArrayList<Sighting>());
        return superhero;
        
    }
    
    public List<Organization> getOrganizationsForSuperhero(Superhero superhero){
        return orgDao.getOrganizationsForSuperhero(superhero);
    }
    
    public List<Location> getLocationsForSuperhero(Superhero superhero){
        return locationDao.getLocationsForSuperhero(superhero);
    }
    
    public List<Sighting> getSightingsForSuperhero(Superhero superhero){
        return superheroDao.getSightingsForSuperhero(superhero.getId());
    }
    
    public Superhero getSuperheroById(int id){
        return superheroDao.getSuperheroById(id);
    }
    
    public List<Superhero> getAllSuperheroes(){
        return superheroDao.getAllSuperheroes();
    }
    
    public Superhero addSuperhero(Superhero superhero){
        return superheroDao.addSuperhero(superhero);
    }
    
    public void updateSuperhero(Superhero superhero){
        superheroDao.updateSuperhero(superhero);
    }
    
    public void deleteSuperheroById(int id){
        superheroDao.deleteSuperheroById(id);
    }
    
    
    /**
    public void uploadFile(String fileName, MultipartFile multipartFile) throws IOException{
        
        java.nio.file.Path uploadPath = Paths.get(IMAGE_DIRECTORY);
        
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        
        try(InputStream inputStream = multipartFile.getInputStream()){
            java.nio.file.Path filePath = uploadPath.resolve(fileName + IMAGE_EXT);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException ioe){
            throw new IOException("Could not save img: " + fileName, ioe);
        }
        
    }
    
    public boolean isImageSet(String fileName){
        try{
            File f = new File(IMAGE_DIRECTORY + "/" + fileName + IMAGE_EXT);
            if(f.exists() && !f.isDirectory()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }
    */
    
}
