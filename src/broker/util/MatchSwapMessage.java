package broker.util;

import java.util.StringTokenizer;

/**
 * Understands how to extract info from swap message
 * 
 * @author heineman
 */
public class MatchSwapMessage {
	
	/**
	 * Match message in form:
	 * 
	 * MATCH_SWAP:ID1:msg
	 * 
	 * ID1 is the client making the request
	 * 
	 * @param swapMessage
	 */
	public static Swap getSwap(String swapMessage) {
		StringTokenizer st = new StringTokenizer(swapMessage, IProtocol.separator);
		String tok = st.nextToken(); // consume the MATCH_SWAP
		if (!tok.equals(IProtocol.matchSwapMsg)) {
			return null;
		}
		
		Swap swap = Swap.extractSwap(st);
		return swap;
	}
}
