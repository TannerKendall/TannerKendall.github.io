/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.controllers;

import com.tk.guessthenumber.models.Game;
import com.tk.guessthenumber.models.Round;
import com.tk.guessthenumber.servicelayer.GuessTheNumberServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tanner Kendall
 */
@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {
    
    @Autowired
    GuessTheNumberServiceLayer service;
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {
        return service.createGame();
    }
    
    @PostMapping("/guess")
    public Round guess(@RequestBody Round round){
        return service.guess(round);
    }
    
    @GetMapping("/game")
    public List<Game> getAllGames(){
        return service.getAllGames();
    }
    
    @GetMapping("/game/{gameId}")
    public Game getGameById(@PathVariable("gameId") int gameId){
        return service.getGameById(gameId);
    }
    
    @GetMapping("/rounds/{gameId}")
    public List<Round> getRounds(@PathVariable("gameId") int gameId){
        return service.getRoundsByGameId(gameId);
    }

}
