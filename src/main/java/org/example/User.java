package org.example;

import java.io.Serializable;

public class User implements Serializable {
    private String ID;
    private char[][] table;
    private boolean hasStarted;
    private int [] statistic;

    public User(String ID) {
        this.ID = ID;
    }
}
