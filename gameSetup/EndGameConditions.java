package gameSetup;

import java.util.ArrayList;

import pieces.Piece;

public class EndGameConditions {
    public boolean checkMate;

    public boolean checkIfGameIsOver() {
        return checkMate;
    }
    public void checkMate(gameSetup setup) {
        if (setup.currentPlayer.inCheck) {
            System.out.println(setup.getCurrentPlayer());
            checkMate = true;
            System.out.println("checkMate == true");
        }
    }
}
