package com.company;
import com.company.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JComponent;

public class King extends Piece{
    boolean isChecked;
    Vector<DangerousPieceWithPosition> dangerousPositions;
    public King(Color c){
        if(c.equals(Color.white)) {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whiteKing.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        else {
            picture = new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackKing.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        }
        type=PIECETYPE.KING;
        color=c;
        dangerousPositions=new Vector<DangerousPieceWithPosition>();
    }
    public King(King king) {
        this.picture = king.picture;
        this.type = king.type;
        this.color = king.color;
        this.pos = new Position(king.pos);
        this.validSteps = king.validSteps;
        this.checkingPositions = king.checkingPositions;
        this.allyPieces = king.allyPieces;
        this.isChecking = king.isChecking;
        this.isFirstStep = king.isFirstStep;
        this.dangerousPositions = king.dangerousPositions;
        this.isChecked = king.isChecked;
    }
    /*
    a valid stepsben ellenőrizni kell azt hogy az adott pocícióra lépéssel sakkba kerül e az én királyom,
    ha igen akkor ez a pozició nem valid
     */
    boolean isPositionChecked(Position pos,Board b){
        boolean checked=false;
        for(int i = 0;i< 8 && !checked;++i) {
            for (int j = 0; j < 8 && !checked; ++j) {
                    if (!b.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT) && !b.table.get(i).get(j).piece.getColor().equals(this.getColor())) {
                        /*if(b.table.get(i).get(j).piece.getType().equals(PIECETYPE.QUEEN)) {*/
                        if (b.table.get(i).get(j).piece.getType().equals(PIECETYPE.KING)) {
                            if(((King)(b.table.get(i).get(j).piece)).getAvalaibleSteps(b, false,false).contains(pos))
                            {
                                checked=true;
                            }
                        }
                        else if (b.table.get(i).get(j).piece.getType().equals(PIECETYPE.PAWN)) {
                            if(((Pawn)(b.table.get(i).get(j).piece)).getDangerousPositions().contains(pos))
                            {
                                checked=true;
                            }
                        }
                        else if (b.table.get(i).get(j).piece.getAvalaibleSteps(b, false).contains(pos)) {
                            PIECETYPE p = b.table.get(i).get(j).piece.getType();
                            checked=true;
                        }

                        }
                    }
                }

        return checked;
    }
    boolean isPieceDefended(Piece piece, Board b){
        boolean defended=false;
        for(int i = 0;i < 8 && !defended;++i) {
            for (int j = 0; j < 8 && !defended; ++j) {
                Piece currentPiece=b.table.get(i).get(j).piece;
                if(!currentPiece.equals(piece) && !currentPiece.getType().equals(PIECETYPE.DEFAULT) && currentPiece.getColor().equals(piece.getColor())) {
                    if (currentPiece.getType().equals(PIECETYPE.KING)) {
                        if (((King) (currentPiece)).getAvalaibleSteps(b, false, false).contains(piece.pos)) {
                            defended = true;
                        }
                    }
                    else {
                        currentPiece.getAvalaibleSteps(b,false);
                        Vector<Piece> allyes=currentPiece.getAllyPieces();
                        defended = allyes.contains(piece);
                    }
                }
            }
        }
        return defended;
    }
    @Override
    public Vector<Position> getAvalaibleSteps(Board b,boolean colorize) {
        return getAvalaibleSteps(b,colorize,true);
    }
    @Override
    public Vector<Position> getAvalaibleSteps(Board b,boolean colorize,boolean callIsChecked){
        validSteps.clear();
        allyPieces.clear();
        dangerousPositions.clear();
        Position step;
        for(int i=pos.x-1;i<pos.x+2;++i){
            for(int j=pos.y-1;j<pos.y+2;++j){
                if(j<8 && j>=0 && i>=0 && i<8){
                    PIECETYPE type = b.table.get(j).get(i).piece.getType();
                    Color other=getColor();
                    if(!type.equals(PIECETYPE.DEFAULT)){
                        other = b.table.get(j).get(i).piece.getColor();
                    }
                    boolean isEnemy = !other.equals(getColor());
                    step=new Position(i, j);
                    if (type.equals(PIECETYPE.DEFAULT)){
                        if(callIsChecked) {
                            if(!isPositionChecked(step,b)){
                                validSteps.add(step);
                            }
                            else{
                                dangerousPositions.add(new DangerousPieceWithPosition(b.table.get(j).get(i).piece,step));
                            }
                        }
                        else
                        {
                            validSteps.add(step);
                        }
                    }
                    else if(!type.equals(PIECETYPE.KING) && isEnemy){
                        if(!isPieceDefended(b.table.get(j).get(i).piece,b)){
                            validSteps.add(step);
                        }
                    }
                    else if(!type.equals(PIECETYPE.DEFAULT) && !isEnemy){
                        allyPieces.add(b.table.get(j).get(i).piece);
                    }
                }
            }
        }
        for(int i = 0;i<validSteps.size();++i) {
            if(colorize) {
                b.table.get(validSteps.get(i).y).get(validSteps.get(i).x).button.setBorder(new LineBorder(Color.GREEN));
            }
        }
        if(isFirstStep&&!isChecked){
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
                        if(colorize) {
                            b.table.get(pRightTwo.y).get(pRightTwo.x).button.setBorder(new LineBorder(Color.CYAN));
                        }
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
                        if(colorize) {
                            b.table.get(pRightTwo.y).get(pRightTwo.x).button.setBorder(new LineBorder(Color.CYAN));
                        }
                        validSteps.add(pRightTwo);
                    }
                }
            }
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
            this.pos = p;
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

            /*
            if(b.checkedPositions.contains(p)){
                JOptionPane.showMessageDialog(b.panel,"Sakk");
            }
            */

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
    @Override
    public Piece clone() {
        return new King(this);
    }
}
