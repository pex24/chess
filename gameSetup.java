package gameSetup;

import java.awt.Color;
import java.util.ArrayList;

import pieces.Piece;

public class gameSetup {
    public Board gameBoard;
    public EndGameConditions egc;
    public Player player1;
    public Player player2;
    public int turnCount;
    
    public gameSetup() {
        this.gameBoard = new Board();
        
        player1 = new Player(this , Color.WHITE, null);
        player2 = new Player(this , Color.BLACK, null);
        this.gameBoard.placePiecesOnBoard(this);
        player1.assignPieces(gameBoard.whitePieces);
        player2.assignPieces(gameBoard.blackPieces);

        turnCount = 0;
        player1.setTurn(true);
        egc = new EndGameConditions();
    }
    
    public void endTurn() {
       if (player1.getTurn()) {
           player2.setTurn(true);
           player1.setTurn(false);
       }
       else {
           player1.setTurn(true);
           player2.setTurn(false);
       }
    }
    
    
    public Player currentPlayer() {
        if (player1.getTurn()) {
            System.out.println(player1);
            return player1;
        }
        else {
            System.out.println(player2);
            return player2;
        }
    }
    
    public static void main (String args[]) {
        gameSetup startGame = new gameSetup();
    }
}
