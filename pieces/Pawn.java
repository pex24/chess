package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


import gameSetup.Board;
import gameSetup.Player;
import gameSetup.Square;
import gameSetup.gameSetup;

public class Pawn implements Piece{

    private gameSetup setup;
    Board gameBoard;
    Player player;
    Color color;
    int moveCount;
    Square currentSquare;
    ArrayList<Square> possibleMoves = new ArrayList<Square>();
    ArrayList<Square> possibleAttacks = new ArrayList<Square>();
    ArrayList<Square> possibleEnPassant = new ArrayList<Square>();
    Square doubleMove;
    double x;
    double y;
    public boolean doubleMoved;
    
    public Pawn(Square startingPosition, Player player, gameSetup setup) {
        this.setup = setup;
        this.player = player;
        this.color = player.getPlayerColor();
        this.currentSquare = startingPosition;
        this.gameBoard = setup.gameBoard;
        gameBoard.getTile(startingPosition).addPiece(this);
        this.x = currentSquare.getRect().getCenterX() - 32;
        this.y = currentSquare.getRect().getCenterY() - 30;
        this.moveCount = 0;
    }
    
    @Override
    public void move(Square square) {
        if (!possibleEnPassant.isEmpty()) {
            if (possibleEnPassant.get(0) == (square)) {
                System.out.println("possible");
                capturePiece(possibleEnPassant.get(1));
            } 
        }
        if (possibleAttacks.contains(square)) {
            capturePiece(square);
        }
            Square curr = getcurrentPosition();
            curr.removePiece();
            this.currentSquare = square;
            this.gameBoard.getTile(square).addPiece(this);
            moveCount ++;
            if (square == doubleMove) {
                doubleMoved = true;
            }
    }

    @Override
    public ArrayList<Square> possibleMoves() {
        // Checks to see if the move is within board space
        int moveMod = 0;
        if (this.color == Color.BLACK) {
            moveMod = 1;
        }
        if (this.color == Color.WHITE) {
            moveMod = -1;
        }
        
        if (moveCount == 0) {
            int move2 = currentSquare.getRow() + (moveMod *2);
            if (move2 > 0 && move2 < 8) {
                Square tile = gameBoard.getTile(move2, currentSquare.getCol());
                if (!tile.containsPiece()) {
                    possibleMoves.add(tile);
                    doubleMove = tile;
                }
            }
        }
        
        int move = currentSquare.getRow() + moveMod;
        if (move >= 0 && move < 8) {
            Square tile = gameBoard.getTile(move, currentSquare.getCol());
            if (!tile.containsPiece()) {
                possibleMoves.add(tile);
            }
            else {
            }
        }
        
        if (this.color == Color.WHITE) {
            if (gameBoard.isLegalMove(currentSquare.getRow() - 1, currentSquare.getCol() - 1)) {
                Square whiteAttackLeft = gameBoard.getTile(currentSquare.getRow() - 1, currentSquare.getCol() - 1);
                if (whiteAttackLeft.containsPiece()) {
                    if (whiteAttackLeft.selectPiece().getPieceColor() != this.color) {
                        possibleMoves.add(whiteAttackLeft);
                        possibleAttacks.add(whiteAttackLeft);
                    }
                }
            }           
            if (gameBoard.isLegalMove(currentSquare.getRow() - 1, currentSquare.getCol() + 1)) {
                Square whiteAttackRight = gameBoard.getTile(currentSquare.getRow() - 1, currentSquare.getCol() + 1);
                if (whiteAttackRight.containsPiece()) {
                    if (whiteAttackRight.selectPiece().getPieceColor() != this.color) {
                        possibleMoves.add(whiteAttackRight);
                        possibleAttacks.add(whiteAttackRight);
                    }
                }
            }
        }
        
        if (this.color == Color.BLACK) {
            if (gameBoard.isLegalMove(currentSquare.getRow() + 1, currentSquare.getCol() + 1)){
                Square blackAttackLeft = gameBoard.getTile(currentSquare.getRow() + 1 , currentSquare.getCol() + 1);
                if (blackAttackLeft.containsPiece()) {
                    if (blackAttackLeft.selectPiece().getPieceColor() != this.color) {
                        possibleMoves.add(blackAttackLeft);
                        possibleAttacks.add(blackAttackLeft);
                    }
                }
            }
            if (gameBoard.isLegalMove(currentSquare.getRow() + 1, currentSquare.getCol() - 1)){
                Square blackAttackRight = gameBoard.getTile(currentSquare.getRow() + 1 , currentSquare.getCol() - 1);
                if (blackAttackRight.containsPiece()) {
                    if (blackAttackRight.selectPiece().getPieceColor() != this.color) {
                        possibleMoves.add(blackAttackRight);
                        possibleAttacks.add(blackAttackRight);
                    }
                }
            }
        }
        return possibleMoves;
    }
    
