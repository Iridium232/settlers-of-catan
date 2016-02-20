package shared.definitions;

import shared.exceptions.InvalidNameException;

/**
 * Wraps a string keeping track of the player's name.
 * Allows for validation of this name.
 */
public class PlayerName {
    /**
     * The player's name
     */
    private String value;

    /**
     * @param name
     * @throws InvalidNameException
     */
    public PlayerName(String name) throws InvalidNameException {
        setValue(name);
    }

    /**
     * Initialize to default player name ("Default")
     */
    public PlayerName() {
        value = "Default";
    }

    /**
     * @param name
     * @throws InvalidNameException
     */
    public void setValue(String name) throws InvalidNameException {
        if (validate(name)) {
            this.value = name;
        } else {
            throw new InvalidNameException("Must be at 3-7 characters that are: letters, numbers, dashes, or underscores");
        }
    }

    /**
     * Registering a new user should reject the registration if the [PlayerName] is fewer than 3 or greater than
     * seven characters, if the password is less than 5 characters long or is not made of allowed characters
     * (alphanumerics, underscores, hyphens, or if the password verification entry doesn't match the original
     * -- Functional spec page 4
     *
     * @param name
     * @return If name is valid
     */
    private boolean validate(String name) {
        /* Must be at 3-7 characters that are: letters, numbers, dashes, or underscores */
        return name.matches("([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)([A-Za-z0-9]|_|-)?([A-Za-z0-9]|_|-)?([A-Za-z0-9]|_|-)?([A-Za-z0-9]|_|-)?");
    }

    /**
     * @return The player's name
     */
    public String getValue() {
        return value;
    }
}