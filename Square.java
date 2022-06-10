package gameSetup;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import pieces.Piece;

public class Square {
    private Color color;
    private boolean containsPiece;
    private Piece pieceType;
    private int row;
    private int col;
    private Rectangle rect;
    
    public Square(int location, Color color) {
        this.color = color;
        this.row = location / 8;
        this.col = location % 8;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void addPiece(Piece piece) {
        containsPiece = true;
        pieceType = piece;
    }
    
    public void removePiece() {
        containsPiece = false;
        pieceType = null;
    }
    
    public Piece selectPiece() {
        return pieceType;
    }
    
    public boolean containsPiece() {
        return containsPiece;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public Rectangle createRect(int x , int y, int height, int width) {
        this.rect = new Rectangle();
        rect.x = x;
        rect.y = y;
        rect.height = height;
        rect.width = width;
        return rect;      
    }
    
    public boolean isARect (int x, int y) {
        Point point = new Point(x,y);
        if (this.rect.contains(point)){
            return true;
        }
        return false;       
    }
    
    public Rectangle getRect() {
        return rect;
    }
    
    public String toString() {
        String row = Integer.toString(this.row);
        String col = Integer.toString(this.col);
        return  "(" + row + " : " + col + ")";
    }
    
}
