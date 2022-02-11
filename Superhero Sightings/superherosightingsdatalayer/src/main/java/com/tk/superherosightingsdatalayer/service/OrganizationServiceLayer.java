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
import com.tk.superherosightingsdatalayer.entities.Organization;
import com.tk.superherosightingsdatalayer.entities.Superhero;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
@Service
public class OrganizationServiceLayer {
    
    @Autowired
    OrganizationDao orgDao;
    
    public Organization createOrganization(String name, boolean isHero, String description, String address, String contactInfo, List<Superhero> heroes){
    
        Organization org = new Organization();
        org.setName(name);
        org.setIsHero(isHero);
        org.setDescription(description);
        org.setAddress(address);
        org.setContact(contactInfo);
        org.setOrgMembers(heroes);
        return org;
    }
    
    public Organization getOrganizationById(int id){
        return orgDao.getOrganizationById(id);
    }
    
    public List<Organization> getAllOrganizations(){
        return orgDao.getAllOrganizations();
    }
    
    public Organization addOrganization(Organization org){
        return orgDao.addOrganization(org);
    }
    
    public void editOrganization(Organization org){
        orgDao.updateOrganization(org);
    }
    
    public void deleteOrganizationById(int id){
        orgDao.deleteOrganizationById(id);
    }
}
