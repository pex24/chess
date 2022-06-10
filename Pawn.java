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

public class Pawn implements Piece{
    Board gameBoard;
    Player player;
    Color color;
    int moveCount;
    Square currentSquare;
    ArrayList<Square> possibleMoves = new ArrayList<Square>();
    ArrayList<Square> possibleAttacks = new ArrayList<Square>();
    double x;
    double y;
    
    public Pawn(Square startingPosition, Player player, Board gameBoard) {
        this.player = player;
        this.color = player.getPlayerColor();
        this.currentSquare = startingPosition;
        this.gameBoard = gameBoard;
        gameBoard.getTile(startingPosition).addPiece(this);
        this.x = currentSquare.getRect().getCenterX() - 32;
        this.y = currentSquare.getRect().getCenterY() - 30;
        this.moveCount = 0;
    }
    
    @Override
    public void move(Square square) {
        if (possibleAttacks.contains(square)) {
            capturePiece(square);
        }
        if (possibleMoves.contains(square)) {
            Square curr = getcurrentPosition();
            curr.removePiece();
            this.currentSquare = square;
            this.gameBoard.getTile(square).addPiece(this);
            moveCount ++;
        }      
    }

    @Override
    public ArrayList<Square> possibleMoves() {
        // Checks to see if the move is within board space
        possibleMoves.clear();

        int moveMod = 0;
        if (this.color == Color.BLACK) {
            moveMod = 1;
        }
        if (this.color == Color.WHITE) {
            moveMod = -1;
        }
        
        int move = currentSquare.getRow() + moveMod;
        if (move >= 0 && move < 8) {
            Square tile = gameBoard.getTile(move, currentSquare.getCol());
            if (!tile.containsPiece()) {
                possibleMoves.add(tile);
            }
            else {
                //System.out.println(tile + " :  contains Piece" );
            }
        }
        
        if (moveCount == 0) {
            int move2 = currentSquare.getRow() + (moveMod *2);
            if (move2 > 0 && move2 < 8) {
                Square tile = gameBoard.getTile(move2, currentSquare.getCol());
                possibleMoves.add(tile);
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
    
    public void capturePiece(Square squareUnderAttack) {
        //Captures piece if both possibleAttacks and square contain piece
        Square currentSquare = this.currentSquare;
        
        this.currentSquare = squareUnderAttack;
        if (this.color == Color.WHITE) {
            gameBoard.blackPieces.remove(squareUnderAttack.selectPiece());
        }
        if (this.color == Color.BLACK) {
            gameBoard.whitePieces.remove(squareUnderAttack.selectPiece());
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
    
    public void removePiece(Graphics g) throws IOException {

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
}
