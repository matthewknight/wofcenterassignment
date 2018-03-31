package steps;
import Core.Owner;
import Core.WOFCenterStorage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class OwnerSubscriptionTestSteps {
    private Connection connection;
    private WOFCenterStorage wofCenterStorage;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Given("^I am connected to the empty WOF database$")
    public void iAmConnectedToTheEmptyWOFDatabase() throws Throwable {
        String url = "jdbc:sqlite:wofcenter.sqlite";
        connection = DriverManager.getConnection(url);
        wofCenterStorage = new WOFCenterStorage();
        wofCenterStorage.clearDatabase();
    }

    @When("^I input the details of myself$")
    public void iInputTheDetailsOfMyself() throws Throwable {
        Owner testOwner = new Owner("John",
                "Smith", "cucumber@test.com", "secret123");

        // Set the standard output to a string to be later checked for the display message
        System.setOut(new PrintStream(outContent));

        // Add the new owner
        wofCenterStorage.registerOwner(testOwner);

        // Get the last line of the console output
        String[] lines = outContent.toString().split("\n");
        String consoleMessage = lines[lines.length - 1];

        // Check for the correctly displayed message
        Assert.assertEquals("Successfully added new owner.", consoleMessage);
        System.setOut(System.out);
    }

    @Then("^the database should contains my inputted details$")
    public void theDatabaseShouldContainsMyInputtedDetails() throws Throwable {
        Owner foundOwner = wofCenterStorage.getOwner("cucumber@test.com");
        Assert.assertEquals(foundOwner.getFirstName(), "John");
        Assert.assertEquals(foundOwner.getLastName(), "Smith");
        Assert.assertEquals(foundOwner.getPassword(), "secret123");
    }

    @When("^I input my details with the same email again$")
    public void iInputMyDetailsWithTheSameEmailAgain() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Owner testOwner = new Owner("John",
                "Smith", "cucumber@test.com", "secret123");

        // Set the standard output to a string to be later checked for the display message
        System.setOut(new PrintStream(outContent));

        // Add the new owner
        wofCenterStorage.registerOwner(testOwner);

    }

    @Then("^the system should reject my repeated details$")
    public void theSystemShouldRejectMyRepeatedDetails() throws Throwable {
        // Get the last line of the console output
        String[] lines = outContent.toString().split("\n");
        String consoleMessage = lines[lines.length - 1];

        // Check for the correctly displayed message
        Assert.assertEquals("Not a unique email, failed to add owner.", consoleMessage);
        System.setOut(System.out);
    }
}
