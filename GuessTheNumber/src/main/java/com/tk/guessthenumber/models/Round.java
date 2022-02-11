/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Tanner Kendall
 */
public class Round {
    
    private int roundId;
    private int gameId;
    private String guess;
    private String result;
    private LocalDateTime timeOfGuess;

    public Round() {
    }

    public Round(int gameId, String guess) {
        this.gameId = gameId;
        this.guess = guess;
    }

    public Round(int roundId, int gameId, String guess, String result, LocalDateTime timeOfGuess) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guess = guess;
        this.result = result;
        this.timeOfGuess = timeOfGuess;
    }
    
    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        if(guess.length() == 1){
            guess = "000" + guess;
        }else if(guess.length() == 2){
            guess = "00" + guess;
            
        }else if(guess.length() == 3){
            guess = "0" + guess;
        }
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getTimeOfGuess() {
        return timeOfGuess;
    }

    public void setTimeOfGuess(LocalDateTime timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.roundId;
        hash = 83 * hash + this.gameId;
        hash = 83 * hash + Objects.hashCode(this.guess);
        hash = 83 * hash + Objects.hashCode(this.result);
        hash = 83 * hash + Objects.hashCode(this.timeOfGuess);
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
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.timeOfGuess, other.timeOfGuess)) {
            return false;
        }
        return true;
    }
  
    
}
