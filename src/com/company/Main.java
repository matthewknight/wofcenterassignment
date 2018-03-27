package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        WOFCenterStorage wofCenterStorage = new WOFCenterStorage();
        wofCenterStorage.insertOwner("knightpmatthew@gmail.com", "Matthew", "Knight",
                "password123");
        //wofCenterStorage.registerVehicle("knightpmatthew@gmail.com", //tbd);
    }
}
