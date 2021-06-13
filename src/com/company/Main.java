package com.company;
import com.company.Board;

public class Main {

    public static void main(String[] args) {
        Board b = new Board();
        GameLogic gl =new GameLogic(b);
        //b.popUp();
        //TODO
        /*
        feladatok : kijavItani a parasztot

        + leIrni hogy hogyan mondanád meg egyenkét a bábukról
        hogy sakkot adnak épp az ellenfél királyának
        Pawn:
        -csak ha nem elotte van a király
        BISHOP,KNIGHT,ROOK,KING,QUEEN:
        -ha a lépéseik kozott benne van a király elhelyezkedése

        + azon gondolkozni hogy hogyan mondod meg hogy matt van e épp
        -ha a király nem tud valid lépést tenni hogy kikeruljon a sakkból vagy
        nem tud más babu a király segitségére lenni pl:ellenfél elé állni
        https://www.chess.com/terms/checkmate-chess

        sakk
        matt
        berosálás
        paraszt menet közbeni ütése(en passant)
        paraszt beér akkor lecserélni
        miértekben gondolkodni
        Állapottér
        * */
    }
}
