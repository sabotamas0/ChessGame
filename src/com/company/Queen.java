package com.company;
import com.company.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Queen extends Piece{
    public Queen(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteQueen.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\blackQueen.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        type=PIECETYPE.QUEEN;
        color=c;
    }
    @Override
    public void getAvalaibleSteps(Board b) {
        validSteps.clear();
        int x=pos.x;
        int y=pos.y;
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
                }
                break;
            }
            validSteps.add(new Position(x, y));
        }
        x=pos.x;
        y=pos.y;
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
                }
                break;
            }

            validSteps.add(new Position(x, y));
        }
        x=pos.x;
        y=pos.y;
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
                }
                break;
            }
            validSteps.add(new Position(x, y));
        }
        x=pos.x;
        y=pos.y;
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
                }
                break;
            }
            validSteps.add(new Position(x, y));
        }
        //a mi poziciónkból balra
        x=pos.x;
        y=pos.y;
        for(int i = x;i>=0;--i) {
            if(x==i){
                continue;
            }
            //TODO csekkolni hogy ez a király e mert akkor sakk van.
            if(!b.table.get(y).get(i).piece.getType().equals(PIECETYPE.DEFAULT)){
                if(!b.table.get(y).get(i).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(i, y));
                }
                break;

            }
            validSteps.add(new Position(i, y));
        }
        ///////////////////////////////////
        //a mi poziciónkból jobbra
        x=pos.x;
        y=pos.y;
        for(int i = x;i<8;++i) {
            if(x==i){
                continue;
            }
            //TODO csekkolni hogy ez a király e mert akkor sakk van.
            if(!b.table.get(y).get(i).piece.getType().equals(PIECETYPE.DEFAULT)){
                if(!b.table.get(y).get(i).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(i, y));
                }
                break;

            }
            validSteps.add(new Position(i, y));
        }
        x=pos.x;
        y=pos.y;
        for(int i = y;i>=0;--i) {
            if(y==i){
                continue;
            }
            if(!b.table.get(i).get(x).piece.getType().equals(PIECETYPE.DEFAULT)){
                if(!b.table.get(i).get(x).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(x, i));
                }
                break;

            }
            validSteps.add(new Position(x, i));
        }
        x=pos.x;
        y=pos.y;
        for(int i = y;i<8;++i) {
            if(y==i){
                continue;
            }
            if(!b.table.get(i).get(x).piece.getType().equals(PIECETYPE.DEFAULT)){
                if(!b.table.get(i).get(x).piece.getColor().equals(getColor()))
                {
                    validSteps.add(new Position(x, i));
                }
                break;

            }
            validSteps.add(new Position(x, i));
        }
        for(int i = 0;i<validSteps.size();++i) {
            b.table.get(validSteps.get(i).y).get(validSteps.get(i).x).button.setBorder(new LineBorder(Color.GREEN));
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

