package shared.exceptions;

/**
 * Created by rscheuer on 2/19/16.
 */
@SuppressWarnings("serial")
public class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}
