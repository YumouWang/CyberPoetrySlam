package broker.handler;

import controllers.client.BrokerClient;

/**
 * 
 * There may be multiple swaps coming in. Each has its own ID.
 * If a client is already engaged in a swap, then subsequent swaps are denied 
 * immediately.
 */
public interface IHandleBrokerMessage {
	
	/** Handle message which was received from broker. */
	void process(BrokerClient broker, String msg);
	
	/** Handle when broker is lost independently. */
	void brokerGone();
}
