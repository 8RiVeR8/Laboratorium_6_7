package org.example;

import java.io.Serializable;

public class User implements Serializable {
    int ID;
    char[][] table;
    boolean hasStarted;
    int [] statistic;

    public User(int ID) {
        this.ID = ID;
    }
}
