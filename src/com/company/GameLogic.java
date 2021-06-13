package com.company;
import com.company.Board;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import com.company.PIECETYPE;
public class GameLogic implements ActionListener {
    Board mBoard;
    //int prevPosX=-1;
    //int prevPosY=-1;
    Position previousPosition;
    public GameLogic(Board b){
        mBoard=b;
        mBoard.addActionListener(this);
        previousPosition=new Position(-1,-1);
        /*
        Pawn p=new Pawn();
        p.picture = new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whitePawn.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        p.color=Color.WHITE;
        p.pos=new Position(1,6);
        mBoard.table.get(p.pos.y).get(p.pos.x).button.setIcon(p.picture);
        mBoard.table.get(p.pos.y).get(p.pos.x).piece=p;
         */
        //PARASZTOK
        //feher
        pawnCreator(new Pawn(Color.white),new Position(0,6));
        pawnCreator(new Pawn(Color.white),new Position(1,6));
        pawnCreator(new Pawn(Color.white),new Position(2,6));
        pawnCreator(new Pawn(Color.white),new Position(3,6));
        pawnCreator(new Pawn(Color.white),new Position(4,6));
        pawnCreator(new Pawn(Color.white),new Position(5,6));
        pawnCreator(new Pawn(Color.white),new Position(6,6));
        pawnCreator(new Pawn(Color.white),new Position(7,6));
        //fekete
        pawnCreator(new Pawn(Color.black),new Position(0,1));
        pawnCreator(new Pawn(Color.black),new Position(1,1));
        pawnCreator(new Pawn(Color.black),new Position(2,1));
        pawnCreator(new Pawn(Color.black),new Position(3,1));
        pawnCreator(new Pawn(Color.black),new Position(4,1));
        pawnCreator(new Pawn(Color.black),new Position(5,1));
        pawnCreator(new Pawn(Color.black),new Position(6,1));
        pawnCreator(new Pawn(Color.black),new Position(7,1));
        //királyok
        //feher
        kingCreator(new King(Color.white),new Position(4,7));
        //fekete
        kingCreator(new King(Color.black),new Position(4,0));
        //királyok
        //feher
        queenCreator(new Queen(Color.white),new Position(3,7));
        //fekete
        queenCreator(new Queen(Color.black),new Position(3,0));
        //bástyák
        //fehér
        rookCreator(new Rook(Color.white),new Position(0,7));
        rookCreator(new Rook(Color.white),new Position(7,7));
        //fekete
        rookCreator(new Rook(Color.black),new Position(0,0));
        rookCreator(new Rook(Color.black),new Position(7,0));
        //futók
        //fehér
        bishopCreator(new Bishop(Color.white),new Position(2,7));
        bishopCreator(new Bishop(Color.white),new Position(5,7));
        //fekete
        bishopCreator(new Bishop(Color.black),new Position(2,0));
        bishopCreator(new Bishop(Color.black),new Position(5,0));
        //lovak
        //fehér
        knightCreator(new Knight(Color.white),new Position(1,7));
        knightCreator(new Knight(Color.white),new Position(6,7));
        //fekete
        knightCreator(new Knight(Color.black),new Position(1,0));
        knightCreator(new Knight(Color.black),new Position(6,0));
        //pawnCreator(new Pawn(Color.black),new Position(6,3));
        //kingCreator(new King(),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteKing.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.WHITE,new Position(4,7));
        //rookCreator(new Rook(),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\blackRook.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.BLACK,new Position(4,4));
        //rookCreator(new Rook(),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteRook.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.WHITE,new Position(2,4));
        //rookCreator(new Rook(),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteRook.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.WHITE,new Position(6,4));

       // knightCreator(new Knight(Color.white),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteKnight.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.WHITE,new Position(4,4));
        //bishopCreator(new Bishop(),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteBishop.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.WHITE,new Position(3,3));
        //queenCreator(new Queen(),new ImageIcon(new ImageIcon("C:\\Users\\asd\\Desktop\\ChessGame\\images\\whiteQueen.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)),Color.WHITE,new Position(2,3));
    }
    void pawnCreator(Pawn pawn,Position p){
        pawn.pos=p;
        mBoard.table.get(pawn.pos.y).get(pawn.pos.x).button.setIcon(pawn.picture);
        mBoard.table.get(pawn.pos.y).get(pawn.pos.x).piece=pawn;
    }
    void kingCreator(King king,Position p){
        king.pos=p;
        mBoard.table.get(king.pos.y).get(king.pos.x).button.setIcon(king.picture);
        mBoard.table.get(king.pos.y).get(king.pos.x).piece=king;
    }
    void knightCreator(Knight knight,Position p) {
        knight.pos = p;
        mBoard.table.get(knight.pos.y).get(knight.pos.x).button.setIcon(knight.picture);
        mBoard.table.get(knight.pos.y).get(knight.pos.x).piece = knight;
    }
    void queenCreator(Queen queen,Position p) {
        queen.pos = p;
        mBoard.table.get(queen.pos.y).get(queen.pos.x).button.setIcon(queen.picture);
        mBoard.table.get(queen.pos.y).get(queen.pos.x).piece = queen;
    }
    void bishopCreator(Bishop bishop,Position p) {
        bishop.pos = p;
        mBoard.table.get(bishop.pos.y).get(bishop.pos.x).button.setIcon(bishop.picture);
        mBoard.table.get(bishop.pos.y).get(bishop.pos.x).piece = bishop;
    }
    void rookCreator(Rook rook,Position p){
        rook.pos=p;
        mBoard.table.get(rook.pos.y).get(rook.pos.x).button.setIcon(rook.picture);
        mBoard.table.get(rook.pos.y).get(rook.pos.x).piece=rook;
    }
    void setBorderLightGray(){
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                mBoard.table.get(i).get(j).button.setBorder(new LineBorder(Color.lightGray));
            }
        }
    }
    //BISHOP,KNIGHT,ROOK,KING,QUEEN
    @Override
    public void actionPerformed(ActionEvent e) {
        //lépésellenrzés
        setBorderLightGray();
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(e.getSource()==mBoard.table.get(i).get(j).button /*&& mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT)*/){
                    if(previousPosition.y !=-1 &&(previousPosition.y!=i ||previousPosition.x!=j)){
                        if(mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.ROOK)) {
                            Rook rook = (Rook) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : rook.validSteps) {
                                if (p.x == j && p.y == i) {
                                    rook.step(mBoard, new Position(j, i));
                                    break;
                                }
                            }

                        }
                        if(mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.KING)) {
                            King king = (King) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : king.validSteps) {
                                if (p.x == j && p.y == i) {
                                    king.step(mBoard, new Position(j, i));
                                    break;
                                }
                            }

                        }
                        if(mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.PAWN)) {
                            Pawn pawn = (Pawn) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : pawn.validSteps) {
                                if (p.x == j && p.y == i) {
                                    pawn.step(mBoard, new Position(j, i));
                                    break;
                                }
                            }
                        }
                        if(mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.BISHOP)) {
                            Bishop bishop = (Bishop) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : bishop.validSteps) {
                                if (p.x == j && p.y == i) {
                                    bishop.step(mBoard, new Position(j, i));
                                    break;
                                }
                            }
                        }
                        if(mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.QUEEN)) {
                            Queen queen = (Queen) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : queen.validSteps) {
                                if (p.x == j && p.y == i) {
                                    queen.step(mBoard, new Position(j, i));
                                    break;
                                }
                            }
                        }
                        if(mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.KNIGHT)) {
                            Knight knight = (Knight) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : knight.validSteps) {
                                if (p.x == j && p.y == i) {
                                    knight.step(mBoard, new Position(j, i));
                                    break;
                                }
                            }

                        }
                        previousPosition.x=j;
                        previousPosition.y=i;

                    }
                }
            }
        }
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(e.getSource()==mBoard.table.get(i).get(j).button){
                    if(PIECETYPE.PAWN.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Pawn pawn = (Pawn)mBoard.table.get(i).get(j).piece;
                        pawn.getAvalaibleSteps(mBoard);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        return;
                    }
                    if(PIECETYPE.KING.equals(mBoard.table.get(i).get(j).piece.getType())){
                        King king = (King)mBoard.table.get(i).get(j).piece;
                        king.getAvalaibleSteps(mBoard);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        return;
                    }
                    if(PIECETYPE.ROOK.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Rook rook = (Rook)mBoard.table.get(i).get(j).piece;
                        rook.getAvalaibleSteps(mBoard);
                        //lépés
                        previousPosition.x=j;
                        previousPosition.y=i;
                        return;
                    }
                    if(PIECETYPE.KNIGHT.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Knight knight = (Knight)mBoard.table.get(i).get(j).piece;
                        knight.getAvalaibleSteps(mBoard);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        return;
                    }
                    if(PIECETYPE.BISHOP.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Bishop bishop = (Bishop)mBoard.table.get(i).get(j).piece;
                        bishop.getAvalaibleSteps(mBoard);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        return;
                    }
                    if(PIECETYPE.QUEEN.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Queen queen = (Queen) mBoard.table.get(i).get(j).piece;
                        queen.getAvalaibleSteps(mBoard);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        return;
                    }
                }
            }
        }

        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                mBoard.table.get(i).get(j).button.setBorder(new LineBorder(Color.lightGray));
            }
        }
    }
}
