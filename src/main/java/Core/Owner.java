package Core;

/**
 * Holds simple details about a vehicle owner that registers in the WoF database
 */
public class Owner {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Owner(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
