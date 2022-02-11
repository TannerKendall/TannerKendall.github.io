/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.entities;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Tanner Kendall
 */
public class Sighting {
    
    private int id;
    
    @NotNull(message = "Please create a Superhero first..")
    @Min(value = 0)
    private int superheroId;
    
    //@NotNull(message = "Please create a Location first..")
    private Location location;
    
    @NotNull(message = "Date required.")
    @Past
    private LocalDate sightingDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(int superheroId) {
        this.superheroId = superheroId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.superheroId;
        hash = 89 * hash + Objects.hashCode(this.location);
        hash = 89 * hash + Objects.hashCode(this.sightingDate);
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
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.superheroId != other.superheroId) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sighting{" + "id=" + id + ", superheroId=" + superheroId + ", location=" + location + ", sightingDate=" + sightingDate + '}';
    }
    
}
