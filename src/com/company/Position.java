package com.company;

public class Position {
    public int x=-1;
    public int y=-1;
    public Position(int pX,int pY){
        this.x=pX;
        this.y=pY;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position)) {
            return false;
        }
        Position otherPos = (Position) other;
        return otherPos.x==x && otherPos.y ==y;
    }

    public boolean isValid(int xMax,int yMax){
        return (y<yMax && y>=0 && x>=0 && x<xMax);
    }
}
