package broker.util;

import java.util.StringTokenizer;

/**
 * Understands how to extract info from swap message
 * 
 * @author heineman
 */
public class ConfirmSwapMessage {
	
	/**
	 * Confirm message in form:
	 * 
	 * CONFIRM_SWAP:ID-req:ID-acc:Swap Request
	 * 
	 * This message is crafted by the accepting client, who has filled in the
	 * remaining wildcards and so the confirm is ready to be sent to both clients
	 * directly.
	 * 
	 * @param swapMessage
	 */
	public static Swap getSwap(String swapMessage) {
		StringTokenizer st = new StringTokenizer(swapMessage, IProtocol.separator);
		String tok = st.nextToken(); // consume the CONFIRM_SWAP
		if (!tok.equals(IProtocol.confirmSwapMsg)) {
			return null;
		}
		
		Swap swap = Swap.extractSwap(st);
		return swap;
	}
}
