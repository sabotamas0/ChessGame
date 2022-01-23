package com.company;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.awt.event.KeyEvent;
import javax.swing.JSlider;
public class Board extends JFrame{
    //Vector<CheckingPiece> potentialCheckingPieces;
    public JPanel panel;
    Vector<Vector<ButtonWithPiece>> table;
    JButton reset = new JButton();
    JButton visszaTolt = new JButton();
    JButton replayStep = new JButton();
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
        this.setJMenuBar(createMenuBar());
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
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu, submenu, submenu2, submenu3;
        menu=new JMenu("Menu");
        submenu=new JMenu("Reset Game");
        submenu2=new JMenu("Reload From File");
        submenu3=new JMenu("Replay Steps With Slider");
        reset.setText("Reset");
        submenu.add(reset);
        visszaTolt.setText("Reload");
        submenu2.add(visszaTolt);
        replayStep.setText("ReplaySteps");
        submenu3.add(replayStep);
        menu.add(submenu);
        menu.add(submenu2);
        menu.add(submenu3);
        menuBar.add(menu);
        return menuBar;
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
        reset.addActionListener(al);
        visszaTolt.addActionListener(al);
        replayStep.addActionListener(al);
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                table.get(i).get(j).button.addActionListener(al);

            }
        }
    }
    public void addKeyListener(KeyListener l){
        for(int i = 0;i< 8;++i) {
            for (int j = 0; j < 8; ++j) {
                table.get(i).get(j).button.addKeyListener(l);
            }
        }
    }
    void popUp()
    {
        JOptionPane.showMessageDialog(panel,"Sakk");
    }

}
