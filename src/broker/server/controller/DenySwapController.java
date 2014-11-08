package broker.server.controller;

import broker.ipc.Broker;
import broker.ipc.BrokerThread;
import broker.ipc.Client;
import broker.ipc.ProcessMessageHandler;
import broker.util.DenySwapMessage;
import broker.util.IProtocol;

/**
 * A denial of a swap request has been received. This must be sent
 * directly to the requesting client "as is".
 */
public class DenySwapController extends ProcessMessageHandler {
	
	/** Constructor for concrete handler. */
	public DenySwapController(ProcessMessageHandler nextOne) {
		super(nextOne);
	}

	/** Process the DenySwap message. */
	public boolean take(String request, BrokerThread thread) {
		if (!request.startsWith(IProtocol.denySwapMsg + IProtocol.separator)) {
			return false; 
		}
		
		String id = DenySwapMessage.getRequestID(request);

		Client requestor = Broker.getClient(id);
		
		// tell requestor of failure to find a swap
		requestor.thread.sendMessage(request);
		return true;
	}
}
