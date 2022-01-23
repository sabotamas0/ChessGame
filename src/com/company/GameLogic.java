package com.company;
import com.company.Board;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.Stack;
import java.util.Vector;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.io.IOException;

import com.company.PIECETYPE;
public class GameLogic implements ActionListener{
    Board mBoard;
    JPanel p;
    JFrame f=new JFrame();
    boolean flag;
    int szam=fajldarabVisszaAd();
    enum GLOBALSTATE{CHECK,STALEMATE,DEFAULT,CHECKMATE,INVALIDSTEP};
    //int prevPosX=-1;
    //int prevPosY=-1;
    public Position previousStep;
    Position previousPosition;
    Vector<Piece> previousTableState;
    public GameLogic(Board b){
        mBoard=b;
        mBoard.addActionListener(this);
        previousPosition=new Position(-1,-1);
        createDefaultGame();
        //createKingQueen();
    }
    public void pawnCreator(Pawn pawn,Position p){
        pawn.pos=p;
        mBoard.table.get(pawn.pos.y).get(pawn.pos.x).button.setIcon(pawn.picture);
        mBoard.table.get(pawn.pos.y).get(pawn.pos.x).piece=pawn;
    }
    void createDefaultGame(){

        for(int i = 0 ;i<8;++i){
            for(int j=0;j<8;++j){
                mBoard.table.get(i).get(j).piece.type=PIECETYPE.DEFAULT;
                mBoard.table.get(i).get(j).button.setIcon(null);
                mBoard.whiteTurn=true;
            }
        }
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
    }

