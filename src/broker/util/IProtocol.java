package broker.util;

/**
 * Simply a place to include everything about the protocol.
 * 
 *
 */
public interface IProtocol {
	// core messages
	
	/** Each client "logins" to the broker with uniq ID that persists. */
	public final String loginMsg = "LOGIN";
	
	/** 
	 * Client makes swap request. 
	 * 
	 * NOTE: Because of change in architecture, the client must explicitly select words
	 * before making the request to the broker. Note your GUI can still select a random
	 * word by word type (if user doesn't want to select the N words manually)
	 * 
	 * A Swap Request is identified by
	 *  
	 *      {N, offerTypes[], offerWords[], requestTypes[], requestWords[]} where:
	 * 
	 * N is an integer > 0 and all arrays are of size N
	 * offerTypes[i] determines the type of the ith word being offered
	 * offerWords[i] optionally determines the actual word being offered
	 * requestTypes[i] determines the type of the ith word being requested
	 * requestWords[i] optionally determines the actual word being requested
	 * 
	 * Separator between each is ':' separator character
	 * 
	 * IDreq:IDacc:5:noun:noun:noun:noun:noun:car:tree:rock:table:dog:verb:noun:*:*:*:*:book:*:*:*
	 * 
	 *  "I want to swap five words (car,tree,rock,table,dog) all of which are nouns, and
	 *   I want five words back, one of which must be a verb and one must be the noun 'book'."
	 * 
	 * The IDreq is statistically unique and refers to the client making the request.
	 * Eventually this might be paired up with another client who accepts this
	 * swap, and his id would be IDacc. Until that happens, it is "*"
	 * 
	 * None of the Offer words can be '*' because the originating client must select
	 * the initial set. Note, your GUI can still say "any 5 words" but that means the
	 * code preparing the first REQUEST_SWAP message just selects those five words in 
	 * advance before sending to the broker.
	 * 
	 **/
	public final String requestSwapMsg = "REQUEST_SWAP";
	
	/** 
	 * Client asked to match swap request with words. 
	 *  
	 * MATCH_SWAP:ID-req:ID-acc:swapRequest
	 *  
	 * Upon a successful match, the client confirms to broker and then both are 
	 * notified to execute swap. 
	 */
	public final String matchSwapMsg = "MATCH_SWAP";
	
	/** 
	 * Match denied
	 * 
	 * ID1:*
	 * 
	 * ID1 is the client making the swap request
	 * 
	 */
	public final String denySwapMsg = "DENY_SWAP";
	
	/** 
	 * Match found, so both are asked to CONFIRM SWAP.
	 * 
	 * ID1:ID2:swap-request
	 * 
	 * ID1 is the client making the swap request
	 * ID2 is the id which that client had created when making the swap request
	 * 
	 * Swap encoded is fully materialized and there are no "*" wildcards
	 * 
	 * Originator of swap takes request words. Recipient of swap takes offer words
	 */
	public final String confirmSwapMsg = "CONFIRM_SWAP";
	
	
	// needed to separate characters in the header
	public final String separator = ":";
	
	// responses
	public final String okStatus = "OK";

	
	
}
