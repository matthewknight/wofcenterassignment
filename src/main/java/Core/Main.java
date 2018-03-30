package Core;

public class Main {

    public static void main(String[] args) {
        WOFCenterStorage wofCenterStorage = new WOFCenterStorage();
        wofCenterStorage.registerOwner("knightpmatthew@gmail.com", "Matthew", "Knight",
                "password123");
        wofCenterStorage.registerVehicle("w@gmacom", "yyyyyy", "Toyota",
                "Vitz RSX", "petrol", 146000, 2005);
    }
}
