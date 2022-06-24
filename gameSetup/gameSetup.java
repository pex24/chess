package gameSetup;

import java.awt.Color;
import java.util.ArrayList;

import pieces.Piece;

public class gameSetup implements Runnable {
    public Board gameBoard;
    public EndGameConditions endGameConditions;
    public Player player1;
    public Player player2;
    public Player currentPlayer;
    public int turnCount;
    
    public gameSetup() {
        this.gameBoard = new Board();       
        this.endGameConditions = new EndGameConditions();
        
        player1 = new Player(this, Color.WHITE, null);
        player2 = new Player(this, Color.BLACK, null);
        this.gameBoard.placePiecesOnBoard(this);
        PlacePieces pp = new PlacePieces(this);
        player1.assignPieces(pp.getWhitePieces(), pp.getWhitePawns());
        player1.assignOppoenentPieces(pp.getBlackPieces(), pp.getBlackPawns());
        player2.assignPieces(pp.getBlackPieces(), pp.getBlackPawns());
        player2.assignOppoenentPieces(pp.getWhitePieces(), pp.getWhitePawns());
        turnCount = 0;
        player1.setTurn(true);
        currentPlayer = player1;
        player1.startPlayersTurn();
    }
    
    public void endTurn(Player currentPlayer) {
       turnCount++;
       switchCurrentPlayer();
       //System.out.println(currentPlayer + "'s Turn Has Ended");
    }
    
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    
    public void switchCurrentPlayer() {
        if (this.currentPlayer == player1) {
            currentPlayer = player2;
            currentPlayer.startPlayersTurn();
            player1.setTurn(false);
            player2.setTurn(true);
        }
        else {
            currentPlayer = player1;
            currentPlayer.startPlayersTurn();
            player1.setTurn(true);
            player2.setTurn(false);
        }
    }
    
    public static void main (String args[]) {
        gameSetup gameSetup = new gameSetup();
        DisplayBoard display = new DisplayBoard();
        gameSetup.run();
    }

    @Override
    public void run() {
        // while not checkMate, let player select a move, execute move and then switch player
        while(endGameConditions.checkIfGameIsOver()) {
            System.out.println("Game Not Over");
            //Player currentPlayer = getCurrentPlayer();
            //Piece selectedPiece = currentPlayer;
            //Square pieceMovingTo = currentPlayer;
            //currentPlayer.movePiece(selectedPiece, pieceMovingTo);
        }
        System.out.println("Game Over");
       
    }
}
