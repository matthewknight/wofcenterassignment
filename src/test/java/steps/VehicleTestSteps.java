package steps;
import Core.Owner;
import Core.Vehicle;
import Core.WOFCenterStorage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class VehicleTestSteps {
    private Connection connection;
    private WOFCenterStorage wofCenterStorage;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Given("^I have connected to the empty WOF database$")
    public void iAmConnectedToTheEmptyWOFDatabase() throws Throwable {
        String url = "jdbc:sqlite:wofcenter.sqlite";
        connection = DriverManager.getConnection(url);
        wofCenterStorage = new WOFCenterStorage();
        wofCenterStorage.clearDatabase();
    }

    @Given("^I have already registered as a user$")
    public void iHaveAlreadyRegisteredAsAUser() throws Throwable {
        Owner testOwner = new Owner("Matthew",
                "Knight", "vehicletest@cucumber.com", "password123");

        // Add the new owner
        wofCenterStorage.registerOwner(testOwner);
    }

    @When("^I input the details of my vehicle$")
    public void iInputTheDetailsOfMyVehicle() throws Throwable {
        Vehicle testVehicle = new Vehicle("ABC123", "Lexus", "ES300", "petrol",
                120000, 1998, "vehicletest@cucumber.com");

        // Set the standard output to a string to be later checked for the display message
        System.setOut(new PrintStream(outContent));

        // Add the new owner
        wofCenterStorage.registerVehicle(testVehicle);

        // Get the last line of the console output
        String[] lines = outContent.toString().split("\n");
        String consoleMessage = lines[lines.length - 1];

        // Check for the correctly displayed message
        Assert.assertEquals("Successfully added new vehicle.", consoleMessage);
        System.setOut(System.out);
    }

    @Then("^the database should contains the details of my vehicle$")
    public void theDatabaseShouldContainsTheDetailsOfMyVehicle() throws Throwable {
        Vehicle foundVehicle = wofCenterStorage.getVehicle("ABC123");
        Assert.assertEquals(foundVehicle.getMake(), "Lexus");
        Assert.assertEquals(foundVehicle.getModel(), "ES300");
        Assert.assertEquals(foundVehicle.getFuelType(), "petrol");
        Assert.assertEquals(foundVehicle.getOdometer(), 120000);
        Assert.assertEquals(foundVehicle.getManufactureYear(), 1998);
        Assert.assertEquals(foundVehicle.getOwnerEmail(), "vehicletest@cucumber.com");
    }

    @When("^I input the details of my vehicle with a non registered email$")
    public void iInputTheDetailsOfMyVehicleWithANonRegisteredEmail() throws Throwable {
        Vehicle testVehicle = new Vehicle("XYZ987", "Ferrari", "GTC4Lucco", "petrol",
                2400, 2015, "yeetman@hotmail.com");

        // Set the standard output to a string to be later checked for the display message
        System.setOut(new PrintStream(outContent));

        // Add the new owner
        wofCenterStorage.registerVehicle(testVehicle);
    }

    @Then("^the database should reject my new vehicle$")
    public void theDatabaseShouldRejectMyNewVehicle() throws Throwable {
        // Get the last line of the console output
        String[] lines = outContent.toString().split("\n");
        String consoleMessage = lines[lines.length - 1];

        // Check for the correctly displayed message
        Assert.assertEquals("Not a valid plate/owner email, failed to add vehicle.", consoleMessage);
        System.setOut(System.out);
    }

    @When("^I input the details of my vehicle again$")
    public void iInputTheDetailsOfMyVehicleAgain() throws Throwable {
        Vehicle testVehicle = new Vehicle("ABC123", "Lexus", "ES300", "petrol",
                120000, 1998, "vehicletest@cucumber.com");

        // Set the standard output to a string to be later checked for the display message
        System.setOut(new PrintStream(outContent));

        // Add the new owner
        wofCenterStorage.registerVehicle(testVehicle);
    }
}
