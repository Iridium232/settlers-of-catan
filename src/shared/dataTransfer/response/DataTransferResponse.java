package shared.dataTransfer.response;

/**
 * Created by rscheuer
 * This class transfer data returned by the server
 */
public abstract class DataTransferResponse {
    public Boolean isBad;
    protected String responseBody;

    /**
     * This is the basic model to transfer data that is being sent to the client
     **/
    public DataTransferResponse(String responseBody, Boolean isBad) {
        setResponseBody(responseBody);
        this.isBad = isBad;
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

    public void setResponseBody(String responseBody) {this.responseBody = responseBody;}
    
}
