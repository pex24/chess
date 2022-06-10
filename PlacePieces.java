package gameSetup;

import java.awt.Color;
import java.util.ArrayList;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class PlacePieces {
    gameSetup setup;
    Board gameBoard;
    private ArrayList<Piece> blackPieceList;
    private ArrayList<Piece> whitePieceList;
    
    public PlacePieces(gameSetup setup) {
        this.setup = setup;
        this.gameBoard = setup.gameBoard;
        blackPieceList = new ArrayList<Piece>();
        whitePieceList = new ArrayList<Piece>();
        placePawns();
        placeRooks();
        placeKnights();
        placeBishops();
        placeQueens();
        placeKings();
    }
    

    private void placePawns() {
        for (int col = 0; col < gameBoard.colSize; col++) {
            int row = 1;
            Square tile = gameBoard.getTile(row , col);
            Pawn p = new Pawn(tile, setup.player2, gameBoard);
            blackPieceList.add(p);
        }
        
        for (int col = 0; col < gameBoard.colSize; col++) {
            int row = 6;
            Square tile = gameBoard.getTile(row, col);
            Pawn p = new Pawn(tile, setup.player1, gameBoard);        
            whitePieceList.add(p);
        }
    }
    
    private void placeRooks() {
        Square tile0 = gameBoard.getTile(0, 0);
        Rook blackRook0 = new Rook(tile0, setup.player2 , gameBoard);
        blackPieceList.add(blackRook0);
        Square tile1 = gameBoard.getTile(0, 7);
        Rook blackRook1 = new Rook(tile1 , setup.player2 , gameBoard);
        blackPieceList.add(blackRook1);
        
        Square tile2 = gameBoard.getTile(7,0);
        Rook whiteRook0 = new Rook(tile2, setup.player1, gameBoard);
        whitePieceList.add(whiteRook0);
        Square tile3 = gameBoard.getTile(7,7);
        Rook whiteRook1 = new Rook(tile3, setup.player1, gameBoard);
        whitePieceList.add(whiteRook1);
    }
    
    private void placeKnights() {
        Square tile0 = gameBoard.getTile(0, 1);
        Knight blackKnight0 = new Knight(tile0, Color.BLACK, gameBoard);
        blackPieceList.add(blackKnight0);
        Square tile1 = gameBoard.getTile(0, 6);
        Knight blackKnight1 = new Knight(tile1 , Color.BLACK, gameBoard);
        blackPieceList.add(blackKnight1);
        
        Square tile2 = gameBoard.getTile(7, 1);
        Knight whiteKnight0 = new Knight(tile2, Color.WHITE, gameBoard);
        whitePieceList.add(whiteKnight0);
        Square tile3 = gameBoard.getTile(7, 6);
        Knight whiteKnight1 = new Knight(tile3, Color.WHITE, gameBoard);
        whitePieceList.add(whiteKnight1);
    }
    
    private void placeBishops() {
        Square tile0 = gameBoard.getTile(0, 2);
        Bishop blackBishop0 = new Bishop(tile0, Color.BLACK, gameBoard);
        blackPieceList.add(blackBishop0);
        Square tile1 = gameBoard.getTile(0, 5);
        Bishop blackBishop1 = new Bishop(tile1 , Color.BLACK, gameBoard);
        blackPieceList.add(blackBishop1);
        
        Square tile2 = gameBoard.getTile(7, 2);
        Bishop whiteBishopt0 = new Bishop(tile2, Color.WHITE, gameBoard);
        whitePieceList.add(whiteBishopt0);
        Square tile3 = gameBoard.getTile(7, 5);
        Bishop whiteBishop1 = new Bishop(tile3, Color.WHITE, gameBoard);    
        whitePieceList.add(whiteBishop1);
    }
    
    private void placeQueens() {
        Square tile0 = gameBoard.getTile(0, 3);
        Queen blackQueen = new Queen (tile0, Color.BLACK, gameBoard);
        blackPieceList.add(blackQueen);
        Square tile1 = gameBoard.getTile(7, 3);
        Queen whiteQueen = new Queen (tile1, Color.WHITE, gameBoard);
        whitePieceList.add(whiteQueen);
    }
    
    private void placeKings() {
        Square tile0 = gameBoard.getTile(0, 4);
        King blackKing = new King (tile0, setup.player2, gameBoard);
        blackPieceList.add(blackKing);
        Square tile1 = gameBoard.getTile(7, 4);
        King whiteKing = new King (tile1, setup.player1, gameBoard);
        whitePieceList.add(whiteKing);
        
    }


    public ArrayList<Piece> getBlackPieces() {
        return blackPieceList;
    }
    
    public ArrayList<Piece> getWhitePieces() {
        return whitePieceList;
    }
}