    public void addPossibleMove(Square squareToMove, Square squareToAttack) {
        possibleMoves.add(squareToMove);
        possibleEnPassant.add(squareToMove);
        possibleEnPassant.add(squareToAttack);
    }
    
    public void capturePiece(Square squareUnderAttack) {
        //Captures piece if both possibleAttacks and square contain piece
        Square currentSquare = this.currentSquare;
        
        this.currentSquare = squareUnderAttack;
        if (this.player == setup.player1) {
             setup.player2.getPlayersPieces().remove(squareUnderAttack.selectPiece());
        }
        if (this.player == setup.player2) {
            setup.player1.getPlayersPieces().remove(squareUnderAttack.selectPiece());
        }
        
        squareUnderAttack.removePiece();        
        currentSquare.removePiece();
        squareUnderAttack.addPiece(this);
        this.moveCount ++;
        possibleAttacks.clear();
    }
  
    @Override
    public Square getcurrentPosition() {
        return currentSquare;
    }
    
    @Override
    public void setcurrentPosition(Square square) {
        //Used for testing if a move will put this player into check
        //For moving the piece in game use move() function
        currentSquare.removePiece();
        this.currentSquare = square;
        currentSquare.addPiece(this);
    }
    
    public Square adjacentLeft(){
        if (gameBoard.isLegalMove(currentSquare.getRow(), currentSquare.getCol() - 1)) {
            return gameBoard.getTile(currentSquare.getRow(), currentSquare.getCol() - 1);
        }
        else {
            return null;
        }
    }
    public Square adjacentRight() {
        if (gameBoard.isLegalMove(currentSquare.getRow(), currentSquare.getCol() + 1)) {
            return gameBoard.getTile(currentSquare.getRow(), currentSquare.getCol() + 1);
        }
        else {
            return null;
        }
    }
    
    public Square squareBehindCurrentLocation() {
        if (this.color == Color.BLACK) {
            Square behind = gameBoard.getTile(this.currentSquare.getRow() - 1, this.currentSquare.getCol());
            return behind;
                    
        }
        if (this.color == Color.WHITE) {
            Square behind = gameBoard.getTile(this.currentSquare.getRow() + 1, this.currentSquare.getCol());
            return behind;
        }
        return currentSquare;
        
    }
    
    public String toString() {
        if (this.color == Color.WHITE) {
            return "WPawn";
        }
        else {
            return "BPawn";
        }
    }

    @Override
    public Color getPieceColor() {
        return this.color;
    }
    
    public void drawPiece(Graphics g) throws IOException {
        if (this.color == Color.BLACK) {
            Image img = ImageIO.read(new FileInputStream("pieceImages/blackPawn.png"));
            g.drawImage(img, (int)this.x, (int)this.y, null);    
        }
        if (this.color == Color.WHITE) {
            Image img = ImageIO.read(new FileInputStream("pieceImages/whitePawn.png"));
            g.drawImage(img, (int)this.x, (int)this.y, null); 
        }
    }
 
    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public void setY(Double y) {
        this.y = y;        
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    public void showPlayer() {
        System.out.println(this.player);
        System.out.println(this);   
    }

    @Override
    public Player belongsToPlayer() {
        return this.player;
    }
    @Override
    public void clearPossibleMoves() {
        possibleMoves.clear();
    }
}
