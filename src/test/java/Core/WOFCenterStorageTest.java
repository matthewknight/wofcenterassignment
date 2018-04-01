package Core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WOFCenterStorageTest {
    private WOFCenterStorage wofCenterStorage = new WOFCenterStorage();

    @Before
    public void setUp() throws Exception {
        wofCenterStorage.clearDatabase();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Clear database
     */
    @Test
    public void clearDatabase() {
        assertTrue(wofCenterStorage.clearDatabase());
    }

    /**
     * Add a valid owner
     */
    @Test
    public void addOwner() {
        Owner testOwner = new Owner("Bobby", "Flame",
                "bobby123@hotmail.com", "password123");
        assertTrue(wofCenterStorage.registerOwner(testOwner));
    }

    /**
     * Add a valid owner, get the added owner and check for the same name
     */
    @Test
    public void addOwnerGetOwner() {
        Owner testOwner = new Owner("Bobby", "Flame",
                "bobby123@hotmail.com", "password123");
        assertTrue(wofCenterStorage.registerOwner(testOwner));
        Owner fetchedOwner = wofCenterStorage.getOwner("bobby123@hotmail.com");
        assertEquals(testOwner.getFirstName(), fetchedOwner.getFirstName());
    }

    /**
     * Add an existing owner
     */
    @Test
    public void addOwnerTwice() {
        Owner testOwner = new Owner("Bobby", "Flame",
                "bobby123@hotmail.com", "password123");
        assertTrue(wofCenterStorage.registerOwner(testOwner));
        assertFalse(wofCenterStorage.registerOwner(testOwner));
    }


    /**
     * Add an owner then a vehicle under that email
     */
    @Test
    public void addRegisteredEmailVehicle() {
        Owner testOwner = new Owner("Bobby", "Flame",
                "bobby123@hotmail.com", "password123");
        wofCenterStorage.registerOwner(testOwner);
        Vehicle testVehicle = new Vehicle("TESTPLATE", "Nissan", "350z", "petrol",
                56000, 2001, "bobby123@hotmail.com");
        assertTrue(wofCenterStorage.registerVehicle(testVehicle));
    }

    /**
     * Add an owner then a vehicle with a non registered email
     */
    @Test
    public void addNonRegisteredEmailVehicle() {
        Owner testOwner = new Owner("Bobby", "Flame",
                "bobby123@hotmail.com", "password123");
        wofCenterStorage.registerOwner(testOwner);
        Vehicle testVehicle = new Vehicle("TESTPLATE", "Nissan", "350z", "petrol",
                56000, 2001, "yeetman312@uclive.ac.nz");
        assertFalse(wofCenterStorage.registerVehicle(testVehicle));
    }
}