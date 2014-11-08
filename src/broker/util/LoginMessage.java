package broker.util;

/**
 * Understands how to extract ID from login message
 * @author heineman
 *
 */
public class LoginMessage {
	
	/**
	 * Login message in form:
	 * 
	 * LOGIN:ID
	 * 
	 * @param loginMessage
	 */
	public static String getID(String loginMessage) {
		int idx = loginMessage.indexOf(IProtocol.separator);
		return loginMessage.substring(idx+1);
	}
}
