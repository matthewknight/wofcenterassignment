package Core;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class WOFCenterStorage {

    private static final String db_url = "jdbc:sqlite:wofcenter.sqlite";
    /**
     * Gets a Connection for the current database
     * @return A Connection object to the database or null if no connection could be made
     */
    private static Connection getConnection() throws SQLException {
        // Path to the WOF center database

        System.out.println("Getting connection to: " + db_url);

        // Return the DB Connection object for other methods to perform queries

        Connection conn = DriverManager.getConnection(db_url);

        return conn;
    }

    /**
     * Attempts to insert a new Owner into the current database
     *
     * @param email Email of new owner (must be unique)
     * @param firstName First name of new owner
     * @param lastName Last name of new owner
     * @param password Password of user
     * @return Whether the operation was successful
     */
    public boolean registerOwner(String email, String firstName, String lastName,
                                   String password) {
        // Check for required details
        assert (email != null && firstName != null && lastName != null && password != null);

        System.out.println(String.format("Creating a new owner: %s (%s %s)", email, firstName, lastName));
        // Form SQL statement with wildcards
        String sqlInsert = "INSERT INTO Owner (email, firstName, lastName, password) VALUES (?, ?, ?, ?)";

        // Set connection object here outside of try/catch scope to close finally
        Connection dbConnection = null;
        try {
            // Grab a connection to the database to perform the insertion
            dbConnection = getConnection();

            // Check this connection is valid
            assert (dbConnection != null);

            // Create a PreparedStatement to specify values onto
            PreparedStatement insertStatement = dbConnection.prepareStatement(sqlInsert);

            // Insert the Owner's details into the statement
            insertStatement.setString(1, email);
            insertStatement.setString(2, firstName);
            insertStatement.setString(3, lastName);
            insertStatement.setString(4, password);

            // Execute the statement and print how many rows were affected
            System.out.println("Rows added: " + insertStatement.executeUpdate());
            return true;
        } catch (SQLException e) {
            // Specify error code for a primary key violation (ie when a non-unique email is added)
            int ConstraintViolationErrorCode = 19;

            // Check for the specific error code, else print an error stacktrace
            if (e.getErrorCode() == ConstraintViolationErrorCode) {
                System.out.println("Not a unique email, failed to add owner.");
            } else {
                e.printStackTrace();
            }
            return false;
        } finally {
            // Attempt to close the created connection
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + dbConnection);
            }
        }
    }

    /**
     * Inserts a new vehicle with the corresponding owner's email
     *
     * @param email Vehicle owner's email
     * @param plate Vehicle's license plate
     * @param carMake Vehicle's make
     * @param carModel Vehicle's model
     * @param fuelType Vehicle's fuel type
     * @param odometerReading Vehicle's mileage
     * @param manufactureYear Vehicle's year of manufacture
     * @return Whether the operation was successful
     */
    public boolean registerVehicle(String email, String plate, String carMake, String carModel, String fuelType,
                               int odometerReading, int manufactureYear) {
        // Check for required details
        assert (email != null && plate != null && carMake != null && carModel != null && odometerReading >= 0 &&
            manufactureYear >= 1900);

        // Print out attempted action to console for debugging purposes
        System.out.println(String.format("Attempting to create a new vehicle: %s %s: %s of owner %s", carMake,
                carModel, plate, email));

        // Form SQL statement with wildcards
        String sqlInsert = "INSERT INTO Vehicle (plate, make, model, fuelType, odometer, manufactureYear, ownerEmail) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Set connection object here outside of try/catch scope to close finally
        Connection dbConnection = null;

        try {
            // Grab a connection to the database to perform the insertion
            dbConnection = getConnection();

            // Check this connection is valid
            assert (dbConnection != null);

            // Check that the statement will comply with foreign key constraints
            dbConnection.createStatement().execute("PRAGMA foreign_keys = ON");

            // Create a PreparedStatement to specify values onto
            PreparedStatement insertStatement = dbConnection.prepareStatement(sqlInsert);

            // Insert the Owner's details into the statement
            insertStatement.setString(1, plate);
            insertStatement.setString(2, carMake);
            insertStatement.setString(3, carModel);
            insertStatement.setString(4, fuelType);
            insertStatement.setInt(5, odometerReading);
            insertStatement.setInt(6, manufactureYear);
            insertStatement.setString(7, email);

            // Execute the statement and print how many rows were affected
            System.out.println("Rows added: " + insertStatement.executeUpdate());
            return true;
        } catch (SQLException e) {
            // Specify error code for a primary key violation (ie when a non-unique email is added)
            int ConstraintViolationErrorCode = 19;

            // Check for the specific error code, else print an error stacktrace
            if (e.getErrorCode() == ConstraintViolationErrorCode) {
                System.out.println("Not a valid plate/owner email, failed to add vehicle.");
            } else {
                e.printStackTrace();
            }
            return false;
        } finally {
            // Attempt to close the created connection
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + dbConnection);
            }
        }
    }
}
