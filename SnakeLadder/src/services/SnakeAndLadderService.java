package services;

import models.*;

import java.util.*;

public class SnakeAndLadderService {
    private Board snakeAndLadderBoard;
    private int initialNumberOfPlayers;
    private Queue<Player> players;
    private boolean isGameCompleted;

    private int noOfDices; // optional
    private boolean shouldGameContinueTillLastPlayer; // optional
    private boolean shouldAllowMultipleDiceRollOnSix; // optional

    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NO_OF_DICES = 1;

    public SnakeAndLadderService(int boardSize) {
        this.snakeAndLadderBoard = new Board(boardSize);
        this.noOfDices = SnakeAndLadderService.DEFAULT_NO_OF_DICES;
        this.players = new LinkedList<Player>();
    }
    public SnakeAndLadderService() {
        this(SnakeAndLadderService.DEFAULT_BOARD_SIZE);
    }

    public void setNoOfDices(int noOfDices){
        this.noOfDices = noOfDices;
    }

    public void setShouldGameContinueTillLastPlayer(boolean shouldGameContinueTillLastPlayer) {
        this.shouldGameContinueTillLastPlayer = shouldGameContinueTillLastPlayer;
    }

    public void setShouldAllowMultipleDiceRollOnSix(boolean shouldAllowMultipleDiceRollOnSix) {
        this.shouldAllowMultipleDiceRollOnSix = shouldAllowMultipleDiceRollOnSix;
    }

    public void setPlayers(List<Player> players) {
        this.players = new LinkedList<Player>();
        this.initialNumberOfPlayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<>();
        for(Player p: players){
            this.players.add(p);
            playerPieces.put(p.getId(), 0);
        }
        this.snakeAndLadderBoard.setPlayerPieces(playerPieces);
    }

    public void setSnakes(List<Snake> snakes) {
        snakeAndLadderBoard.setSnakes(snakes); // Add snakes to board
    }

    public void setLadders(List<Ladder> ladders) {
        snakeAndLadderBoard.setLadders(ladders); // Add ladders to board
    }

    private int getNewPosition(int newPosition){
        int previousPosition;
        do {
            previousPosition = newPosition;
            for(Snake snake: snakeAndLadderBoard.getSnakes()){
                if(snake.getStart() == newPosition){
                    newPosition = snake.getEnd();
                }
            }

            for(Ladder ladder: snakeAndLadderBoard.getLadders()){
                if(ladder.getStart() == newPosition){
                    newPosition = ladder.getEnd();
                }
            }
        } while(previousPosition!=newPosition);
        return newPosition;
    }

    private void movePlayer(Player player, int positions) {
        int oldPosition = snakeAndLadderBoard.getPlayerPieces().get(player.getId());
        int newPosition = oldPosition + positions;

        int boardSize = snakeAndLadderBoard.getSize();
        if(newPosition > boardSize){
            newPosition = oldPosition;
        } else {
            newPosition = getNewPosition(newPosition);
        }

        snakeAndLadderBoard.getPlayerPieces().put(player.getId(), newPosition);
        System.out.println(player.getName() + " rolled a " + positions + " and moved from " + oldPosition +" to " + newPosition);
    }

    private int getTotalValueAfterDiceRolls(){
        return DiceService.roll();
    }

    private boolean hasPlayerWon(Player player){
        int playerPosition = snakeAndLadderBoard.getPlayerPieces().get(player.getId());
        int winningPosition = snakeAndLadderBoard.getSize();
        return playerPosition == winningPosition;
    }

    private boolean isGameCompleted(){
        int currentNumberOfPlayers = players.size();
        return currentNumberOfPlayers < initialNumberOfPlayers;
    }

    public void startGame(){
        while(!isGameCompleted()){
            int totalDiceValue = getTotalValueAfterDiceRolls();
            Player currentPlayer = players.poll();
            assert currentPlayer != null;
            movePlayer(currentPlayer, totalDiceValue);
            if(hasPlayerWon(currentPlayer)){
                System.out.println(currentPlayer.getName() + " wins the game");
                snakeAndLadderBoard.getPlayerPieces().remove(currentPlayer.getId());
            } else {
                players.add(currentPlayer);
            }
        }
    }
}
