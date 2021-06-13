package com.company;
import com.company.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Pawn extends Piece{
    public Pawn(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whitePawn.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\blackPawn.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        isFirstStep=true;
        type=PIECETYPE.PAWN;
        color=c;
    }
    @Override
    public void getAvalaibleSteps(Board b) {
        validSteps.clear();
        if(getColor().equals(Color.WHITE)){
            Position pULeft=new Position(pos.x-1, pos.y-1);
            Position pUp=new Position(pos.x, pos.y-1);
            Position pURight=new Position(pos.x+1, pos.y-1);
            if(pUp.y<0){
                return;
            }
            else {
                if(isFirstStep){
                    Position twoUp=new Position(pos.x, pos.y-2);
                    if (twoUp.y >= 0 && b.table.get(twoUp.y).get(twoUp.x).piece.getType().equals(PIECETYPE.DEFAULT) && b.table.get(pUp.y).get(pUp.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        b.table.get(twoUp.y).get(twoUp.x).button.setBorder(new LineBorder(Color.GREEN));
                        validSteps.add(twoUp);
                    }
                }
                if(b.table.get(pUp.y).get(pUp.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                    b.table.get(pUp.y).get(pUp.x).button.setBorder(new LineBorder(Color.GREEN));
                    validSteps.add(pUp);
                }
                if(pULeft.x >= 0 && pULeft.y>=0) {
                    if (!b.table.get(pULeft.y).get(pULeft.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if (!b.table.get(pULeft.y).get(pULeft.x).piece.getColor().equals(getColor())) {
                            b.table.get(pULeft.y).get(pULeft.x).button.setBorder(new LineBorder(Color.GREEN));
                            validSteps.add(pULeft);
                        }
                    }
                }
                if(pURight.x < 8) {
                    if (!b.table.get(pURight.y).get(pURight.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if (!b.table.get(pURight.y).get(pURight.x).piece.getColor().equals(getColor())) {
                            b.table.get(pURight.y).get(pURight.x).button.setBorder(new LineBorder(Color.GREEN));
                            validSteps.add(pURight);
                        }
                    }
                }
            }
        }
        else{
            Position pDLeft=new Position(pos.x-1, pos.y+1);
            Position pDown=new Position(pos.x, pos.y+1);
            Position pDRight=new Position(pos.x+1, pos.y+1);
            if(pDown.y>=8){
                return;
            }
            else {
                if(isFirstStep){
                    Position twoDown=new Position(pos.x, pos.y+2);
                    if (twoDown.y <8 && b.table.get(twoDown.y).get(twoDown.x).piece.getType().equals(PIECETYPE.DEFAULT) && b.table.get(pDown.y).get(pDown.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        b.table.get(twoDown.y).get(twoDown.x).button.setBorder(new LineBorder(Color.GREEN));
                        validSteps.add(twoDown);
                    }
                }
                if(b.table.get(pDown.y).get(pDown.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                    b.table.get(pDown.y).get(pDown.x).button.setBorder(new LineBorder(Color.GREEN));
                    validSteps.add(pDown);
                }
                if (pDLeft.x>= 0) {
                    if (!b.table.get(pDLeft.y).get(pDLeft.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if (!b.table.get(pDLeft.y).get(pDLeft.x).piece.getColor().equals(getColor())) {
                            b.table.get(pDLeft.y).get(pDLeft.x).button.setBorder(new LineBorder(Color.GREEN));
                            validSteps.add(pDLeft);
                        }
                    }
                }
                if (pDRight.x < 8) {
                    if (!b.table.get(pDRight.y).get(pDRight.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if (!b.table.get(pDRight.y).get(pDRight.x).piece.getColor().equals(getColor())) {
                            b.table.get(pDRight.y).get(pDRight.x).button.setBorder(new LineBorder(Color.GREEN));
                            validSteps.add(pDRight);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void step(Board b, Position p) {
        if(!(b.whiteTurn && getColor().equals(Color.white) || !b.whiteTurn && getColor().equals(Color.black)))
        {
            return;
        }
        if(b.table.get(p.y).get(p.x).piece.getType().equals(PIECETYPE.DEFAULT)){
            b.table.get(this.pos.y).get(this.pos.x).piece=b.table.get(p.y).get(p.x).piece;
            b.table.get(this.pos.y).get(this.pos.x).button.setIcon(null);
            b.table.get(p.y).get(p.x).piece=this;
            b.table.get(p.y).get(p.x).button.setIcon(this.picture);
            this.pos=p;
            b.whiteTurn=!b.whiteTurn;
            isFirstStep=false;
        }
        else if(!b.table.get(p.y).get(p.x).piece.getColor().equals(getColor())){
            if(!b.table.get(p.y).get(p.x).piece.getType().equals(PIECETYPE.KING)){
                b.table.get(this.pos.y).get(this.pos.x).button.setIcon(null);
                b.table.get(p.y).get(p.x).piece=this;
                b.table.get(p.y).get(p.x).button.setIcon(this.picture);
                b.table.get(this.pos.y).get(this.pos.x).piece=new Piece();
                this.pos=p;
                b.whiteTurn=!b.whiteTurn;
                isFirstStep=false;
            }

        }
    }
}
