package shared.dataTransfer.cookie;


import org.json.Cookie;

/**
 * This class represents the cookie data recieved from the Catan server concerning a user
 */
public class UserCookie extends Cookie {

    /**
     * All user cookie urls start this
     */
    private final static String URL_BEG = "catan.user=";

    /**
     * The default constructor for a cookie. The url is set to null.
     *
     * @pre none
     * @post a cookie object is created with all it's values set to null.
     */
    public UserCookie() {
        super();
    }

    /**
     * The a constructor for a cookie. Pass in a url containing the cookie data.
     *
     * @pre the url is valid
     * @post a cookie object is created with all it's values set to null.
     */
    public UserCookie(String url) {
        super();
    }


    public Cookie getUserCookie() {
        return this;
    }
}
