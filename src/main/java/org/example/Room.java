package org.example;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    ArrayList<User> users = new ArrayList<>();
    String roomID;
    char [][] board;
    int index = 1;
    char winner;

    public Room(String roomID) {
        this.roomID = String.valueOf(index);
        this.board = new char[][]{{' ', ' ',' '}, {' ', ' ',' '}, {' ', ' ',' '}};
        index++;
    }


}
