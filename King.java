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

public class King implements Piece{
    Board gameBoard;
    Player player;
    Color color;
    int moveCount;
    Square currentSquare;
    ArrayList<Square> possibleMoves = new ArrayList<Square>();
    ArrayList<Square> possibleAttacks = new ArrayList<Square>();
    double x;
    double y;
    
    public King(Square startingPosition, Player player, Board gameBoard) {
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
        System.out.println("preMove : " + currentSquare);
        if (possibleAttacks.contains(square)) {
            capturePiece(square);
        }
        if (possibleMoves.contains(square)) {
            Square curr = getcurrentPosition();
            curr.removePiece();
            this.currentSquare = square;
            this.gameBoard.getTile(square).addPiece(this);
            moveCount ++;
            System.out.println("postMove : " +currentSquare);
        }  
    }

    @Override
    public ArrayList<Square> possibleMoves() {
        if (this.player.inCheck()) {
            System.out.println("CHECK");           
        }
        possibleMoves = new ArrayList<Square>();
        int[] row = {-1, 0, 1};
        int[] col = {-1, 0, 1};
        
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
    public void capturePiece(Square square) {
        //Captures piece if both possiblAttacks and square contain piece
        Square currentSquare = this.currentSquare;
        
        this.currentSquare = square;
        if (this.color == Color.WHITE) {
            gameBoard.blackPieces.remove(square.selectPiece());
        }
        if (this.color == Color.BLACK) {
            gameBoard.whitePieces.remove(square.selectPiece());
        }
        square.removePiece();
        currentSquare.removePiece();
        square.addPiece(this);
        this.moveCount ++;
        possibleAttacks.clear();
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
        System.out.println(this.player);
        System.out.println(this);  
    }

}
