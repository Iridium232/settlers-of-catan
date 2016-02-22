package shared.dataTransfer.request;

/**
 * Created by rscheuer on 2/20/16.
 */
public abstract  class DataTransferRequest {
    /**
     * Returns a JSON representation of itself.
     *
     * @return String a JSON string
     */
    public abstract String toJSON();

}
