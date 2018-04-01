package Core;

/**
 * Holds simple details about a vehicle that registers in the WoF database
 */
public class Vehicle {
    private String plate;
    private String make;
    private String model;
    private String fuelType;
    private int odometer;
    private int manufactureYear;
    private String ownerEmail;

    // Simple vehicle constructor
    public Vehicle(String plate, String make, String model, String fuelType, int odometer,
                   int manufactureYear, String ownerEmail) {
        this.plate = plate;
        this.make = make;
        this.model = model;
        this.fuelType = fuelType;
        this.odometer = odometer;
        this.manufactureYear = manufactureYear;
        this.ownerEmail = ownerEmail;
    }

    public String getPlate() {
        return plate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getOdometer() {
        return odometer;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }
}
