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
    private ArrayList<Square> possibleMoves;

    public PiecesDragAndDropListener(JPanel panel, gameSetup setup) {
        this.chessGui = panel;
        this.gameBoard = setup.gameBoard;
        this.setup = setup;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println(e.getX());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
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
        for (Piece p : setup.currentPlayer().getPlayersPieces()) {
            if (mouseOverPiece(p,x,y)) {               
                dragPiece = p;
                possibleMoves = dragPiece.possibleMoves();
                //System.out.println(dragPiece.possibleMoves());           
                g.setColor(Color.CYAN);
                if (possibleMoves != null) {
                    for (Square tile : possibleMoves) {
                        Rectangle rect = tile.getRect();
                        g.setColor(Color.CYAN);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(5));
                        g2.draw(new Rectangle(rect.x -1, rect.y -1, 68,68));                     
                    }
                this.dragPiece = p;
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
        possibleMoves = null;
        if (dragPiece != null) {
            if (dragPiece.possibleMoves().contains(tile)) {
                Graphics g = this.chessGui.getGraphics();
                System.out.println("tile to move to : " + tile);
                dragPiece.move(tile);                                    
                dragPiece.setX(tile.getRect().getCenterX() - 30);
                dragPiece.setY(tile.getRect().getCenterY() - 30);
                this.chessGui.repaint();
                setup.endTurn();
            }
            if (!dragPiece.possibleMoves().contains(tile)) {
                dragPiece.setX(dragPiece.getcurrentPosition().getRect().getCenterX() - 30);
                dragPiece.setY(dragPiece.getcurrentPosition().getRect().getCenterY() - 30);
                dragPiece = null;
                possibleMoves = null;
                this.chessGui.repaint();
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
}