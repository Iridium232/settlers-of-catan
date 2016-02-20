package shared.exceptions;

/**
 * Created by rscheuer on 2/19/16.
 */
@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}