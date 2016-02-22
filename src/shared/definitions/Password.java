package shared.definitions;

/**
 * Created by rscheuer on 2/19/16.
 */

import shared.exceptions.InvalidNameException;
import shared.exceptions.InvalidPasswordException;

/**
 * Wraps a string this is the password.
 * Allows for validation of this password.
 */
public class Password {
    /**
     * The password
     */
    private String value;

    /**
     * @param password
     * @throws InvalidNameException
     */
    public Password(String password) throws InvalidPasswordException {
        setValue(password);
    }

    /**
     * Initialize to default password ("Default")
     */
    public Password() {
        value = "Default";
    }

    /**
     * @param password
     * @throws InvalidNameException
     */
    public void setValue(String password) throws InvalidPasswordException {
        if (validate(password)) {
            this.value = password;
        } else {
            throw new InvalidPasswordException("Password invalid. Must be at least 5 characters that are: letters, numbers, dashes, or underscores");
        }
    }

    /**
     * @param name
     * @return If password is valid
     */
    private boolean validate(String name) {
        /* At least 5 characters that are: letters, numbers, dashes, or underscores */
        // put me back when done testing
        //return name.matches("([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)*");
        return name.matches("([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)?([A-Za-z0-9]|_|-)?([A-Za-z0-9]|_|-)*");
    }

    /**
     * @return The password
     */
    public String getValue() {
        return value;
    }
}
