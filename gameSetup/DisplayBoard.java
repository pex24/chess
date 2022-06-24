package gameSetup;

import java.awt.EventQueue;
import java.awt.Graphics;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;


import javax.swing.JFrame;
import javax.swing.JPanel;

import pieces.Piece;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class DisplayBoard extends JPanel{

    private JFrame screen;
    private JPanel board;
    /**
     * Launch the application.
     */
    public void run() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DisplayBoard window = new DisplayBoard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     * @param gameBoard2 
     */
    public DisplayBoard() {
        gameSetup setup = new gameSetup();        
        this.screen = new JFrame("Chess");
        this.screen.setSize(700,700);
        this.screen.setVisible(true);                   
        this.screen.setDefaultCloseOperation(3);
        
        drawSquaresAndPieces ds = new drawSquaresAndPieces(setup);
        this.screen.add(ds); 
  
        PiecesDragAndDropListener listener = new PiecesDragAndDropListener(ds, setup);
        ds.addMouseListener(listener);
        ds.addMouseMotionListener(listener);
        
    }
    
    static class drawSquaresAndPieces extends JPanel {
        private Board gameBoard;
        private gameSetup setup;
        private ArrayList<Piece> piecesToBeDisplayed = new ArrayList<Piece>();
        private final int squareSize = 75; 
        
        drawSquaresAndPieces(gameSetup setup){
            this.setup = setup;
            this.gameBoard = setup.gameBoard;
            for (Piece BP : setup.player1.playerPieces) {
                piecesToBeDisplayed.add(BP);
            }
            for (Piece WP : setup.player2.playerPieces) {
                piecesToBeDisplayed.add(WP);
                
            }
        }        
        
        public void updatePieces(Board gameBoard) {
            this.piecesToBeDisplayed.clear();
            this.piecesToBeDisplayed.addAll(setup.player1.playerPieces);
            this.piecesToBeDisplayed.addAll(setup.player2.playerPieces);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);        
            setDoubleBuffered(true);
            updatePieces(gameBoard);
            //gameBoard.printBoard();
            for (Square[] row : gameBoard.tiles) {
                for (Square square : row) {
                    g.setColor(square.getColor());
                    g.fillRect(square.getRow()*squareSize + 40, square.getCol()*squareSize + 40, squareSize, squareSize);                 
                }
            }
            for (Piece piece : piecesToBeDisplayed) {
                try {
                    piece.drawPiece(g);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        
        public void printBoard() {
            for (int row = 0 ; row < gameBoard.rowSize; row++) {
                for (int col = 0; col < gameBoard.colSize; col++) {
                    System.out.print(gameBoard.tiles[row][col]+ "  ");                  
                }
                System.out.println();
            }
        }  
    }
}
