package com.company;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import com.company.ButtonWithPiece;
import com.company.PieceInterface;
public class Board extends JFrame {
    //Vector<CheckingPiece> potentialCheckingPieces;
    public JPanel panel;
    Vector<Vector<ButtonWithPiece>> table;

    public boolean whiteTurn;
    public Board(){

        String ablakFejlecSzoveg="Sakk";
        whiteTurn=true;
        this.setVisible(true);
        this.setSize(400,400);
        this.setTitle(ablakFejlecSzoveg);
        panel=new JPanel();
        panel.setLayout(new GridLayout(8,8));
        table = new Vector<Vector<ButtonWithPiece>>();
        for(int i = 0;i< 8;++i){
            Vector<ButtonWithPiece> row=new Vector<ButtonWithPiece>();
            for(int j = 0;j< 8;++j){
                row.add(new ButtonWithPiece(new JButton(),new Piece()));
            }
            table.add(row);
        }
        fillColor();


        for(int i = 0;i< 8;++i){
            for(int j = 0;j< 8;++j){
                panel.add(table.get(i).get(j).button);
            }
        }

        this.setContentPane(panel);
    }
    Vector<Piece> getPieces(){
        Vector<Piece> pieces=new Vector<Piece>();
        for(int i = 0;i< 8;++i){
            for(int j = 0;j< 8;++j){
                //if(!table.get(i).get(j).piece.getType().equals(PIECETYPE.DEFAULT)){
                if(table.get(i).get(j).piece!=null)
                {
                    pieces.add(table.get(i).get(j).piece.clone());
                }
            }
        }
        return pieces;
    }
    void setPieces(Vector<Piece> pieces){
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                table.get(i).get(j).piece=null;
                table.get(i).get(j).button.setIcon(null);
            }
        }
        for(int i=0;i<pieces.size();++i){
            Position p =pieces.get(i).pos;
            if(p.x!=-1)
            {
                table.get(p.y).get(p.x).piece=pieces.get(i);
                table.get(p.y).get(p.x).button.setIcon(table.get(p.y).get(p.x).piece.picture);
            }

        }
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                if(table.get(i).get(j).piece==null)
                {
                    table.get(i).get(j).piece=new Piece();
                    table.get(i).get(j).button.setIcon(null);
                }
            }
        }
    }
    void fillColor(){
        boolean white=true;
        Color color;
        for(int i = 0;i< 8;++i){
            for(int j = 0;j< 8;++j){
               if(white){
                   color=Color.LIGHT_GRAY;
                   white=false;
               }
               else{
                   color=Color.DARK_GRAY;
                   white=true;
               }
               table.get(i).get(j).button.setBackground(color);
            }
            white=!white;
        }
    }
    public void addActionListener(ActionListener al){
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                table.get(i).get(j).button.addActionListener(al);
            }
        }
    }
    void popUp()
    {
        JOptionPane.showMessageDialog(panel,"Sakk");
    }

}
