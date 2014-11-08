package broker.util;

import java.util.StringTokenizer;

/**
 * Understands how to extract info from swap message
 * 
 * @author heineman
 */
public class RequestSwapMessage {
	
	/**
	 * Request swap message in form:
	 * 
	 * REQUEST_SWAP:ID:*:msg
	 * 
	 * Second ID is '*' because at request time we don't yet know may accept. 
	 * 
	 * @param swapMessage
	 */
	public static Swap getSwap(String swapMessage) {
		StringTokenizer st = new StringTokenizer(swapMessage, IProtocol.separator);
		String tok = st.nextToken(); // consume the REQUEST_SWAP token
		if (!tok.equals(IProtocol.requestSwapMsg)) {
			return null;
		}

		Swap swap = Swap.extractSwap(st);
		return swap;
	}
}
