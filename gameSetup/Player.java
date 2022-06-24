package gameSetup;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Rook;

public class Player {
    Board gameBoard;
    Color color;
    ArrayList<Piece> playerPieces;
    ArrayList<Piece> opponentPieces;
    private boolean isPlayersTurn;
    private Time timeLeft;
    private King king;
    private Rook shortRook;
    private Rook longRook;
    private King opponentKing;
    private gameSetup setup;
    public boolean inCheck;
    private ArrayList<Square> opponentPossibleMoves;
    private Piece currentlySelectedPiece;
    private Square selectedPieceNewLocation;
    private ArrayList<Pawn> playersPawns;
    private ArrayList<Pawn> opponentPawns;

   
    
    public Player(gameSetup setup, Color cl, Time time) {
        this.color = cl;
        this.setup = setup;
        this.gameBoard = setup.gameBoard;
        this.opponentPieces = new ArrayList<Piece>();
        this.timeLeft = time;
        this.playerPieces = new ArrayList<Piece>();      
        this.inCheck = false;
    }
    
    public void selectPiece(Piece piece) {
        if (playerPieces.contains(piece)) {
            currentlySelectedPiece = piece;
        }
    }
    
    public void selectedPieceNewLocation(Square square) {
        if (currentlySelectedPiece.possibleMoves().contains(square)) {
            selectedPieceNewLocation = square;
        }
    }
    
    public void movePiece() {      
            if (currentlySelectedPiece == this.king && checkForCastle() == true) { 
                if (selectedPieceNewLocation == gameBoard.getTile(this.king.getcurrentPosition().getRow(), this.king.getcurrentPosition().getCol() + 2)) { 
                    executeShortCastle(); 
                    //gameBoard.printBoard();
                }
                if (selectedPieceNewLocation == gameBoard.getTile(this.king.getcurrentPosition().getRow(), this.king.getcurrentPosition().getCol() - 2) ) {
                    executeLongCastle(); 
                    //gameBoard.printBoard();
                }
            }
            else {
                currentlySelectedPiece.move(selectedPieceNewLocation);  
            }       
    }

    public String toString() {
        if (this.color == Color.WHITE) {
            return "White Player";
        }
        else {
            return "Black Player";
        }
    }
    
    public void checkingForCheck() {
        Square kingLocation = this.king.getcurrentPosition();
        opponentPossibleMoves = new ArrayList<Square>();
     
        for (Piece p : opponentPieces) {
            opponentPossibleMoves.addAll(p.possibleMoves());
        }
        if (opponentPossibleMoves.contains(kingLocation)) {
            this.inCheck = true;
        }
        else {
            this.inCheck = false;
        }
    }
    
    public boolean isInCheck() {
        //return if the current player is in check
        return this.inCheck;
    }    
    
    public boolean testIfMoveIsValid(Piece pieceToMove , Square squareToMove) {
        //stops the current player from putting themselves into check
        //if true allow the move
        //if false stops the move from occurring
        
        // if king is in check, it can capture a piece to end check but put itself into check again
        Square currentLocation = pieceToMove.getcurrentPosition();
        if (squareToMove.containsPiece()) {
            Piece pieceInSquareToMove = squareToMove.selectPiece();
            if (this.inCheck) {
                if (pieceInSquareToMove.possibleMoves().contains(king.getcurrentPosition())) {
                    //pieceInSquareToMove.setcurrentPosition();
                    pieceToMove.setcurrentPosition(squareToMove);                  
                    checkingForCheck();
                    if (this.inCheck) {
                        pieceToMove.setcurrentPosition(currentLocation);  
                        pieceInSquareToMove.setcurrentPosition(squareToMove);
                        return false;
                    }
                    else {
                        pieceToMove.setcurrentPosition(currentLocation);  
                        pieceInSquareToMove.setcurrentPosition(squareToMove);
                        return true;
                    }
                }
                pieceToMove.setcurrentPosition(currentLocation);
                pieceInSquareToMove.setcurrentPosition(squareToMove);
                return false; 
            }
            else {
                pieceToMove.setcurrentPosition(currentLocation);
                pieceInSquareToMove.setcurrentPosition(squareToMove);
                return true;
            }
        }
        pieceToMove.setcurrentPosition(squareToMove);
        checkingForCheck();        
        if (this.inCheck) {
            pieceToMove.setcurrentPosition(currentLocation);
            this.inCheck = false;
            return false; 
        }
        else {
            pieceToMove.setcurrentPosition(currentLocation);
            return true;
        }
    }
    
