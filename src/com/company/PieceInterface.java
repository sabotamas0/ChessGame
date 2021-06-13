package com.company;

import java.awt.*;

public interface PieceInterface {
    PIECETYPE getType();
    Position getPosition();
    Color getColor();
    void getAvalaibleSteps(Board b);
    void step(Board b,Position p);
    boolean isFirstStep();
}
