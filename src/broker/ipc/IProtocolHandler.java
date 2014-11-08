package broker.ipc;

import java.io.BufferedReader;
import java.io.PrintWriter;

/** 
 * Broker out-sources processing of protocol to implementors 
 * of this interface, who determine when to read (and write)
 * 
 * When client disconnects, removed from list
 */
public interface IProtocolHandler {

	/**
	 * Process the protocol given access to a socket's input and output.
	 * Return false to terminate protocol; true to continue 
	 */
	boolean process(BufferedReader fromSocket, PrintWriter toSocket);

	/** Protocol handler will know its own thread. */
	void setBrokerThread(BrokerThread brokerThread);

	/** ID used by broker to refer to thread. */
	void setID(String id);
	
	/** register new handlers for messages. */
	
}
