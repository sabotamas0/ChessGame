package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

enum PIECETYPE{PAWN,BISHOP,KNIGHT,ROOK,KING,QUEEN,DEFAULT};
/*

    The Pawn
    The Bishop
    The Knight
    The Rook
    The Queen
    The King

 */
public class Piece implements PieceInterface {
    Piece(){
        validSteps=new Vector<Position>();
        checkingPositions=new Vector<Position>();
        allyPieces=new Vector<Piece>();
    }
    //TODO le lehessen kérdezni hogy default e
    @Override
    public PIECETYPE getType(){
        return type;
    }
    @Override
    public Position getPosition(){
        return pos;
    }
    @Override
    public Color getColor(){
        return color;
    }
    @Override
    public Vector<Position> getAvalaibleSteps(Board b,boolean colorize){
        return new Vector<Position>();
    }

    @Override
    public Vector<Position> getAvalaibleSteps(Board b,boolean colorize,boolean callIsChecked){return new Vector<Position>();}

    @Override
    public Vector<Position> getCheckingPositions() {
        return checkingPositions;
    }

    @Override
    public Vector<Piece> getAllyPieces() {
        return allyPieces;
    }

    @Override
    public boolean step(Board b,Position p){return false;};
    @Override
    public boolean isFirstStep(){return isFirstStep;};
    Position pos;
    PIECETYPE type=PIECETYPE.DEFAULT;
    Color color;
    ImageIcon picture;
    Vector<Position> validSteps;
    Vector<Position> checkingPositions;
    Vector<Piece> allyPieces;
    public boolean isChecking=false;
    boolean isFirstStep=true;
}
