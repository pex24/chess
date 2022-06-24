package gameSetup;

import java.awt.Color;
import java.util.ArrayList;
import pieces.Piece;


public class Board {
    public final static int boardSize = 64;
    public int rowSize = 8;
    public int colSize = 8;
    public Square[][] tiles = new Square[8][8];
    public ArrayList<Piece> blackPieces;
    public ArrayList<Piece> whitePieces;
    
    private final int squareSize = 75;  //used for drawing the squares
    
    public Board(){
        int count = 0;
        int countForColor = 0;
        for (int row = 0; row < rowSize; row ++) {
            for (int col = 0; col < colSize; col++) {
                if (countForColor % 2 == 1) {
                    tiles[row][col] = new Square(count, Color.LIGHT_GRAY);
                } else {
                    tiles[row][col] = new Square(count, Color.WHITE);
                }
                if (col != 7) {
                    countForColor ++;
                }
                count++;              
                tiles[row][col].createRect(tiles[row][col].getCol() *squareSize + 40, tiles[row][col].getRow() * squareSize + 40, squareSize, squareSize);
            }
        }
    }
    
    public void placePiecesOnBoard(gameSetup gs) {
        PlacePieces p = new PlacePieces(gs);
        blackPieces = p.getBlackPieces();
        whitePieces = p.getWhitePieces();
    }
    
    public static ArrayList<Integer> getEdges() {
        ArrayList<Integer> edges = new ArrayList<Integer>();
        for (int i = 0; i < 8 ; i ++) {
            edges.add(i);
        }
        for (int i = 56; i < 64 ; i ++) {
            edges.add(i);
        }
        for (int i = 0; i < 64; i+=8) {
            edges.add(i);
        }
        for (int i = 7; i < 64; i +=8) {
            edges.add(i);
        }
        for (int i : edges) {
            System.out.println(i);
        }
        return edges;
    }
     
    public ArrayList<ArrayList> getRow(Square square){
        ArrayList<ArrayList> rowList = new ArrayList<ArrayList>();
        ArrayList<Square> upList = new ArrayList<Square>();
        ArrayList<Square> downList = new ArrayList<Square>();
        for (int col = square.getCol(); col < 8 ; col++) {
            upList.add(getTile(square.getRow(), col));
        }
        for (int col = square.getCol(); col >= 0 ; col--) {
            downList.add(getTile(square.getRow(), col));
        }
        rowList.add(upList);
        rowList.add(downList);
        return rowList;
    }
    
    public ArrayList<ArrayList> getCol(Square square){
        ArrayList<ArrayList> colList = new ArrayList<ArrayList>();
        ArrayList<Square> upList = new ArrayList<Square>();
        ArrayList<Square> downList = new ArrayList<Square>();
        
        for (int row = square.getRow() + 1; row < 8; row ++) {
            upList.add(getTile(row,square.getCol()));
        }
        for (int row = square.getRow() - 1; row >= 0; row --) {
            downList.add(getTile(row,square.getCol()));
        }
        colList.add(upList);
        colList.add(downList);
        
        return colList;
    }
    
    public ArrayList<ArrayList> getDiagonals(Square curr){
        ArrayList<ArrayList> diagonals = new ArrayList<ArrayList>();
        ArrayList<Square> diagonal0 = new ArrayList<Square>();
        ArrayList<Square> diagonal1 = new ArrayList<Square>();
        ArrayList<Square> diagonal2 = new ArrayList<Square>();
        ArrayList<Square> diagonal3 = new ArrayList<Square>();
        
        int row = curr.getRow();
        int col = curr.getCol();      
        while (row < 7 && col < 7) {
            row++;
            col++;         
            diagonal0.add(getTile(row,col));
        }
        row = curr.getRow();
        col = curr.getCol();   
        while (row > 0 && col > 0) {
            row--;
            col--;
            Square squ = getTile(row, col);
            if (!diagonals.contains(squ)) {
                diagonal1.add(getTile(row,col));
            }
        }
        
        row = curr.getRow();
        col = curr.getCol();
        while (row < 7 && col > 0) {
            row++;
            col--;
            diagonal2.add(getTile(row,col));
        }
        row = curr.getRow();
        col = curr.getCol();   
        while (row > 0 && col < 7) {
            row--;
            col++;           
            Square squ = getTile(row, col);
            if (!diagonals.contains(squ)) {
                diagonal3.add(getTile(row,col));
            }            
        }
        diagonals.add(diagonal0);
        diagonals.add(diagonal1);
        diagonals.add(diagonal2);
        diagonals.add(diagonal3);
        
        return diagonals;        
    }
    
    public Square getKingLocation(Player player) {
        for (Piece p : blackPieces) {
            if (p.toString() == "BKing"  && player.color == Color.BLACK) {
                //System.out.println(p.getcurrentPosition());
                return p.getcurrentPosition();
            }
        }
        for (Piece p : whitePieces) {
            if (p.toString() == "WKing"  && player.color == Color.WHITE) {
                //System.out.println(p.getcurrentPosition());
                return p.getcurrentPosition();
            }
        }
        return null;
    }
    
    public Square getOppenentKingLocation(Player player) {
        for (Piece p : blackPieces) {
            if (p.toString() == "BKing"  && player.color == Color.WHITE) {
                //System.out.println(p.getcurrentPosition());
                return p.getcurrentPosition();
            }
        }
        for (Piece p : whitePieces) {
            if (p.toString() == "WKing"  && player.color == Color.BLACK) {
                //System.out.println(p.getcurrentPosition());
                return p.getcurrentPosition();
            }
        }
        return null;
    }
    
    public boolean isLegalMove(int row , int col) {
        if (row < 8 && row >= 0 && col < 8 && col >= 0) {
            return true;
        }
        return false;
    }
    
    public Square getTileBasedOnXY(int xCoordinate, int yCoordinate) {
        for (Square[] row : tiles) {
            for (Square s : row) {
                if (s.isARect(xCoordinate, yCoordinate)) {
                    return s;
                }
            }
        }
        return null;
    }
    public Square getTile(int row, int col) {
        return tiles[row][col];
    }
    
    public Square getTile(Square square) {
        int row = square.getRow();
        int col = square.getCol();
        return getTile(row,col);
    }
    
    public void printBoard() {
        for (int row = 0 ; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                //System.out.println(getTile(row,col));
                if (tiles[row][col].containsPiece() == true) {
                    System.out.print(tiles[row][col].selectPiece() + "  ");
                }
                else {
                    System.out.print(" null ");
                }
            }
            System.out.println();
        }
    }
  
    public static void main(String args[]) {
        Board b = new Board();
        Square sq = new Square(9,Color.BLACK);
        ArrayList<ArrayList> col = b.getCol(sq);
        System.out.println(col);
    }
}
