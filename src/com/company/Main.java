package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        WOFCenterStorage wofCenterStorage = new WOFCenterStorage();
        wofCenterStorage.registerOwner("knightpmatthew@gmail.com", "Matthew", "Knight",
                "password123");
        wofCenterStorage.registerVehicle("w@gmacom", "FYE964", "Toyota",
                "Vitz RSX", "petrol", 146000, 2005);
    }
}
