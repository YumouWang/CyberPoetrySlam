package broker.util;

import java.util.StringTokenizer;

/**
 * Understands how to extract info from deny swap message
 * 
 * @author heineman
 */
public class DenySwapMessage {
	
	/**
	 * Deny message in form:
	 * 
	 * DENY_SWAP:ID-req
	 * 
	 * @param denyMessage
	 */
	public static String getRequestID(String denyMessage) {
		StringTokenizer st = new StringTokenizer(denyMessage, IProtocol.separator);
		String tok = st.nextToken(); // consume the DENY_SWAP
		if (!tok.equals(IProtocol.denySwapMsg)) {
			return null;
		}
		
		return st.nextToken();  // the id of requesting client
	}
}
