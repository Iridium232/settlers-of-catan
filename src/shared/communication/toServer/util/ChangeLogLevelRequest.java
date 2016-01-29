package shared.communication.toServer.util;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * Sets the server's log level (ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF)
 */
public class ChangeLogLevelRequest {
    /**
     * The server's new log level. The following values are allowed:
     * ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF
     */
    private String logLevel;

    public ChangeLogLevelRequest(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}