    void createKingQueen(){

        //feher
        kingCreator(new King(Color.white),new Position(4,7));

        //fekete
        kingCreator(new King(Color.black),new Position(4,0));
        //királyok
        //feher

        queenCreator(new Queen(Color.white),new Position(3,7));
        //fekete
        queenCreator(new Queen(Color.black),new Position(3,0));
    }
    public void setKingCheckedFalse()
    {
        for(int i = 0;i < 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.KING)) {
                    King king = (King) mBoard.table.get(i).get(j).piece;
                    king.isChecked=false;
                }
            }
        }
    }
    boolean isPieceDetachable(Piece piece){
        boolean detachable=false;
        for(int i = 0;i < 8 && !detachable;++i) {
            for (int j = 0; j < 8 && !detachable; ++j) {
                if(!piece.getColor().equals(mBoard.table.get(i).get(j).piece.getColor()) && !mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT)) {
                    Vector<Position> pieceAvalaibleSteps=mBoard.table.get(i).get(j).piece.getAvalaibleSteps(mBoard,false);
                    if(pieceAvalaibleSteps.contains(piece.pos)){
                        detachable=true;
                    }
                }
            }
        }
        return detachable;
    }

    boolean isChecked(King king) {
        boolean isChecked=false;
        for(int i = 0;i < 8 && !isChecked;++i) {
            for (int j = 0; j < 8 && !isChecked; ++j) {
                if(!king.getColor().equals(mBoard.table.get(i).get(j).piece.getColor()) && !mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT) && !mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.KING) ) {
                    Vector<Position> pieceCheckingPositions=new Vector<Position>();
                    if (mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.QUEEN)) {
                        Queen q= (Queen)mBoard.table.get(i).get(j).piece;
                        q.getAvalaibleSteps(mBoard,false);
                        pieceCheckingPositions = q.getCheckingPositions();
                    }
                    else if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.BISHOP))
                    {
                        Bishop b =((Bishop) (mBoard.table.get(i).get(j).piece));
                        b.getAvalaibleSteps(mBoard,false);
                        pieceCheckingPositions = b.getCheckingPositions();
                    }
                    else if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.PAWN))
                    {
                        Pawn p=((Pawn) (mBoard.table.get(i).get(j).piece));
                        p.getAvalaibleSteps(mBoard,false,true);
                        pieceCheckingPositions = p.getCheckingPositions();
                    }
                    else if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.KNIGHT))
                    {
                        Knight k =((Knight) (mBoard.table.get(i).get(j).piece));
                        k.getAvalaibleSteps(mBoard,false);
                        pieceCheckingPositions = k.getCheckingPositions();
                    }
                    else if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.ROOK))
                    {
                        Rook r = ((Rook) (mBoard.table.get(i).get(j).piece));
                        r.getAvalaibleSteps(mBoard,false);
                        pieceCheckingPositions = r.getCheckingPositions();
                    }
                    isChecked = pieceCheckingPositions.contains(king.pos);
                }
            }
        }
        return isChecked;
    }

    boolean isCheckNeutralized(Piece piece){
        boolean neutralized=false;
        for(int i = 0;i < 8 && !neutralized;++i) {
            for (int j = 0; j < 8 && !neutralized; ++j) {
                if(!piece.getColor().equals(mBoard.table.get(i).get(j).piece.getColor()) && !mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT) && !mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.KING)) {
                    Vector<Position> pieceCheckingPositions=piece.getCheckingPositions();
                    if(mBoard.table.get(i).get(j).piece.getAvalaibleSteps(mBoard,false).containsAll(pieceCheckingPositions)){
                        neutralized=true;
                    }
                }
            }
        }
        return neutralized;
    }
    int howManyPiecesOnBoard(){
        int count=0;
        for(int i = 0;i < 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(!mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT)){
                    ++count;
                }
            }
        }
        return count;
    }
    boolean isTwoKingLeft() {
        int count=0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (!mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT)) {
                    ++count;
                }
            }
        }
        return count==2;
    }
    boolean isKingDefendable(King king){
        for(int i = 0;i < 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(mBoard.table.get(i).get(j).piece.isChecking) {
                    if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.KNIGHT) && !isPieceDetachable(mBoard.table.get(i).get(j).piece)){
                        return false;
                    }
                    else{
                        if(isCheckNeutralized(mBoard.table.get(i).get(j).piece) || isPieceDetachable(mBoard.table.get(i).get(j).piece)){
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    GLOBALSTATE getGameState(){
        if(isTwoKingLeft()){
            return GLOBALSTATE.STALEMATE;
        }
        Vector<Position> dangerousPositions= new Vector<Position>();
        Vector<Position> kingPositions= new Vector<Position>();
        King wKing=new King(Color.white);
        King bKing=new King(Color.white);
        //ha más állapotot csinálunk van e egyáltalán király a táblán
        for(int i = 0;i < 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.KING)){
                    if(mBoard.whiteTurn)
                    {
                        if(mBoard.table.get(i).get(j).piece.getColor().equals(Color.white)) {
                            wKing = (King) mBoard.table.get(i).get(j).piece;
                            kingPositions=wKing.getAvalaibleSteps(mBoard,false,false);
                        }
                        else
                        {
                            bKing=(King) mBoard.table.get(i).get(j).piece;
                        }
                    }
                    else
                    {
                        if(mBoard.table.get(i).get(j).piece.getColor().equals(Color.black)) {
                            bKing = (King) mBoard.table.get(i).get(j).piece;
                            kingPositions=bKing.getAvalaibleSteps(mBoard,false,false);
                        }
                        else
                        {
                            wKing=(King) mBoard.table.get(i).get(j).piece;
                        }
                    }
                }
                else if(!mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT))
                {
                    if(mBoard.whiteTurn){
                        if(mBoard.table.get(i).get(j).piece.getColor().equals(Color.black)){
                            if(mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.PAWN))
                            {
                                Pawn pawn=(Pawn) mBoard.table.get(i).get(j).piece;
                                dangerousPositions.addAll(pawn.getAvalaibleSteps(mBoard,false,true));
                            }
                            else
                            {
                                dangerousPositions.addAll(mBoard.table.get(i).get(j).piece.getAvalaibleSteps(mBoard,false));
                            }

                        }
                    }
                    else {
                        if (mBoard.table.get(i).get(j).piece.getColor().equals(Color.white)) {
                            if (mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.PAWN)) {
                                Pawn pawn = (Pawn) mBoard.table.get(i).get(j).piece;
                                dangerousPositions.addAll(pawn.getAvalaibleSteps(mBoard, false, true));
                            } else {
                                dangerousPositions.addAll(mBoard.table.get(i).get(j).piece.getAvalaibleSteps(mBoard, false));
                            }
                        }
                    }
                }
            }
        }
        GLOBALSTATE state=GLOBALSTATE.DEFAULT;
        boolean isCheck=false;
        //annak az állapotnak nem kéne fennálni hogy a másik ragad be és az aktuális király van sakkban
        //todo ezt szépen megírni.

        if(!mBoard.whiteTurn)
        {
            if(isChecked(wKing)){
                return GLOBALSTATE.INVALIDSTEP;
            }
        }
        else
        {
            if(isChecked(bKing)){
                return GLOBALSTATE.INVALIDSTEP;
            }
        }
        boolean kingIsStuck=false;
        if(kingPositions.isEmpty())
        {
            //TODO
            kingIsStuck=true;
        }
        else if (dangerousPositions.containsAll(kingPositions))
        {
            kingIsStuck=true;
        }

        if(kingIsStuck){
            if(mBoard.whiteTurn){
                if(isChecked(wKing)){
                    if(isKingDefendable(wKing)){
                        state=GLOBALSTATE.CHECK;
                    }
                    else{
                        state=GLOBALSTATE.CHECKMATE;
                    }
                }
                else{
                    boolean found=wKing.dangerousPositions.isEmpty();
                    for(int i=0;i<wKing.dangerousPositions.size() && !found;++i){
                        if(isPieceDetachable(wKing.dangerousPositions.get(i).piece)){
                            found=true;
                        }
                    }
                    if(found){
                        state=GLOBALSTATE.DEFAULT;
                    }
                    else{
                        state=GLOBALSTATE.STALEMATE;
                    }
                }
            }
            else{
                if(isChecked(bKing)){
                    if(isKingDefendable(bKing)){
                        state=GLOBALSTATE.CHECK;
                    }
                    else{
                        state=GLOBALSTATE.CHECKMATE;
                    }
                }
                else{
                    boolean found=bKing.dangerousPositions.isEmpty();
                    for(int i=0;i<bKing.dangerousPositions.size() && !found;++i){
                        if(isPieceDetachable(bKing.dangerousPositions.get(i).piece)){
                            found=true;
                        }
                    }
                    if(found){
                        state=GLOBALSTATE.DEFAULT;
                    }
                    else{
                        state=GLOBALSTATE.STALEMATE;
                    }
                }
            }
            /*
            if(mBoard.whiteTurn && isChecked(wKing)&&!isKingDefendable(wKing))
            {
                state=GLOBALSTATE.CHECKMATE;
            }
            else if(isChecked(bKing)&&!isKingDefendable(bKing) ){
                state=GLOBALSTATE.CHECKMATE;
            }
            else if(isChecked(wKing) ||isChecked(bKing)){
                state=GLOBALSTATE.CHECK;
            }
            else {
                //vagy else if van vagy valami ami eldönti hogy ez most patt helyzet e
            }
            */
            /*
            else if(!kingPositions.isEmpty()) {
                state = GLOBALSTATE.STALEMATE;
            }
            */
        }
        else{

            if(isChecked(wKing) ||isChecked(bKing)){
                state=GLOBALSTATE.CHECK;
            }
        }
        if(bKing.pos.y==-1 ||wKing.pos.y==-1 )
        {
            //ha akarjuk állítani akkor todo
            state=GLOBALSTATE.DEFAULT;
        }
        return state;
    }
    public void kingCreator(King king,Position p){
        king.pos=p;
        mBoard.table.get(king.pos.y).get(king.pos.x).button.setIcon(king.picture);
        mBoard.table.get(king.pos.y).get(king.pos.x).piece=king;
    }
    public void knightCreator(Knight knight,Position p) {
        knight.pos = p;
        mBoard.table.get(knight.pos.y).get(knight.pos.x).button.setIcon(knight.picture);
        mBoard.table.get(knight.pos.y).get(knight.pos.x).piece = knight;
    }
    public void queenCreator(Queen queen,Position p) {
        queen.pos = p;
        mBoard.table.get(queen.pos.y).get(queen.pos.x).button.setIcon(queen.picture);
        mBoard.table.get(queen.pos.y).get(queen.pos.x).piece = queen;
    }
    public void bishopCreator(Bishop bishop,Position p) {
        bishop.pos = p;
        mBoard.table.get(bishop.pos.y).get(bishop.pos.x).button.setIcon(bishop.picture);
        mBoard.table.get(bishop.pos.y).get(bishop.pos.x).piece = bishop;
    }
    public void rookCreator(Rook rook,Position p){
        rook.pos=p;
        mBoard.table.get(rook.pos.y).get(rook.pos.x).button.setIcon(rook.picture);
        mBoard.table.get(rook.pos.y).get(rook.pos.x).piece=rook;
    }
    public void setBorderLightGray(){
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                mBoard.table.get(i).get(j).button.setBorder(new LineBorder(Color.lightGray));
            }
        }
    }
    //BISHOP,KNIGHT,ROOK,KING,QUEEN
    public JSlider letrehozzSlider(int szam){
        JSlider slider = new JSlider(JSlider.HORIZONTAL);
        slider.setMinorTickSpacing(1);
        slider.setMaximum(szam);
        slider.setPaintTicks(true);
        f=new JFrame();
        p=new JPanel();
        p.add(slider);
        f.add(p);
        f.setVisible(true);
        f.setContentPane(p);
        f.setSize(500,150);
        return slider;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(mBoard.visszaTolt)){
            visszaToltFajlbolTablat();
            flag=true;
        }
        if(e.getSource().equals(mBoard.replayStep)){
            JSlider slider =letrehozzSlider(fajldarabVisszaAd());
            slider.setMinimum(0);
            if(fajldarabVisszaAd()!=0) {
                slider.addChangeListener(e1 -> {
                    slider.setMaximum(fajldarabVisszaAd());
                    slider.repaint();
                    replayForward(slider.getValue(),slider);
                });
            }
            else{
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        }
        if(e.getSource().equals(mBoard.reset)){
            createDefaultGame();
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            szam=0;
            beleirTablaEsFelulIr();
            torolLepesFajlokatEsNullazSzamokat();
            letrehozzNullasFajlt();
        }
        setBorderLightGray();
        previousTableState=mBoard.getPieces();
        boolean stepped=false;
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if (e.getSource() == mBoard.table.get(i).get(j).button /*&& mBoard.table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT)*/&&mBoard.table.get(i).get(j).piece!=null) {
                    if (previousPosition.y != -1 && (previousPosition.y != i || previousPosition.x != j)) {
                        if (mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.ROOK)) {
                            Rook rook = (Rook) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : rook.validSteps) {
                                if (p.x == j && p.y == i) {
                                    setKingCheckedFalse();
                                    if(stepped=rook.step(mBoard, new Position(j, i))) {
                                        ++szam;
                                        beleirFajlDb(szam);
                                        beleirTablat(szam);
                                        beleirTablaEsFelulIr();
                                        previousStep = new Position(j, i);
                                    }
                                    break;
                                }
                            }

                        }
                        if (mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.KING)) {
                            King king = (King) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : king.validSteps) {
                                if (p.x == j && p.y == i) {
                                    if(stepped=king.step(mBoard, new Position(j, i))) {
                                        ++szam;
                                        beleirFajlDb(szam);
                                        beleirTablat(szam);
                                        beleirTablaEsFelulIr();
                                        previousStep = new Position(j, i);
                                    }
                                    break;
                                }
                            }
                        }
                        if (mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.PAWN)) {
                            Pawn pawn = (Pawn) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : pawn.validSteps) {
                                if (p.x == j && p.y == i) {
                                    setKingCheckedFalse();
                                    if(stepped= pawn.step(mBoard, new Position(j, i))) {
                                        ++szam;
                                        beleirFajlDb(szam);
                                        beleirTablat(szam);
                                        beleirTablaEsFelulIr();
                                        previousStep = new Position(j, i);
                                    }
                                    break;
                                }
                            }

                        }
                        if (mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.BISHOP)) {
                            Bishop bishop = (Bishop) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : bishop.validSteps) {
                                if (p.x == j && p.y == i) {
                                    setKingCheckedFalse();
                                    if(stepped=bishop.step(mBoard, new Position(j, i))) {
                                        ++szam;
                                        beleirFajlDb(szam);
                                        beleirTablat(szam);
                                        beleirTablaEsFelulIr();
                                        previousStep = new Position(j, i);
                                    }
                                    break;
                                }
                            }
                        }
                        if (mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.QUEEN)) {
                            Queen queen = (Queen) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : queen.validSteps) {
                                if (p.x == j && p.y == i) {
                                    setKingCheckedFalse();
                                    if(stepped= queen.step(mBoard, new Position(j, i))) {
                                        ++szam;
                                        beleirFajlDb(szam);
                                        beleirTablat(szam);
                                        beleirTablaEsFelulIr();
                                        previousStep = new Position(j, i);
                                    }
                                    break;
                                }
                            }
                        }
                        if (mBoard.table.get(previousPosition.y).get(previousPosition.x).piece.getType().equals(PIECETYPE.KNIGHT)) {
                            Knight knight = (Knight) mBoard.table.get(previousPosition.y).get(previousPosition.x).piece;
                            for (Position p : knight.validSteps) {
                                if (p.x == j && p.y == i) {
                                    setKingCheckedFalse();
                                    if(stepped=knight.step(mBoard, new Position(j, i))) {
                                        ++szam;
                                        beleirFajlDb(szam);
                                        beleirTablat(szam);
                                        beleirTablaEsFelulIr();
                                        previousStep = new Position(j, i);
                                    }
                                    break;
                                }
                            }

                        }

                        previousPosition.x = j;
                        previousPosition.y = i;
                    }
                }
            }
        }







        boolean detected=false;
        for(int i = 0;i< 8 && !detected;++i) {
            for (int j = 0; j < 8&& !detected; ++j) {
                if(e.getSource()==mBoard.table.get(i).get(j).button&&mBoard.table.get(i).get(j).piece!=null){
                    if(PIECETYPE.PAWN.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Pawn pawn = (Pawn)mBoard.table.get(i).get(j).piece;
                        pawn.getAvalaibleSteps(mBoard,true);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        detected=true;
                    }
                    if(PIECETYPE.KING.equals(mBoard.table.get(i).get(j).piece.getType())){
                        King king = (King)mBoard.table.get(i).get(j).piece;
                        king.getAvalaibleSteps(mBoard,true,true);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        detected=true;
                    }
                    if(PIECETYPE.ROOK.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Rook rook = (Rook)mBoard.table.get(i).get(j).piece;
                        rook.getAvalaibleSteps(mBoard,true);
                        //lépés
                        previousPosition.x=j;
                        previousPosition.y=i;
                        detected=true;
                    }
                    if(PIECETYPE.KNIGHT.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Knight knight = (Knight)mBoard.table.get(i).get(j).piece;
                        knight.getAvalaibleSteps(mBoard,true);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        detected=true;
                    }
                    if(PIECETYPE.BISHOP.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Bishop bishop = (Bishop)mBoard.table.get(i).get(j).piece;
                        bishop.getAvalaibleSteps(mBoard,true);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        detected=true;
                    }
                    if(PIECETYPE.QUEEN.equals(mBoard.table.get(i).get(j).piece.getType())){
                        Queen queen = (Queen) mBoard.table.get(i).get(j).piece;
                        queen.getAvalaibleSteps(mBoard,true);
                        previousPosition.x=j;
                        previousPosition.y=i;
                        detected=true;
                    }
                }
            }
        }
        if(stepped)
        {
            //mBoard.whiteTurn=!mBoard.whiteTurn;
            GLOBALSTATE state =getGameState();
            switch (state){
                case CHECK -> JOptionPane.showMessageDialog(mBoard.panel,"Sakk");
                case CHECKMATE -> {

                    JOptionPane.showMessageDialog(mBoard.panel, "SakkMatt");
                    mBoard.setPieces(new Vector<Piece>());
                    setBorderLightGray();
                    stepped=false;
                    createDefaultGame();
                }
                case STALEMATE -> {
                    JOptionPane.showMessageDialog(mBoard.panel, "Dontetlen");
                    mBoard.setPieces(new Vector<Piece>());
                    setBorderLightGray();
                    stepped=false;
                    createDefaultGame();
                }
                case INVALIDSTEP -> {
                    mBoard.setPieces(previousTableState);
                    mBoard.whiteTurn=!mBoard.whiteTurn;
                    stepped=false;
                    setBorderLightGray();
                    JOptionPane.showMessageDialog(mBoard.panel,"Invalid lepes");
                }
            }
            //mBoard.whiteTurn=!mBoard.whiteTurn;
        }
        if(previousStep != null && stepped) {
            mBoard.table.get(previousStep.y).get(previousStep.x).button.setBorder(new LineBorder(Color.CYAN));
        }
        if(detected)
        {
            return;
        }
        //melyik bábú lépett utoljára + //ezt a kövi órára cyan
        /*
        lep a babu
        az egy pozicio
        poziciot elmentem
        azt a poziciot kulon be/ki szinezni egy feltétel mellet
         */
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                mBoard.table.get(i).get(j).button.setBorder(new LineBorder(Color.lightGray));
            }
        }

        if(previousStep != null && stepped) {
            mBoard.table.get(previousStep.y).get(previousStep.x).button.setBorder(new LineBorder(Color.CYAN));
        }

    }

    void beleirTablaEsFelulIr(){//egesz tablat kiirja es mindig felulirja es ezt toltom vissza
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(mBoard.whiteTurn+ "\n");
            for(int i=0;i<mBoard.table.size();++i) {
                for (int j = 0; j < mBoard.table.get(i).size(); ++j) {
                    if(mBoard.table.get(i).get(j).piece.getColor()!=null && mBoard.table.get(i).get(j).piece.getType()!=PIECETYPE.DEFAULT) {
                        myWriter.write(mBoard.table.get(i).get(j).piece.getType().toString() + "," + "#" + Integer.toHexString(mBoard.table.get(i).get(j).piece.getColor().getRGB()).substring(2).toUpperCase() + "," + mBoard.table.get(i).get(j).piece.pos.x + "," + mBoard.table.get(i).get(j).piece.pos.y + "\n");
                    }
                }
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    void letrehozzNullasFajlt(){
        try {
            FileWriter myWriter = new FileWriter("0.txt");
            mBoard.whiteTurn=true;
            myWriter.write(mBoard.whiteTurn+ "\n");
            for(int i=0;i<mBoard.table.size();++i) {
                for (int j = 0; j < mBoard.table.get(i).size(); ++j) {
                    if(mBoard.table.get(i).get(j).piece.getColor()!=null && mBoard.table.get(i).get(j).piece.getType()!=PIECETYPE.DEFAULT) {
                        myWriter.write(mBoard.table.get(i).get(j).piece.getType().toString() + "," + "#" + Integer.toHexString(mBoard.table.get(i).get(j).piece.getColor().getRGB()).substring(2).toUpperCase() + "," + mBoard.table.get(i).get(j).piece.pos.x + "," + mBoard.table.get(i).get(j).piece.pos.y + "\n");
                    }
                }
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    void beleirTablat(int szam){//egesz tablat kiirja es mindig felulirja es ezt toltom vissza
        try{
            FileWriter myWriter = new FileWriter(szam+".txt");
            myWriter.write(mBoard.whiteTurn+ "\n");
            for(int i=0;i<mBoard.table.size();++i) {
                for (int j = 0; j < mBoard.table.get(i).size(); ++j) {
                    if(mBoard.table.get(i).get(j).piece.getColor()!=null && mBoard.table.get(i).get(j).piece.getType()!=PIECETYPE.DEFAULT) {
                        myWriter.write(mBoard.table.get(i).get(j).piece.getType().toString() + "," + "#" + Integer.toHexString(mBoard.table.get(i).get(j).piece.getColor().getRGB()).substring(2).toUpperCase() + "," + mBoard.table.get(i).get(j).piece.pos.x + "," + mBoard.table.get(i).get(j).piece.pos.y + "\n");
                    }
                }
            }
            myWriter.close();
        } catch(IOException e) {
            System.out.println(" ");
        }
    }
    void beleirFajlDb(int szam){
        try{
            FileWriter myWriter = new FileWriter("szam.txt");
            myWriter.write(Integer.toString(szam));
            myWriter.close();
        } catch(IOException e) {
            System.out.println(" ");
        }
    }
    Vector<String> visszaAdFajltartalom(){
        Vector<String> tartalom=new Vector<>();
        try {
            File myObj = new File("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\filename.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tartalom.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(" ");
            e.printStackTrace();
        }
        return tartalom;
    }
    Vector<String> visszaAdFajltartalom(String szam){
        Vector<String> tartalom=new Vector<>();
        try {
            File myObj = new File("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\"+szam+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tartalom.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(" ");
            e.printStackTrace();
        }
        return tartalom;
    }
    Vector<Vector<String>> visszaAdFajlokTartalmat() {
        Vector<Vector<String>> fajlokTartalma=new Vector<Vector<String>>();
        Vector<String> tartalom=new Vector<>();
        try {
            File myObj = new File("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\szam.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tartalom.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(" ");
            e.printStackTrace();
        }
        Integer fajlDb=Integer.parseInt(tartalom.get(0));
        for(Integer i = 0;i<=fajlDb;++i){
            Vector<String> aktualisFajl=visszaAdFajltartalom(i+"");
            fajlokTartalma.add(aktualisFajl);
        }
        return fajlokTartalma;
    }
    void torolLepesFajlokatEsNullazSzamokat(){
        Vector<String> tartalom=new Vector<>();
        try {
            File myObj = new File("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\szam.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tartalom.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(" ");
            e.printStackTrace();
        }
        Integer fajlDb=Integer.parseInt(tartalom.get(0));
        for(int i = 0;i<=fajlDb;++i){
            File myObj = new File(i +".txt");
            myObj.delete();
        }
        beleirFajlDb(0);
    }
    Integer fajldarabVisszaAd(){
        Vector<String> tartalom=new Vector<>();
        try {
            File myObj = new File("C:\\Users\\sabotamas0\\Documents\\repos\\ChessGame\\szam.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tartalom.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(" ");
            e.printStackTrace();
        }
        return Integer.parseInt(tartalom.get(0));
    }
    void replayForward(int szam,JSlider s){
        Vector<Vector<String>> fajlokTartalma = visszaAdFajlokTartalmat();
        Vector<String> asd = fajlokTartalma.get(szam);
        visszaToltFajlbolTablat(asd);

    }
    void visszaToltFajlbolTablat(Vector<String> fajltartalma){
        mBoard.setPieces(new Vector<Piece>());
        Vector<Piece> reloadedPieces=new Vector<>();
        mBoard.whiteTurn= Boolean.parseBoolean(fajltartalma.get(0));
        for(int i = 0;i<fajltartalma.size();++i){
            String[] arrOfStr = fajltartalma.get(i).split(",");
            for(int j = 0;j<=arrOfStr.length;++j){
                switch (arrOfStr[0]) {
                    case "PAWN" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Pawn pawn=new Pawn(Color.white);
                            pawn.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(pawn);
                        }
                        else {
                            Pawn pawn=new Pawn(Color.black);
                            pawn.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(pawn);
                        }
                    }
                    case "ROOK" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Rook rook=new Rook(Color.white);
                            rook.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(rook);
                        }
                        else {
                            Rook rook=new Rook(Color.black);
                            rook.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(rook);
                        }
                    }
                    case "KNIGHT" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Knight knight=new Knight(Color.white);
                            knight.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(knight);
                        }
                        else {
                            Knight knight=new Knight(Color.black);
                            knight.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(knight);
                        }
                    }
                    case "BISHOP" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Bishop bishop = new Bishop(Color.white);
                            bishop.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(bishop);
                        }
                        else {
                            Bishop bishop = new Bishop(Color.black);
                            bishop.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(bishop);
                        }
                    }
                    case "QUEEN" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Queen queen = new Queen(Color.white);
                            queen.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(queen);
                        }
                        else {
                            Queen queen = new Queen(Color.black);
                            queen.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(queen);
                        }
                    }
                    case "KING" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            King king = new King(Color.white);
                            king.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(king);
                        }
                        else {
                            King king = new King(Color.black);
                            king.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(king);
                        }
                    }
                }
            }
        }
        for(int i=0;i<reloadedPieces.size();++i){
            Position p =reloadedPieces.get(i).pos;
            mBoard.table.get(p.y).get(p.x).piece=reloadedPieces.get(i);
            mBoard.table.get(p.y).get(p.x).button.setIcon(mBoard.table.get(p.y).get(p.x).piece.picture);
        }
    }
    void visszaToltFajlbolTablat(){
        mBoard.setPieces(new Vector<Piece>());
        Vector<String> fajltartalma= visszaAdFajltartalom();
        Vector<Piece> reloadedPieces=new Vector<>();
        mBoard.whiteTurn= Boolean.parseBoolean(fajltartalma.get(0));
        for(int i = 1;i<fajltartalma.size();++i){
            String[] arrOfStr = fajltartalma.get(i).split(",");
            for(int j = 0;j<=arrOfStr.length;++j){
                switch (arrOfStr[0]) {
                    case "PAWN" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Pawn pawn=new Pawn(Color.white);
                            pawn.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(pawn);
                        }
                        else {
                            Pawn pawn=new Pawn(Color.black);
                            pawn.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(pawn);
                        }
                    }
                    case "ROOK" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Rook rook=new Rook(Color.white);
                            rook.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(rook);
                        }
                        else {
                            Rook rook=new Rook(Color.black);
                            rook.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(rook);
                        }
                    }
                    case "KNIGHT" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Knight knight=new Knight(Color.white);
                            knight.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(knight);
                        }
                        else {
                            Knight knight=new Knight(Color.black);
                            knight.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(knight);
                        }
                    }
                    case "BISHOP" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Bishop bishop = new Bishop(Color.white);
                            bishop.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(bishop);
                        }
                        else {
                            Bishop bishop = new Bishop(Color.black);
                            bishop.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(bishop);
                        }
                    }
                    case "QUEEN" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            Queen queen = new Queen(Color.white);
                            queen.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(queen);
                        }
                        else {
                            Queen queen = new Queen(Color.black);
                            queen.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(queen);
                        }
                    }
                    case "KING" -> {
                        if (arrOfStr[1].equals("#FFFFFF")) {
                            King king = new King(Color.white);
                            king.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(king);
                        }
                        else {
                            King king = new King(Color.black);
                            king.pos = new Position(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[3]));
                            reloadedPieces.add(king);
                        }
                    }
                }
            }
        }
        for(int i=0;i<reloadedPieces.size();++i){
            Position p =reloadedPieces.get(i).pos;
            mBoard.table.get(p.y).get(p.x).piece=reloadedPieces.get(i);
            mBoard.table.get(p.y).get(p.x).button.setIcon(mBoard.table.get(p.y).get(p.x).piece.picture);
        }
    }
}
