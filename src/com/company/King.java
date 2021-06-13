package com.company;
import com.company.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class King extends Piece{
    public King(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteKing.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\blackKing.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        type=PIECETYPE.KING;
        color=c;
    }
    @Override
    public void getAvalaibleSteps(Board b) {
        validSteps.clear();
        for(int i=pos.x-1;i<pos.x+2;++i){
            for(int j=pos.y-1;j<pos.y+2;++j){
                if(j<8 && j>=0 && i>=0 && i<8){
                    PIECETYPE type = b.table.get(j).get(i).piece.getType();
                    Color other=getColor();
                    if(!type.equals(PIECETYPE.DEFAULT)){
                        other = b.table.get(j).get(i).piece.getColor();
                    }
                    boolean isEnemy = !other.equals(getColor());
                    if (type.equals(PIECETYPE.DEFAULT) || (!type.equals(PIECETYPE.KING) && isEnemy)){
                        validSteps.add(new Position(i,j));
                    }
                }
            }
        }
        for(int i = 0;i<validSteps.size();++i) {
            b.table.get(validSteps.get(i).y).get(validSteps.get(i).x).button.setBorder(new LineBorder(Color.GREEN));
        }
        if(isFirstStep){
            Position pLeftTwo=new Position(this.pos.x-2,this.pos.y);
            Position pLeftOne=new Position(this.pos.x-1,this.pos.y);
            Position pRightTwo=new Position(this.pos.x+2,this.pos.y);
            Position pRightOne=new Position(this.pos.x+1,this.pos.y);
            if(getColor().equals(Color.WHITE)){
                //Fehét balra sáncolás csekk
                if(pLeftOne.isValid(8,8) && pLeftTwo.isValid(8,8)){
                    PIECETYPE typeOne = b.table.get(pLeftOne.y).get(pLeftOne.x).piece.getType();
                    PIECETYPE typeTwo = b.table.get(pLeftTwo.y).get(pLeftTwo.x).piece.getType();
                    boolean oneIsDefault = typeOne.equals(PIECETYPE.DEFAULT);
                    boolean twoIsDefault = typeTwo.equals(PIECETYPE.DEFAULT);
                    PIECETYPE typeRook = b.table.get(0).get(7).piece.getType();
                    boolean typeIsRook = typeRook.equals(PIECETYPE.ROOK);
                    boolean isRooksFirstStep = b.table.get(0).get(7).piece.isFirstStep();
                    if(oneIsDefault && twoIsDefault && typeIsRook && isRooksFirstStep){
                        validSteps.add(pLeftTwo);
                    }
                }
                if(pRightTwo.isValid(8,8) && pRightOne.isValid(8,8)){
                    PIECETYPE typeOne = b.table.get(pRightOne.y).get(pRightOne.x).piece.getType();
                    PIECETYPE typeTwo = b.table.get(pRightTwo.y).get(pRightTwo.x).piece.getType();
                    boolean oneIsDefault = typeOne.equals(PIECETYPE.DEFAULT);
                    boolean twoIsDefault = typeTwo.equals(PIECETYPE.DEFAULT);
                    PIECETYPE typeRook = b.table.get(7).get(7).piece.getType();
                    boolean typeIsRook = typeRook.equals(PIECETYPE.ROOK);
                    boolean isRooksFirstStep = b.table.get(7).get(7).piece.isFirstStep();
                    if(oneIsDefault && twoIsDefault && typeIsRook && isRooksFirstStep){
                        //b.table.get(pRightTwo.y).get(pRightTwo.x).button.getGraphics().setColor(Color.green);
                        //b.table.get(pRightTwo.y).get(pRightTwo.x).button.getGraphics().fillOval(0, 0, 25, 25);
                        b.table.get(pRightTwo.y).get(pRightTwo.x).button.setBorder(new LineBorder(Color.CYAN));
                        validSteps.add(pRightTwo);
                    }
                }
            }
            else {
                if(pLeftOne.isValid(8,8) && pLeftTwo.isValid(8,8)){
                    PIECETYPE typeOne = b.table.get(pLeftOne.y).get(pLeftOne.x).piece.getType();
                    PIECETYPE typeTwo = b.table.get(pLeftTwo.y).get(pLeftTwo.x).piece.getType();
                    boolean oneIsDefault = typeOne.equals(PIECETYPE.DEFAULT);
                    boolean twoIsDefault = typeTwo.equals(PIECETYPE.DEFAULT);
                    PIECETYPE typeRook = b.table.get(0).get(0).piece.getType();
                    boolean typeIsRook = typeRook.equals(PIECETYPE.ROOK);
                    boolean isRooksFirstStep = b.table.get(0).get(0).piece.isFirstStep();
                    if(oneIsDefault && twoIsDefault && typeIsRook && isRooksFirstStep){
                        validSteps.add(pLeftTwo);
                    }
                }
                if(pRightTwo.isValid(8,8) && pRightOne.isValid(8,8)){
                    PIECETYPE typeOne = b.table.get(pRightOne.y).get(pRightOne.x).piece.getType();
                    PIECETYPE typeTwo = b.table.get(pRightTwo.y).get(pRightTwo.x).piece.getType();
                    boolean oneIsDefault = typeOne.equals(PIECETYPE.DEFAULT);
                    boolean twoIsDefault = typeTwo.equals(PIECETYPE.DEFAULT);
                    PIECETYPE typeRook = b.table.get(7).get(0).piece.getType();
                    boolean typeIsRook = typeRook.equals(PIECETYPE.ROOK);
                    boolean isRooksFirstStep = b.table.get(7).get(0).piece.isFirstStep();
                    if(oneIsDefault && twoIsDefault && typeIsRook && isRooksFirstStep){
                        //b.table.get(pRightTwo.y).get(pRightTwo.x).button.getGraphics().setColor(Color.green);
                        //b.table.get(pRightTwo.y).get(pRightTwo.x).button.getGraphics().fillOval(0, 0, 25, 25);
                        b.table.get(pRightTwo.y).get(pRightTwo.x).button.setBorder(new LineBorder(Color.CYAN));
                        validSteps.add(pRightTwo);
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
            if(isFirstStep && p.x==2 && p.y==7){
                b.table.get(7).get(0).piece.step(b,new Position(3,7));
            }
            else if(isFirstStep && p.x==6 && p.y==7){
                b.table.get(7).get(7).piece.step(b,new Position(5,7));
            }
            else if(isFirstStep && p.x==2 && p.y==0){
                b.table.get(0).get(0).piece.step(b,new Position(3,0));
            }
            else if(isFirstStep && p.x==6 && p.y==0){
                b.table.get(0).get(7).piece.step(b,new Position(5,0));
            }
            else{
                b.whiteTurn=!b.whiteTurn;
            }

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
            }

        }
    }
}
