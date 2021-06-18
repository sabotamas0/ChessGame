package com.company;
import com.company.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Pawn extends Piece{
    boolean isFirstStepTwoStep;
    public Pawn(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whitePawn.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackPawn.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        isFirstStep=true;
        type=PIECETYPE.PAWN;
        color=c;
    }
    /*
    En passant ütésre akkor van lehetősége a játékosnak,
    ha gyalogja a saját kezdőállásához képest a felezővonalon túl következő soron áll
    (ez a világossal játszó számára az 5., sötét számára a 4. sor).

    Ha egy ilyen állásban az ellenfél gyalogja a jobbra vagy a balra lévő vonalon alapállásából
    (tehát a 7., illetve a 2. sorról) kettőt lép előre – azaz áthalad az előretolt gyalog ütési mezőjén –,
    akkor leüthető, ugyanúgy, mintha csak egyet lépett volna előre.

    Ez az egyetlen lépés a sakkban, amikor az ütés úgy történik,
    hogy az ütő bábu nem a kiütött bábu helyére lép, hanem mögé.
     */
    @Override
    public void getAvalaibleSteps(Board b) {
        validSteps.clear();
        if(getColor().equals(Color.WHITE)){
            Position pULeft=new Position(pos.x-1, pos.y-1);
            Position pUp=new Position(pos.x, pos.y-1);
            Position pURight=new Position(pos.x+1, pos.y-1);
            Position pEnPassantLeft=new Position(pos.x-1, pos.y-1);
            Position pLeft=new Position(pos.x-1, pos.y);
            Position pEnPassantRight=new Position(pos.x+1, pos.y-1);
            Position pRight=new Position(pos.x+1, pos.y);
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
                if(pEnPassantLeft.y==2){
                    if (b.table.get(pLeft.y).get(pLeft.x).piece.getType().equals(PIECETYPE.PAWN)) {
                        if (!b.table.get(pLeft.y).get(pLeft.x).piece.getColor().equals(getColor())) {
                            Pawn pawn = (Pawn) b.table.get(pLeft.y).get(pLeft.x).piece;
                            if (pawn.isFirstStepTwoStep) {
                                validSteps.add(pEnPassantLeft);
                                b.table.get(pEnPassantLeft.y).get(pEnPassantLeft.x).button.setBorder(new LineBorder(Color.GREEN));
                            }
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
            if(this.getColor().equals(Color.white)){
                if(this.pos.y==p.y && p.y==4 && isFirstStep){
                    isFirstStepTwoStep=true;
                }
                b.table.get(p.y+1).get(p.x).button.setIcon(null);// <-wtf
            }
            else{
                if(this.pos.y==p.y && p.y==3 && isFirstStep){
                    isFirstStepTwoStep=true;
                }
            }
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
