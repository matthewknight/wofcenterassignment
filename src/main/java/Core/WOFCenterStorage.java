package Core;

import java.sql.*;

public class WOFCenterStorage {

    // Path to the database
    private static final String db_url = "jdbc:sqlite:wofcenter.sqlite";

    /**
     * Gets a Connection for the current database
     * @return A Connection object to the database or null if no connection could be made
     */
    private static Connection getConnection() throws SQLException {
        // Return the DB Connection object for other methods to perform queries
        return DriverManager.getConnection(db_url);
    }

    /**
     * Clears all rows in the WOF database
     * @return Whether the operation was successful
     */
    public boolean clearDatabase() {
        try {
            Connection dbConnection = getConnection();
            // Check this connection is valid
            assert (dbConnection != null);

            // Run statements that delete all rows from the 2 tables
            dbConnection.createStatement().execute("DELETE FROM Owner");
            dbConnection.createStatement().execute("DELETE FROM Vehicle");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the selected owner from storage
     * @param email Owner email to query
     * @return Owner on successful search, null on empty search
     */
    public Owner getOwner(String email) {
        // Check for required details
        assert (email != null);
        email = "'" + email + "'";

        // Form SQL statement with wildcards
        String sqlSelect = "SELECT * FROM Owner WHERE email = " + email;

        // Set connection object here outside of try/catch scope to close finally
        Connection dbConnection = null;
        try {
            // Grab a connection to the database to perform the query
            dbConnection = getConnection();

            // Check this connection is valid
            assert (dbConnection != null);

            // Execute the statement and form a result set obj
            ResultSet rs = dbConnection.createStatement().executeQuery(sqlSelect);

            // Check if the search is empty
            if (!rs.next()) {
                return null;
            }

            // Parse the result set details into a new Owner object to return
            return new Owner(rs.getString("firstName"), rs.getString("lastName"),
                    rs.getString("email"), rs.getString("password"));
        } catch (SQLException e) {
            // Specify error code for a primary key violation (ie when a non-unique email is added)
            int ConstraintViolationErrorCode = 19;

            // Check for the specific error code, else print an error stacktrace
            if (e.getErrorCode() == ConstraintViolationErrorCode) {
                System.out.println("Not a unique email, failed to add owner.");
            } else {
                e.printStackTrace();
            }
            return null;
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
     * Returns the selected vehicle from storage
     * @param plate License plate to query
     * @return Vehicle on successful search, null on empty search
     */
    public Vehicle getVehicle(String plate) {
        // Check for required details
        assert (plate != null);
        plate = "'" + plate + "'";

        // Form SQL statement with wildcards
        String sqlSelect = "SELECT * FROM Vehicle WHERE plate = " + plate;

        // Set connection object here outside of try/catch scope to close finally
        Connection dbConnection = null;
        try {
            // Grab a connection to the database to perform the query
            dbConnection = getConnection();

            // Check this connection is valid
            assert (dbConnection != null);

            // Execute the statement and form a result set obj
            ResultSet rs = dbConnection.createStatement().executeQuery(sqlSelect);

            // Check if the search is empty
            if (!rs.next()) {
                return null;
            }

            // Parse the result set details into a new Vehicle object to return
            return new Vehicle(rs.getString("plate"), rs.getString("make"), rs.getString("model"),
                    rs.getString("fuelType"), rs.getInt("odometer"), rs.getInt("manufactureYear"),
                    rs.getString("ownerEmail"));
        } catch (SQLException e) {
            // Specify error code for a primary key violation (ie when a non-unique email is added)
            int ConstraintViolationErrorCode = 19;

            // Check for the specific error code, else print an error stacktrace
            if (e.getErrorCode() == ConstraintViolationErrorCode) {
                System.out.println("Not a unique email, failed to add owner.");
            } else {
                e.printStackTrace();
            }
            return null;
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
     * Attempts to insert a new Owner into the current database
     *
     * @param newOwner New owner to add to the DB
     * @return Whether the operation was successful
     */
    public boolean registerOwner(Owner newOwner) {
        assert(newOwner != null);
        // Check for required details
        assert (newOwner.getEmail() != null && newOwner.getFirstName() != null
                && newOwner.getLastName() != null && newOwner.getPassword() != null);

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
            insertStatement.setString(1, newOwner.getEmail());
            insertStatement.setString(2, newOwner.getFirstName());
            insertStatement.setString(3, newOwner.getLastName());
            insertStatement.setString(4, newOwner.getPassword());

            // Execute the statement
            insertStatement.executeUpdate();

            // Display success message
            System.out.println("Successfully added new owner.");
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
     * @param newVehicle New vehicle to store
     * @return Whether the operation was successful
     */
    public boolean registerVehicle(Vehicle newVehicle) {
        // Check for required details
        assert (newVehicle != null);

        assert (newVehicle.getOwnerEmail() != null && newVehicle.getPlate() != null && newVehicle.getMake() != null &&
                newVehicle.getModel() != null && newVehicle.getOdometer() >= 0 &&
                newVehicle.getManufactureYear() >= 1900);

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
            insertStatement.setString(1, newVehicle.getPlate());
            insertStatement.setString(2, newVehicle.getMake());
            insertStatement.setString(3, newVehicle.getModel());
            insertStatement.setString(4, newVehicle.getFuelType());
            insertStatement.setInt(5, newVehicle.getOdometer());
            insertStatement.setInt(6, newVehicle.getManufactureYear());
            insertStatement.setString(7, newVehicle.getOwnerEmail());

            // Execute the statement
            insertStatement.executeUpdate();

            // Display success message
            System.out.println("Successfully added new vehicle.");
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
