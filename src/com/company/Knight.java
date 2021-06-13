package com.company;

import com.company.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Vector;

public class Knight extends Piece{
    public Knight(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteKnight.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\blackKnight.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        type=PIECETYPE.KNIGHT;
        color=c;
    }
    @Override
    public void getAvalaibleSteps(Board b) {
        validSteps.clear();
        Vector<Position> steps=new Vector<Position>();
        //csekkold hogy lelépsz e a pályáról
        steps.add(new Position(pos.x-1, pos.y-2));
        steps.add(new Position(pos.x-2, pos.y-1));
        steps.add(new Position(pos.x+2, pos.y-1));
        steps.add(new Position(pos.x+1, pos.y-2));


        steps.add(new Position(pos.x-2, pos.y+1));
        steps.add(new Position(pos.x-1, pos.y+2));
        steps.add(new Position(pos.x+1, pos.y+2));
        steps.add(new Position(pos.x+2, pos.y+1));

        for(int i = 0;i<steps.size();++i){
            if(steps.get(i).x<8 && steps.get(i).x>=0 && steps.get(i).y>=0 && steps.get(i).y<8 ){
                PIECETYPE type = b.table.get(steps.get(i).y).get(steps.get(i).x).piece.getType();
                Color other=getColor();
                if(!type.equals(PIECETYPE.DEFAULT)){
                    other = b.table.get(steps.get(i).y).get(steps.get(i).x).piece.getColor();
                }
                boolean isEnemy = !other.equals(getColor());
                if (type.equals(PIECETYPE.DEFAULT) || (!type.equals(PIECETYPE.KING) && isEnemy)){
                    validSteps.add(new Position(steps.get(i).x,steps.get(i).y));
                }
            }
        }
        /*
        for(int i = 0;i<down.size();++i){
            if(down.get(i).x<8 && down.get(i).y<8 && down.get(i).x>=0 && b.table.get(down.get(i).y).get(down.get(i).x).piece.getType().equals(PIECETYPE.DEFAULT)){
                validSteps.add(new Position(down.get(i).x,down.get(i).y));
            }
        }
        */
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
