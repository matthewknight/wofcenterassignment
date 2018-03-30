package steps;

import Core.WOFCenterStorage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;

public class VehicleTestSteps {
    @Given("^I am connected to the WOF database$")
    public void iAmConnectedToTheWOFDatabase() throws Throwable {
        String url = "jdbc:sqlite:wofcenter.sqlite";
        Connection connection = DriverManager.getConnection(url);
    }

    @When("^I input the details of my vehicle$")
    public void iInputTheDetailsOfMyVehicle() throws Throwable {
        WOFCenterStorage wofCenterStorage = new WOFCenterStorage();
        throw new PendingException();
    }

    @Then("^the database should contains the details of my vehicle$")
    public void theDatabaseShouldContainsTheDetailsOfMyVehicle() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
