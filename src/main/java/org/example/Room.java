package org.example;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    ArrayList<String> users = new ArrayList<>();
    private String roomID;
    private char [][] board;
    private int index = 1;
    private char winner;

    public Room(String roomID, char[][] board) {
        this.roomID = String.valueOf(index);
        this.board = new char[][]{{' ', ' ',' '}, {' ', ' ',' '}, {' ', ' ',' '}};
        index++;
    }


}
