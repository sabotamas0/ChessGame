package com.company;

import java.awt.*;
import java.util.Vector;

public interface PieceInterface {
    PIECETYPE getType();
    Position getPosition();
    Color getColor();
    Vector<Position> getAvalaibleSteps(Board b,boolean colorize);
    Vector<Position> getAvalaibleSteps(Board b,boolean colorize,boolean callIsChecked);
    Vector<Position> getCheckingPositions();
    Vector<Piece> getAllyPieces();
    Piece clone();
    boolean step(Board b,Position p);
    boolean isFirstStep();
}
