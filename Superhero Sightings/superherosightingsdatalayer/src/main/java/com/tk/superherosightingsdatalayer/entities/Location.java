/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.superherosightingsdatalayer.entities;

import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tanner Kendall
 */
public class Location {
    
    private int id;
    
    @NotBlank(message = "Name required.")
    @Size(max = 50, message = "Name must be no longer than 50 characters.")
    private String name;
    
    @Digits(integer = 2, fraction = 8, message = "format: xx.xxxxxxxx")
    @DecimalMax(value = "90.0", message = "Latitude should not be greater than 90.0")
    @DecimalMin(value = "-90.0", message = "Latitude should not be less than -90.0")
    private double latitude;
    
    @Digits(integer = 3, fraction = 8, message = "format: xx.xxxxxxxx")
    @DecimalMax(value = "180.0", message = "Longitude should not be greater than 180.0")
    @DecimalMin(value = "-180.0", message = "Longitude should not be less than -180.0")
    private double longitude;
    
    @Size(max = 255, message = "Description can be no longer than 255 characters.")
    private String description;
    
    @Size(max = 255, message = "Address Info can be no longer than 255 characters.")
    private String addressInfo;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.addressInfo);
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.addressInfo, other.addressInfo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", description=" + description + ", addressInfo=" + addressInfo + '}';
    }
    
}
