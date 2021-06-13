package com.company;
import com.company.Board;
import com.company.Position;
import com.company.PieceInterface;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;
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
    public void getAvalaibleSteps(Board b){};
    @Override
    public void step(Board b,Position p){};
    @Override
    public boolean isFirstStep(){return isFirstStep;};
    Position pos;
    PIECETYPE type=PIECETYPE.DEFAULT;
    Color color;
    ImageIcon picture;
    Vector<Position> validSteps;
    boolean isFirstStep=true;
}
