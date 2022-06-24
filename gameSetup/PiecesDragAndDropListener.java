package gameSetup;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import gameSetup.DisplayBoard.drawSquaresAndPieces;
import pieces.Piece;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {
    
    private JPanel chessGui;
    private Board gameBoard;
    private gameSetup setup;
    
    private Piece dragPiece;

    public PiecesDragAndDropListener(JPanel panel, gameSetup setup) {
        this.chessGui = panel;
        this.gameBoard = setup.gameBoard;
        this.setup = setup;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(this.dragPiece != null){
            this.dragPiece.setX(e.getPoint().getX() - 30);
            this.dragPiece.setY(e.getPoint().getY() - 30);
            this.chessGui.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Graphics g = chessGui.getGraphics();
        for (Piece p : setup.getCurrentPlayer().getPlayersPieces()) {
            if (mouseOverPiece(p,x,y)) {               
                setup.getCurrentPlayer().selectPiece(p);
                dragPiece = p;              
                g.setColor(Color.CYAN);
                if (dragPiece.possibleMoves() != null) {
                    for (Square tile : dragPiece.possibleMoves()) {
                        Rectangle rect = tile.getRect();
                        g.setColor(Color.CYAN);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(5));
                        g2.draw(new Rectangle(rect.x + 2, rect.y + 2, 72,72));                     
                    }
                break;
                }
            }
        } 
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Square tile = gameBoard.getTileBasedOnXY(x, y);
        if (dragPiece != null) {
            if (dragPiece.possibleMoves().contains(tile)) {
                setup.currentPlayer.selectedPieceNewLocation(tile);
                if (!setup.getCurrentPlayer().testIfMoveIsValid(dragPiece, tile)) { // Moving to this location put the player into check
                    updateBoardDrawing();                                           // Return the piece back to it's starting square
                    dragPiece = null;
                    return;
                }
                else {                                          //A valid move, move the piece to new location
                    setup.currentPlayer.movePiece();
                    updateBoardDrawing();
                    setup.endTurn(setup.getCurrentPlayer());
                    //gameBoard.printBoard();
                }
            }          
            if (!dragPiece.possibleMoves().contains(tile)) { // The selected square was not a possible square for the piece to move to
                updateBoardDrawing();                        // Return the piece back to it's starting square
                dragPiece = null;
            } 
        }
    }

    private boolean mouseOverPiece(Piece p ,int x, int y) {
            Point mouseLocation = new Point();
            mouseLocation.x = x;
            mouseLocation.y = y;
            if (p.getcurrentPosition().getRect().contains(mouseLocation)) {        
                return true;
            } 
        return false;
    }
    
    private void updateBoardDrawing() {
        for (Piece p : setup.currentPlayer.playerPieces) {
            p.setX(p.getcurrentPosition().getRect().getCenterX() - 30);
            p.setY(p.getcurrentPosition().getRect().getCenterY() - 30);
            drawSquaresAndPieces draw;
            this.chessGui.repaint();
        }
    }
}