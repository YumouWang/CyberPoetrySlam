package controllers.swap;

/**
 * Exception for handling if a swap request was not formed correctly
 *
 * @author Nathan
 * @version 11/30/2014
 */
public class InvalidSwapException extends Throwable {
	/**
	 * Constructor
	 * @param msg
	 */
    public InvalidSwapException(String msg) {
        super(msg);
    }
}
