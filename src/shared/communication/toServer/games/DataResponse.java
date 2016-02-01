package shared.communication.toServer.games;

/**
 * Created by rscheuer
 * Model to transfer data to server
 */

public class DataResponse {
    /**
     *This flag tells to or not the database is good request
     */
    public Boolean badRequest;
    protected String responseBody;


    /**
     * This is the basic model to transfer data that is being sent to the client
     *
     * @param reponseBody a string containing the response body
     * @param badRequest is a flag that indicates if is bad request, if it's true, data will contain the orginal reponse. This will not contain Json
     */
    public DataResponse(String responseBody, Boolean badRequest) {
        setResponseBody(responseBody);
        this.badRequest = badRequest;
    }

    /**
     * Converts the stored JSON data to a Java Objects
     *
     * @return the Java Objected Stored in the JSON
     * @pre the isBad flag is false
     * @post there should be no change to this instance of the DataTransferResponse
     */
    public Object toJava() {
        return responseBody;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

}
