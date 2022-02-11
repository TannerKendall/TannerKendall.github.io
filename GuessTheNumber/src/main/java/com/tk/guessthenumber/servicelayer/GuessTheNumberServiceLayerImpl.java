/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.servicelayer;

import com.tk.guessthenumber.dao.GameDao;
import com.tk.guessthenumber.dao.RoundDao;
import com.tk.guessthenumber.models.Game;
import com.tk.guessthenumber.models.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tanner Kendall
 */
@Service
public class GuessTheNumberServiceLayerImpl implements GuessTheNumberServiceLayer {

    @Autowired
    GameDao gameDao;
    @Autowired
    RoundDao roundDao;
    
    @Override
    public int createGame() {
        
        Game game = new Game();
        game.setAnswer(generateNewAnswer());
        game = addGame(game);
        
        return game.getGameId();
        
    }

    
    @Override
    public Game addGame(Game game) {
        return gameDao.addGame(game);
    }
    
    
    @Override
    public Round guess(Round round) {
        
        String answer = gameDao.getGameById(round.getGameId()).getAnswer();
        String guess = round.getGuess();
        String result = getResult(guess, answer);
        round.setResult(result);
        
        if(guess.equals(answer)){
            Game game = getGameById(round.getGameId());
            game.setFinished(true);
            gameDao.updateGame(game);
        }
        
        return roundDao.addRound(round);
        
    }

    @Override
    public List<Game> getAllGames() {
        
        List<Game> games = gameDao.getAllGames();
        
        for(Game game: games){
            if(!game.isFinished()){
                game.setAnswer("----");
            }
        }
        
        return games;
        
    }

    @Override
    public Game getGameById(int gameId) {
        
        Game game = gameDao.getGameById(gameId);
        
        if(!game.isFinished()){
            game.setAnswer("----");
        }
        
        return game;
        
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        
        return roundDao.getAllRoundsByGame(gameId);

    }
    
    private String generateNewAnswer(){
        
        Random random = new Random();
        int digit1, digit2, digit3, digit4;
        
        digit1 = random.nextInt(10);
        
        digit2 = random.nextInt(10);
        while(digit2 == digit1){
            digit2 = random.nextInt(10);
        }
        
        digit3 = random.nextInt(10);
        while(digit3 == digit1 || digit3 == digit2){
            digit3 = random.nextInt(10);
        }
        
        digit4 = random.nextInt(10);
        while(digit4 == digit1 || digit4 == digit2 || digit4 == digit3){
            digit4 = random.nextInt(10);
        }
        
        return String.valueOf(digit1) + String.valueOf(digit2) + String.valueOf(digit3)
                + String.valueOf(digit4);
        
    }
    
    @Override
    public String getResult(String guess, String answer){
        
        char[] guessDigits = guess.toCharArray();
        char[] answerDigits = answer.toCharArray();
        
        int exactCorrect = 0;
        int partialCorrect = 0;
        String result;
        
        for(int i = 0; i < guessDigits.length; i++){
            
            if(answer.indexOf(guessDigits[i]) > - 1){
                if(guessDigits[i] == answerDigits[i]){
                    exactCorrect++;
                } else {
                    partialCorrect++;
                }
            }
            
        }
        
        result = "e:" + exactCorrect + ":p:" + partialCorrect;
        
        return result;
    }

}
