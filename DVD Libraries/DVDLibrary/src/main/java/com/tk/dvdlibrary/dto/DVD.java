/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.dvdlibrary.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author Tanner Kendall
 */
public class DVD {
    
    private String title;
    private LocalDate releaseDate;
    private String mpaaRating;
    private String director;
    private String studio;
    private String userRating;
    //private boolean valid;

    public DVD(String title){
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        String releaseDateString = releaseDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return releaseDateString;
    }
    
    public LocalDate getReleaseDateLocalDate(){
        return releaseDate;
    }

    public void setReleaseDate(String releaseDateString) {
        this.releaseDate = LocalDate.parse(releaseDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        //this.releaseDate = releaseDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        if(mpaaRating.equalsIgnoreCase("G") || mpaaRating.equalsIgnoreCase("PG") || 
                mpaaRating.equalsIgnoreCase("PG13") || mpaaRating.equalsIgnoreCase("PG-13") || mpaaRating.equalsIgnoreCase("R") || mpaaRating.equalsIgnoreCase("Unrated")){
            this.mpaaRating = mpaaRating;
        } else {
            this.mpaaRating = "Unrated";
        }
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.releaseDate);
        hash = 97 * hash + Objects.hashCode(this.mpaaRating);
        hash = 97 * hash + Objects.hashCode(this.director);
        hash = 97 * hash + Objects.hashCode(this.studio);
        hash = 97 * hash + Objects.hashCode(this.userRating);
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
        final DVD other = (DVD) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
            return false;
        }
        if (!Objects.equals(this.mpaaRating, other.mpaaRating)) {
            return false;
        }
        if (!Objects.equals(this.director, other.director)) {
            return false;
        }
        if (!Objects.equals(this.studio, other.studio)) {
            return false;
        }
        if (!Objects.equals(this.userRating, other.userRating)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DVD{" + "title=" + title + ", releaseDate=" + releaseDate + 
                ", mpaaRating=" + mpaaRating + ", director=" + director + 
                ", studio=" + studio + ", userRating=" + userRating + '}';
    }
    
    
    
}
