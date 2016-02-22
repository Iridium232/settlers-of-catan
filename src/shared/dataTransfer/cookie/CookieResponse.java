package shared.dataTransfer.cookie;

import org.json.Cookie;
import shared.dataTransfer.response.DataTransferResponse;

public abstract class CookieResponse extends DataTransferResponse {

    /**
     * The cookie data
     */
    private Cookie cookie;

    public CookieResponse(String responseBody, Boolean isBad) {
        super(responseBody, isBad);
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

}