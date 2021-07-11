package com.company;
import com.company.Piece;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Bishop extends Piece{
    public Bishop(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whiteBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        type=PIECETYPE.BISHOP;
        color=c;
    }
    @Override
    public Vector<Position> getAvalaibleSteps(Board b,boolean colorize) {
        validSteps.clear();
        checkingPositions.clear();
        allyPieces.clear();
        isChecking=false;
        int x=pos.x;
        int y=pos.y;
        Vector<Position> positions=new Vector<Position>();
        for(int i = 0;i<7;++i) {
            x=x-1;
            y=y-1;
            if(x<0 || y<0){
                break;
            }
            PIECETYPE type = b.table.get(y).get(x).piece.getType();
            if(!type.equals(PIECETYPE.DEFAULT) && !type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(x, y));
                    positions.add(new Position(x, y));
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            if(type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    King king=(King) b.table.get(y).get(x).piece;
                    king.isChecked=true;
                    isChecking=true;
                    positions.add(new Position(x, y));
                    checkingPositions.addAll(positions);
                    //JOptionPane.showMessageDialog(b.panel,"Sakk");
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }

            validSteps.add(new Position(x, y));
            positions.add(new Position(x, y));
        }
        x=pos.x;
        y=pos.y;
        positions.clear();
        for(int i = 0;i<8;++i) {
            x=x+1;
            y=y+1;
            if(x>=8 || y>=8){
                break;
            }
            PIECETYPE type = b.table.get(y).get(x).piece.getType();
            if(!type.equals(PIECETYPE.DEFAULT) && !type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(x, y));
                    positions.add(new Position(x, y));
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            if(type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    King king=(King) b.table.get(y).get(x).piece;
                    king.isChecked=true;
                    isChecking=true;
                    positions.add(new Position(x, y));
                    checkingPositions.addAll(positions);
                    //JOptionPane.showMessageDialog(b.panel,"Sakk");
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            validSteps.add(new Position(x, y));
            positions.add(new Position(x, y));
        }
        x=pos.x;
        y=pos.y;
        positions.clear();
        for(int i = 0;i<8;++i) {
            x=x+1;
            y=y-1;
            if(x>=8 || y<0){
                break;
            }
            PIECETYPE type = b.table.get(y).get(x).piece.getType();
            if(!type.equals(PIECETYPE.DEFAULT) && !type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(x, y));
                    positions.add(new Position(x, y));
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            if(type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    King king=(King) b.table.get(y).get(x).piece;
                    king.isChecked=true;
                    isChecking=true;
                    positions.add(new Position(x, y));
                    checkingPositions.addAll(positions);
                    //JOptionPane.showMessageDialog(b.panel,"Sakk");
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            validSteps.add(new Position(x, y));
            positions.add(new Position(x, y));
        }
        x=pos.x;
        y=pos.y;
        positions.clear();
        for(int i = 0;i<8;++i) {
            x=x-1;
            y=y+1;
            if(y>=8 || x<0){
                break;
            }
            PIECETYPE type = b.table.get(y).get(x).piece.getType();
            if(!type.equals(PIECETYPE.DEFAULT) && !type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(x, y));
                    positions.add(new Position(x, y));
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            if(type.equals(PIECETYPE.KING)){
                if(!b.table.get(y).get(x).piece.getColor().equals(getColor()))
                {
                    King king=(King) b.table.get(y).get(x).piece;
                    king.isChecked=true;
                    isChecking=true;
                    positions.add(new Position(x, y));
                    checkingPositions.addAll(positions);
                    //JOptionPane.showMessageDialog(b.panel,"Sakk");
                }
                else{
                    allyPieces.add(b.table.get(y).get(x).piece);
                }
                break;
            }
            validSteps.add(new Position(x, y));
            positions.add(new Position(x, y));
        }
        for(int i = 0;i<validSteps.size();++i) {
            if(colorize) {
                b.table.get(validSteps.get(i).y).get(validSteps.get(i).x).button.setBorder(new LineBorder(Color.GREEN));
            }
        }
        if(isChecking){
            //JOptionPane.showMessageDialog(b.panel,"Sakk");
        }
        return validSteps;
    }

    @Override
    public boolean step(Board b, Position p) {
        if(!(b.whiteTurn && getColor().equals(Color.white) || !b.whiteTurn && getColor().equals(Color.black)))
        {
            return false;
        }
        if(b.table.get(p.y).get(p.x).piece.getType().equals(PIECETYPE.DEFAULT)){
            b.table.get(this.pos.y).get(this.pos.x).piece=b.table.get(p.y).get(p.x).piece;
            b.table.get(this.pos.y).get(this.pos.x).button.setIcon(null);
            b.table.get(p.y).get(p.x).piece=this;
            b.table.get(p.y).get(p.x).button.setIcon(this.picture);
            this.pos=p;
            b.whiteTurn=!b.whiteTurn;
        }
        else if(!b.table.get(p.y).get(p.x).piece.getColor().equals(getColor())){
            if(!b.table.get(p.y).get(p.x).piece.getType().equals(PIECETYPE.KING)){

                b.table.get(this.pos.y).get(this.pos.x).button.setIcon(null);
                b.table.get(p.y).get(p.x).piece=this;
                b.table.get(p.y).get(p.x).button.setIcon(this.picture);
                b.table.get(this.pos.y).get(this.pos.x).piece=new Piece();
                this.pos=p;
                b.whiteTurn=!b.whiteTurn;
            }

        }
        return true;
    }
}
