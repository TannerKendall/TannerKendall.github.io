/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.dao;

import com.tk.superherosightingsdatalayer.dao.SuperheroDaoDB.SuperheroMapper;
import com.tk.superherosightingsdatalayer.entities.Location;
import com.tk.superherosightingsdatalayer.entities.Organization;
import com.tk.superherosightingsdatalayer.entities.Superhero;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    SuperheroDaoDB superheroDao;
    
    @Override
    public Organization getOrganizationById(int organizationId) {
        try{
            final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization org = jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), organizationId);
            org.setOrgMembers(getSuperherosForOrganization(organizationId));
            return org;
        }catch(DataAccessException ex){
            return null;
        }
    }

    private List<Superhero> getSuperherosForOrganization(int organizationId){
        
        final String SELECT_SUPERHEROES_FOR_ORGANIZATION = "SELECT * FROM superhero sh "
                + "JOIN hero_organization ho ON sh.id = ho.superheroId WHERE ho.organizationId = ?";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_ORGANIZATION, new SuperheroMapper(), organizationId);
        superheroDao.associateSuperpowersAndSightings(superheroes);
        return superheroes;
        
    }
    
    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateSuperherosToOrg(organizations);
        return organizations;
    }

    private void associateSuperherosToOrg(List<Organization> organizations){
        for(Organization organization : organizations){
            organization.setOrgMembers(getSuperherosForOrganization(organization.getId()));
        }
    }
    
    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name, isHero, description, address, contact) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.isIsHero(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertIntoSuperheroOrganization(organization);
        return organization;
        
    }

    private void insertIntoSuperheroOrganization(Organization organization){
        
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_organization(superheroId, organizationId) VALUES(?,?)";
        for(Superhero superhero : organization.getOrgMembers()){
            jdbc.update(INSERT_HERO_ORGANIZATION, superhero.getId(), organization.getId());
        }
        
    }
    
    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, isHero = ?, description = ?, address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.isIsHero(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getId());
        final String DELETE_FROM_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, organization.getId());
        insertIntoSuperheroOrganization(organization);
        
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int organizationId) {
        final String DELETE_FROM_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, organizationId);
        
        final String DELETE_FROM_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_FROM_ORGANIZATION, organizationId);
    }
    
    @Override
    public List<Organization> getOrganizationsForSuperhero(Superhero superhero){
        
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                + "JOIN hero_organization ho ON ho.organizationId = o.id WHERE ho.superheroId = ?";
        List<Organization> organizationsForSuperhero = jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationMapper(), superhero.getId());
        associateSuperherosToOrg(organizationsForSuperhero);
        return organizationsForSuperhero;
        
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization>{

        @Override
        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setIsHero(rs.getBoolean("isHero"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setContact(rs.getString("contact"));
            return org;
        }
        
    }
    
}
