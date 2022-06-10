package gameSetup;

import java.util.ArrayList;

import pieces.Piece;

public class EndGameConditions {

    public boolean checkMate(gameSetup setup) {
        return false;
      
    }
    
    public boolean check(gameSetup setup) {
        Square whiteKingLocation = setup.gameBoard.getKingLocation(setup.player1);
        Square blackKingLocation = setup.gameBoard.getKingLocation(setup.player2);
        
        ArrayList<Square> blackPossibleMoves = new ArrayList<Square>();
        for (Piece p : setup.player2.playerPieces) {
            blackPossibleMoves.addAll(p.possibleMoves());
        }
        ArrayList<Square> whitePossibleMoves = new ArrayList<Square>();
        for (Piece p : setup.player1.playerPieces) {
           whitePossibleMoves.addAll(p.possibleMoves());
        }
        
        if (blackPossibleMoves.contains(whiteKingLocation)) {
            return true;
        }
        if (whitePossibleMoves.contains(blackKingLocation)) {
            return true;
        }
        return false;
    }
}
