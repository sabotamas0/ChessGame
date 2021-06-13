package com.company;
import com.company.Piece;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Bishop extends Piece{
    public Bishop(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\blackBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        type=PIECETYPE.BISHOP;
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
            if(!type.equals(PIECETYPE.DEFAULT)){
                boolean enemy =!b.table.get(y).get(x).piece.getColor().equals(getColor());
                if(!type.equals(PIECETYPE.KING) && enemy)
                {
                    validSteps.add(new Position(x, y));
                }
                else
                {
                    if(enemy)
                    {
                        JOptionPane.showMessageDialog(b.panel,"Sakk");
                    }
                }

                break;
            }
            validSteps.add(new Position(x, y));
        }

        /*
        //2 ciklussal kell mozgatni mert y x tengelyen egysszerre mozgok
        for(int i = y ;i>=0;i=-1) {
            for(int j = x ;j>=0;j=-1) {
                validSteps.add(new Position(j, i));
            }
        }
        */

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
