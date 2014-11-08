package broker.ipc;


public abstract class ProcessMessageHandler {
	
	/** Next one in the chain. */
	ProcessMessageHandler successor;
	
	/** 
	 * Construct an element in the chain of responsibility. Last one uses null as a parameter.
	 */
	protected ProcessMessageHandler(ProcessMessageHandler nextOne) {
		successor = nextOne;
	}
	
	/** 
	 * Determine whether to take and act upon the request. 
	 */
	protected abstract boolean take (String request, BrokerThread thread);
	
	/**
	 * Process given request.
	 * 
	 * Determine whether the given request is one that should be processed. If not, then
	 * pass along to the next controller interested in processing protocol messages.	
	 * 
	 * @param request
	 * @param thread
	 */
	public boolean process(String request, BrokerThread thread) {
		if (take(request, thread)) {
			return true;
		}
		
		if (successor == null) { return false; }
		return successor.process(request, thread);
	}
}