    public boolean checkForCastle() {
        //king and rook move count must be 0
        //king must not be currently in check or end up in check after the castle move
        
        if (this.king.getMoveCount() == 0 && this.shortRook.getMoveCount() == 0 && this.color == Color.WHITE && !this.inCheck && gameBoard.getTile(7, 7) == this.shortRook.getcurrentPosition()) {
            if (!gameBoard.getTile(7, 6).containsPiece() && !gameBoard.getTile(7, 5).containsPiece()) {
                if (testIfMoveIsValid(this.king, gameBoard.getTile(7, 6))) { 
                    this.king.possibleMoves.add(gameBoard.getTile(7, 6));
                    System.out.println(this.king.possibleMoves);
                }
                return true;
            }            
        }
        if (this.king.getMoveCount() == 0 && this.shortRook.getMoveCount() == 0 && this.color == Color.BLACK && !this.inCheck) {
            if (!gameBoard.getTile(0, 6).containsPiece() && !gameBoard.getTile(0, 5).containsPiece()) {
                if (testIfMoveIsValid(this.king, gameBoard.getTile(0, 6))) { 
                    this.king.possibleMoves.add(gameBoard.getTile(0, 6));
                    System.out.println(this.king.possibleMoves);
                }
                return true;
            }
        }
        if (this.king.getMoveCount() == 0 && this.shortRook.getMoveCount() == 0 && this.color == Color.WHITE && !this.inCheck) {
            if (!gameBoard.getTile(7, 1).containsPiece() && !gameBoard.getTile(7, 2).containsPiece() && !gameBoard.getTile(7, 3).containsPiece()) {
                if (testIfMoveIsValid(this.king, gameBoard.getTile(7, 2))) { 
                    this.king.possibleMoves.add(gameBoard.getTile(7, 2));
                    System.out.println(this.king.possibleMoves);
                }
                return true;
            }
        }
        if (this.king.getMoveCount() == 0 && this.shortRook.getMoveCount() == 0 && this.color == Color.BLACK && !this.inCheck) {
            if (!gameBoard.getTile(0, 1).containsPiece() && !gameBoard.getTile(0, 2).containsPiece() && !gameBoard.getTile(0, 3).containsPiece()) {
                if (testIfMoveIsValid(this.king, gameBoard.getTile(0, 2))) { 
                    this.king.possibleMoves.add(gameBoard.getTile(0, 2));
                    System.out.println(this.king.possibleMoves);
                }
                return true;
            }
        }
        return false;
    }
    
    public void executeShortCastle() {
        Square newRookLocation = gameBoard.getTile(this.shortRook.getcurrentPosition().getRow(), (this.shortRook.getcurrentPosition().getCol() - 2));
        currentlySelectedPiece = this.shortRook;
        selectedPieceNewLocation = newRookLocation;
        movePiece();
        this.king.move(gameBoard.getTile(this.king.getcurrentPosition().getRow(), this.king.getcurrentPosition().getCol() + 2));
    }
    
    public void executeLongCastle() {
        Square newRookLocation = gameBoard.getTile(this.longRook.getcurrentPosition().getRow(), (this.longRook.getcurrentPosition().getCol() + 3));
        currentlySelectedPiece = this.longRook;
        selectedPieceNewLocation = newRookLocation;
        movePiece();
        this.king.move(gameBoard.getTile(this.king.getcurrentPosition().getRow(), this.king.getcurrentPosition().getCol() - 2));
    }
    
    public void checkForEnPassant() {
        System.out.println(this);
        for (Pawn pawn : playersPawns) {
            pawn.doubleMoved = false;
            if (gameBoard.isLegalMove(pawn.getcurrentPosition().getRow(), pawn.getcurrentPosition().getCol() - 1)) {
                if (pawn.adjacentLeft().containsPawn()) {
                    Pawn piece = (Pawn) pawn.adjacentLeft().selectPiece();
                    if (opponentPawns.contains(piece) && piece.doubleMoved == true) {
                        System.out.println("Left contains Piece " + pawn.belongsToPlayer());
                        pawn.addPossibleMove(piece.squareBehindCurrentLocation() , piece.getcurrentPosition());
                        System.out.println(piece.squareBehindCurrentLocation());
                    }
                }
            }
            if (gameBoard.isLegalMove(pawn.getcurrentPosition().getRow(), pawn.getcurrentPosition().getCol() + 1)) {
                if (pawn.adjacentRight().containsPawn()) {
                    Pawn piece = (Pawn) pawn.adjacentRight().selectPiece();
                    if (opponentPawns.contains(piece) && piece.doubleMoved == true) {
                        System.out.println("Right contains Piece " + pawn.belongsToPlayer());
                        pawn.addPossibleMove(piece.squareBehindCurrentLocation() , piece.getcurrentPosition());
                        System.out.println(piece.squareBehindCurrentLocation());
                    }
                }
            }
        }
    }
    
    public void assignPieces(ArrayList<Piece> pieces, ArrayList<Pawn> pawnPieces) {
        this.playerPieces = pieces;
        this.playersPawns = pawnPieces;
        this.king = (King) pieces.get(pieces.size() -1); 
        this.shortRook = (Rook) pieces.get(9);
        this.longRook = (Rook) pieces.get(8);
    }
  
    public void assignOppoenentPieces(ArrayList<Piece> pieces, ArrayList<Pawn> pawnPieces) {
        this.opponentPieces = pieces;
        this.opponentKing = (King) pieces.get(pieces.size() -1);
        this.opponentPawns = pawnPieces;
    }
    
    public void setTurn(boolean bool) {
        isPlayersTurn = bool;
    }
    
    public boolean getTurn() {
        return this.isPlayersTurn;
    }
    
    public void startPlayersTurn() {
        for (Piece piece : this.playerPieces) {
            piece.clearPossibleMoves();
        }
        checkingForCheck();
        checkForEnPassant();          
    }
    
    public Color getPlayerColor() {
        return this.color;
    }
    public ArrayList<Piece> getPlayersPieces(){
        return playerPieces;
    }  
    public ArrayList<Pawn> getPlayersPawns(){
        return playersPawns;
    }
}
