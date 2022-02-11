/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Tanner Kendall
 */
public class Organization {
    
    private int id;
    
    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name must be no longer than 50 characters.")
    private String name;
    
    private boolean isHero;
    
    @NotBlank(message = "Description required.")
    @Size(max = 255, message = "Description can be no longer than 255 characters.")
    private String description;
    
    @NotBlank(message = "Address required.")
    @Size(max = 255, message = "Address can be no longer than 255 characters.")
    private String address;
    
    @NotBlank(message = "Contact info required.")
    @Size(max = 255, message = "Contact can be no longer than 255 characters.")
    private String contact;
    
    
    private List<Superhero> orgMembers;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsHero() {
        return isHero;
    }

    public void setIsHero(boolean isHero) {
        this.isHero = isHero;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Superhero> getOrgMembers() {
        return orgMembers;
    }

    public void setOrgMembers(List<Superhero> orgMembers) {
        this.orgMembers = orgMembers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + (this.isHero ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.address);
        hash = 41 * hash + Objects.hashCode(this.contact);
        hash = 41 * hash + Objects.hashCode(this.orgMembers);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.isHero != other.isHero) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.orgMembers, other.orgMembers)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", name=" + name + ", isHero=" + isHero + ", description=" + description + ", address=" + address + ", contact=" + contact + ", orgMembers=" + orgMembers + '}';
    }
    
}
