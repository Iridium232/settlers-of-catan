package shared.communication.toServer.games;

/**
 * Created by rscheuer
 * This is the basic model to transfer data that is being sent to the server
 *
 */
public abstract class DataRequest {
    /**
     * Returns a JSON representation of itself.
     * @return String a JSON string
     **/

    public abstract String toJSON();
}
