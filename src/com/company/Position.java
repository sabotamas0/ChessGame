package com.company;

public class Position {
    public int x=-1;
    public int y=-1;
    public Position(int pX,int pY){
        this.x=pX;
        this.y=pY;
    }
    public boolean isValid(int xMax,int yMax){
        return (y<yMax && y>=0 && x>=0 && x<xMax);
    }
}
