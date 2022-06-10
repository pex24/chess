package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import gameSetup.Player;
import gameSetup.Square;

public interface Piece {   
    public Player belongsToPlayer();
    public int getMoveCount();
    public Color getPieceColor();
    public void move(Square square);

    public ArrayList<Square> possibleMoves();
    public Square getcurrentPosition();
    public void capturePiece(Square square);
    public String toString();

    public void drawPiece(Graphics g) throws IOException;

    public Double getX();
    public Double getY();
    public void setX(Double x);
    public void setY(Double y);
    public void showPlayer();
    
    
}
