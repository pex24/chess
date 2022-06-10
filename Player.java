package gameSetup;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

import pieces.King;
import pieces.Piece;

public class Player {
    Board gameBoard;
    Color color;
    ArrayList<Piece> playerPieces;
    ArrayList<Piece> playerLostPieces;
    private boolean isPlayersTurn;
    private Time timeLeft;
    private King king;
    private gameSetup setup;
    
    public Player(gameSetup setup, Color cl, Time time) {
        this.color = cl;
        this.setup = setup;
        this.gameBoard = setup.gameBoard;
        this.playerLostPieces = new ArrayList<Piece>();
        this.timeLeft = time;
        this.playerPieces = new ArrayList<Piece>();      
    }

    public String toString() {
        if (this.color == Color.WHITE) {
            return "White Player";
        }
        else {
            return "Black Player";
        }
    }
    
    public boolean inCheck() {
        Square kingLocation = setup.gameBoard.getKingLocation(this);
        ArrayList<Square> possibleMoves = new ArrayList<Square>();
        Player opponent = null;
        if (this.color == Color.BLACK) {
           opponent = setup.player1;
        }
        if (this.color == Color.WHITE) {
            opponent = setup.player2;
        }
        for (Piece p : opponent.playerPieces) {
            possibleMoves.addAll(p.possibleMoves());
        }
        if (possibleMoves.contains(kingLocation)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void assignPieces(ArrayList<Piece> pieces) {
        this.playerPieces = pieces;
    }
  
    public void setTurn(boolean bool) {
        isPlayersTurn = bool;
    }
    
    public boolean getTurn() {
        return this.isPlayersTurn;
    }
    public Color getPlayerColor() {
        return this.color;
    }
    public ArrayList<Piece> getPlayersPieces(){
        return playerPieces;
    }
    
    Timer timer = new Timer();
   
}
