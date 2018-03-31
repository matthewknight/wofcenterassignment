package Core;

public class Main {

    public static void main(String[] args) {
        WOFCenterStorage wofCenterStorage = new WOFCenterStorage();
        Owner testOwner = new Owner("Matthew", "Knight","knightpmatthew@gmail.com",
                "password123");
        wofCenterStorage.registerOwner(testOwner);

        Vehicle testVehicle = new Vehicle("FYE964", "Toyota", "Vitz", "petrol",
                140000, 2005, "knightpmatthew@gmail.com");
        wofCenterStorage.registerVehicle(testVehicle);
    }
}
