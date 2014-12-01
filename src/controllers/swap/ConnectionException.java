package controllers.swap;

/**
 * Exception related to connecting to the broker
 * @author Nathan
 * @version 11/30/2014
 */
public class ConnectionException extends Throwable {

    public ConnectionException(String msg) {
        super(msg);
    }
}
