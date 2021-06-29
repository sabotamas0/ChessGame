package com.company;
import com.company.Piece;
import com.company.Queen;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

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
    void promotePawn(Position po,Board b){
        JPanel p=new JPanel();
        p.setLayout(new GridLayout(2,2));
        p.setSize(50,50);
        JButton queen;
        JButton bishop;
        JButton rook;
        JButton knight;
        if(getColor().equals(Color.white)) {
            queen = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whiteQueen.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            bishop = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whiteBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            rook = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whiteRook.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            knight = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\whiteKnight.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        }
        else{
            queen = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackQueen.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            bishop = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            rook = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackRook.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
            knight = new JButton(new ImageIcon(new ImageIcon("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\images\\blackKnight.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
        }
        p.add(queen);
        p.add(bishop);
        p.add(rook);
        p.add(knight);
        p.setVisible(true);
        JFrame f=new JFrame();
        f.add(p);
        f.setSize(200,200);
        f.setVisible(true);
        f.setContentPane(p);
        queen.addActionListener(e -> {
            if(e.getSource().equals(queen)){
                Queen queen1;
                if(getColor().equals(Color.white)) {
                    queen1 = new Queen(Color.white);
                }
                else{
                    queen1 = new Queen(Color.black);
                }
                queen1.pos=po;
                b.table.get(po.y).get(po.x).piece= queen1;
                b.table.get(po.y).get(po.x).button.setIcon(queen1.picture);
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                queen1.getAvalaibleSteps(b,true);
            }
        });
        bishop.addActionListener(e -> {
            if(e.getSource().equals(bishop)){
                Bishop bishop1;
                if(getColor().equals(Color.white)) {
                    bishop1 = new Bishop(Color.white);
                }
                else{
                    bishop1 = new Bishop(Color.black);
                }
                bishop1.pos=po;
                b.table.get(po.y).get(po.x).piece= bishop1;
                b.table.get(po.y).get(po.x).button.setIcon(bishop1.picture);
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                bishop1.getAvalaibleSteps(b,true);
            }
        });
        rook.addActionListener(e -> {
            if(e.getSource().equals(rook)){
                Rook rook1;
                if(getColor().equals(Color.white)) {
                    rook1 = new Rook(Color.white);
                }
                else{
                    rook1 = new Rook(Color.black);
                }
                rook1.pos=po;
                b.table.get(po.y).get(po.x).piece= rook1;
                b.table.get(po.y).get(po.x).button.setIcon(rook1.picture);
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                rook1.getAvalaibleSteps(b,true);
            }
        });
        knight.addActionListener(e -> {
            if(e.getSource().equals(knight)){
                Knight knight1;
                if(getColor().equals(Color.white)) {
                    knight1 = new Knight(Color.white);
                }
                else{
                    knight1 = new Knight(Color.black);
                }
                knight1.pos=po;
                b.table.get(po.y).get(po.x).piece= knight1;
                b.table.get(po.y).get(po.x).button.setIcon(knight1.picture);
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                knight1.getAvalaibleSteps(b,true);
            }
        });

    }
    @Override
    public Vector<Position> getAvalaibleSteps(Board b,boolean colorize) {
        validSteps.clear();
        isChecking=false;
        if(getColor().equals(Color.WHITE)){
            Position pULeft=new Position(pos.x-1, pos.y-1);
            Position pUp=new Position(pos.x, pos.y-1);
            Position pURight=new Position(pos.x+1, pos.y-1);
            Position pEnPassantLeft=new Position(pos.x-1, pos.y-1);
            Position pLeft=new Position(pos.x-1, pos.y);
            Position pEnPassantRight=new Position(pos.x+1, pos.y-1);
            Position pRight=new Position(pos.x+1, pos.y);
            if(pUp.y<0){
                return new Vector<Position>();
            }
            else {
                if(isFirstStep){
                    Position twoUp=new Position(pos.x, pos.y-2);
                    if (twoUp.y >= 0 && b.table.get(twoUp.y).get(twoUp.x).piece.getType().equals(PIECETYPE.DEFAULT) && b.table.get(pUp.y).get(pUp.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if(colorize) {
                            b.table.get(twoUp.y).get(twoUp.x).button.setBorder(new LineBorder(Color.GREEN));
                        }
                        validSteps.add(twoUp);
                    }
                }
                if(b.table.get(pUp.y).get(pUp.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                    if(colorize) {
                        b.table.get(pUp.y).get(pUp.x).button.setBorder(new LineBorder(Color.GREEN));
                    }
                    validSteps.add(pUp);
                }
                if(pULeft.x >= 0 && pULeft.y>=0) {
                    if (!b.table.get(pULeft.y).get(pULeft.x).piece.getType().equals(PIECETYPE.DEFAULT)  && !b.table.get(pULeft.y).get(pULeft.x).piece.getType().equals(PIECETYPE.KING)) {
                        if (!b.table.get(pULeft.y).get(pULeft.x).piece.getColor().equals(getColor())) {
                            if(colorize) {
                                b.table.get(pULeft.y).get(pULeft.x).button.setBorder(new LineBorder(Color.GREEN));
                            }
                            validSteps.add(pULeft);
                        }
                    }
                    else if (b.table.get(pULeft.y).get(pULeft.x).piece.getType().equals(PIECETYPE.KING)){
                        King king=(King) b.table.get(pULeft.y).get(pULeft.x).piece;
                        king.isChecked=true;
                        isChecking=true;
                    }
                }
                if(pEnPassantLeft.y==2){
                    if (b.table.get(pLeft.y).get(pLeft.x).piece.getType().equals(PIECETYPE.PAWN)) {
                        if (!b.table.get(pLeft.y).get(pLeft.x).piece.getColor().equals(getColor())) {
                            Pawn pawn = (Pawn) b.table.get(pLeft.y).get(pLeft.x).piece;
                            if (pawn.isFirstStepTwoStep) {
                                validSteps.add(pEnPassantLeft);
                                if(colorize)
                                {
                                    b.table.get(pEnPassantLeft.y).get(pEnPassantLeft.x).button.setBorder(new LineBorder(Color.GREEN));
                                }
                            }
                        }
                    }
                }
                if(pEnPassantRight.y==2){
                    if (b.table.get(pRight.y).get(pRight.x).piece.getType().equals(PIECETYPE.PAWN)) {
                        if (!b.table.get(pRight.y).get(pRight.x).piece.getColor().equals(getColor())) {
                            Pawn pawn = (Pawn) b.table.get(pRight.y).get(pRight.x).piece;
                            if (pawn.isFirstStepTwoStep) {
                                validSteps.add(pEnPassantRight);
                                if(colorize)
                                {
                                    b.table.get(pEnPassantRight.y).get(pEnPassantRight.x).button.setBorder(new LineBorder(Color.GREEN));
                                }
                            }
                        }
                    }
                }
                if(pURight.x < 8) {
                    if (!b.table.get(pURight.y).get(pURight.x).piece.getType().equals(PIECETYPE.DEFAULT) && !b.table.get(pURight.y).get(pURight.x).piece.getType().equals(PIECETYPE.KING)) {
                        if (!b.table.get(pURight.y).get(pURight.x).piece.getColor().equals(getColor())) {
                            if(colorize) {
                                b.table.get(pURight.y).get(pURight.x).button.setBorder(new LineBorder(Color.GREEN));
                            }
                            validSteps.add(pURight);
                        }
                    }
                    else if (b.table.get(pURight.y).get(pURight.x).piece.getType().equals(PIECETYPE.KING)){
                        King king=(King) b.table.get(pURight.y).get(pURight.x).piece;
                        king.isChecked=true;
                        isChecking=true;
                    }
                }
            }
        }
        else{
            Position pDLeft=new Position(pos.x-1, pos.y+1);
            Position pDown=new Position(pos.x, pos.y+1);
            Position pDRight=new Position(pos.x+1, pos.y+1);
            Position pEnPassantLeft=new Position(pos.x-1, pos.y+1);
            Position pLeft=new Position(pos.x-1, pos.y);
            Position pEnPassantRight=new Position(pos.x+1, pos.y+1);
            Position pRight=new Position(pos.x+1, pos.y);
            if(pDown.y>=8){
                return new Vector<Position>();
            }
            else {
                if(isFirstStep){
                    Position twoDown=new Position(pos.x, pos.y+2);
                    if (twoDown.y <8 && b.table.get(twoDown.y).get(twoDown.x).piece.getType().equals(PIECETYPE.DEFAULT) && b.table.get(pDown.y).get(pDown.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if(colorize) {
                            b.table.get(twoDown.y).get(twoDown.x).button.setBorder(new LineBorder(Color.GREEN));
                        }
                        validSteps.add(twoDown);
                    }
                }
                if(b.table.get(pDown.y).get(pDown.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                    if(colorize) {
                        b.table.get(pDown.y).get(pDown.x).button.setBorder(new LineBorder(Color.GREEN));
                    }
                    validSteps.add(pDown);
                }
                if (pDLeft.x>= 0) {
                    if (!b.table.get(pDLeft.y).get(pDLeft.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if (!b.table.get(pDLeft.y).get(pDLeft.x).piece.getColor().equals(getColor())) {
                            if(colorize) {
                                b.table.get(pDLeft.y).get(pDLeft.x).button.setBorder(new LineBorder(Color.GREEN));
                            }
                            validSteps.add(pDLeft);
                        }
                    }
                }
                if(pEnPassantLeft.y==5){
                    if (b.table.get(pLeft.y).get(pLeft.x).piece.getType().equals(PIECETYPE.PAWN)) {
                        if (!b.table.get(pRight.y).get(pLeft.x).piece.getColor().equals(getColor())) {
                            Pawn pawn = (Pawn) b.table.get(pLeft.y).get(pLeft.x).piece;
                            if (pawn.isFirstStepTwoStep) {
                                validSteps.add(pEnPassantLeft);
                                if(colorize)
                                {
                                    b.table.get(pEnPassantLeft.y).get(pEnPassantLeft.x).button.setBorder(new LineBorder(Color.GREEN));
                                }
                            }
                        }
                    }
                }
                if(pEnPassantRight.y==5){
                    if (b.table.get(pRight.y).get(pRight.x).piece.getType().equals(PIECETYPE.PAWN)) {
                        if (!b.table.get(pRight.y).get(pRight.x).piece.getColor().equals(getColor())) {
                            Pawn pawn = (Pawn) b.table.get(pRight.y).get(pRight.x).piece;
                            if (pawn.isFirstStepTwoStep) {
                                validSteps.add(pEnPassantRight);
                                if(colorize)
                                {
                                    b.table.get(pEnPassantRight.y).get(pEnPassantRight.x).button.setBorder(new LineBorder(Color.GREEN));
                                }
                            }
                        }
                    }
                }
                if (pDRight.x < 8) {
                    if (!b.table.get(pDRight.y).get(pDRight.x).piece.getType().equals(PIECETYPE.DEFAULT)) {
                        if (!b.table.get(pDRight.y).get(pDRight.x).piece.getColor().equals(getColor())) {
                            if(colorize) {
                                b.table.get(pDRight.y).get(pDRight.x).button.setBorder(new LineBorder(Color.GREEN));
                            }
                            validSteps.add(pDRight);
                        }
                    }
                }
            }
        }
        return validSteps;
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
                if(p.y==0){
                    promotePawn(p,b);
                }
                if(p.y==4 && isFirstStep){
                    isFirstStepTwoStep=true;
                }
                b.table.get(p.y+1).get(p.x).button.setIcon(null);
            }
            else{
                if(p.y==7){
                    promotePawn(p,b);
                }
                if(this.pos.y==p.y && p.y==3 && isFirstStep){
                    isFirstStepTwoStep=true;
                }
                b.table.get(p.y-1).get(p.x).button.setIcon(null);
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
