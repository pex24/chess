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

public class King implements Piece{
    gameSetup setup;
    Board gameBoard;
    Player player;
    Color color;
    int moveCount;
    Square currentSquare;
    public ArrayList<Square> possibleMoves = new ArrayList<Square>();
    ArrayList<Square> possibleAttacks = new ArrayList<Square>();
    ArrayList<Square> checkingAttacks = new ArrayList<Square>();
    double x;
    double y;
    boolean hasCastled;
    
    public King(Square startingPosition, Player player, gameSetup setup) {
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
        possibleMoves = new ArrayList<Square>();
        int[] row = {-1, 0, 1};
        int[] col = {-1, 0, 1};

        this.player.checkForCastle();
        
        for (int r : row) {
            for (int c : col) {
                if (gameBoard.isLegalMove(currentSquare.getRow() + r , currentSquare.getCol() + c)){
                    Square possibleSquare = gameBoard.getTile(currentSquare.getRow() + r, currentSquare.getCol() + c);
                    if (!possibleSquare.containsPiece()) {
                        possibleMoves.add(possibleSquare);
                    }
                    else {
                        if (possibleSquare.selectPiece().belongsToPlayer() != this.player) {
                            possibleMoves.add(possibleSquare);
                            possibleAttacks.add(possibleSquare);
                        }
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
    public Color getPieceColor() {
        return this.color;
    }
    
    @Override
    public String toString() {
        if (this.color == Color.WHITE) {
            return "WKing";
        }
        else {
            return "BKing";
        }
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
            Image img = ImageIO.read(new FileInputStream("pieceImages/blackKing.png"));
            g.drawImage(img, (int)this.x, (int)this.y, null);    
        }
        if (this.color == Color.WHITE) {
            Image img = ImageIO.read(new FileInputStream("pieceImages/whiteKing.png"));
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
    public void showPlayer() {
    }

    @Override
    public void clearPossibleMoves() {
        possibleMoves.clear();
    }
}
