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

public class Queen implements Piece {
    gameSetup setup;
    Board gameBoard;
    Player player;
    Color color;
    int moveCount;
    Square currentSquare;
    ArrayList<Square> possibleMoves = new ArrayList<Square>();
    ArrayList<Square> possibleAttacks = new ArrayList<Square>();
    double x;
    double y;
    
    public Queen(Square startingPosition, Player player, gameSetup setup) {
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
    public ArrayList<Square> possibleMoves() {
        possibleAttacks = new ArrayList<Square>();
        possibleMoves = new ArrayList<Square>();

        ArrayList<ArrayList> allPossibleSquares = new ArrayList<ArrayList>();
        ArrayList<ArrayList> colSquares = gameBoard.getCol(currentSquare);
        ArrayList<ArrayList> rowSquares = gameBoard.getRow(currentSquare);
        ArrayList<ArrayList> diagonalSquare = gameBoard.getDiagonals(currentSquare);
        allPossibleSquares.addAll(rowSquares);
        allPossibleSquares.addAll(colSquares);
        allPossibleSquares.addAll(diagonalSquare);        
        
        for (ArrayList<Square> arr : allPossibleSquares) {
            for (Square square : arr) {
                if (gameBoard.isLegalMove(square.getRow(),square.getCol()) && currentSquare != square) {
                    if (!gameBoard.getTile(square).containsPiece()) {
                        possibleMoves.add(square); 
                    }
                    else {
                        if (gameBoard.getTile(square).selectPiece().belongsToPlayer() != this.player) {
                            possibleMoves.add(square);
                            possibleAttacks.add(square);
                        }
                       break;
                    }                                       
                }
            }
        }      
        return possibleMoves;
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
    
    @Override
    public String toString() {
        if (this.color == Color.WHITE) {
            return "WQueen";
        }
        else {
            return "BQueen";
        }
    }

    @Override
    public Color getPieceColor() {
        return this.color;
    }

    @Override
    public Player belongsToPlayer() {
        return this.player;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public void drawPiece(Graphics g) throws IOException {
        if (this.color == Color.BLACK) {
            Image img = ImageIO.read(new FileInputStream("pieceImages/blackQueen.png"));
            g.drawImage(img, (int)this.x, (int)this.y, null);    
        }
        if (this.color == Color.WHITE) {
            Image img = ImageIO.read(new FileInputStream("pieceImages/whiteQueen.png"));
            g.drawImage(img, (int)this.x, (int)this.y, null); 
        }        
    }

    @Override
    public Double getX() {
        return this.x;
    }

    @Override
    public Double getY() {
        return this.y;
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
    public void showPlayer() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void clearPossibleMoves() {
        possibleMoves.clear();
    }
}
