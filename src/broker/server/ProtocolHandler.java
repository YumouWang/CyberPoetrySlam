package broker.server;

import broker.ipc.BrokerThread;
import broker.ipc.IProtocolHandler;
import broker.ipc.ProcessMessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/** Implementation of protocol. */
public class ProtocolHandler implements IProtocolHandler {
	/** Every handler knows its own broker thread. Set by BrokerThread */
	public BrokerThread thread;

	/** ID used by broker to refer to this thread. */
	String id;

	/** Chain of responsibility pattern to process protocol. */
	ProcessMessageHandler chain;
	
	public ProtocolHandler(ProcessMessageHandler chain) {
		this.chain = chain;
	}

	@Override
	public void setBrokerThread(BrokerThread brokerThread) {
		this.thread = brokerThread;
	}	

	@Override
	public void setID(String id) {
		this.id = id;
	}
	
	/** Return false to disconnect and end the protocol. */
	public boolean process(BufferedReader fromSocket, PrintWriter toSocket) {
		try {
			// Retrieve request from client as a single string.
			String request = fromSocket.readLine();
			if (request == null) {
				return false;
			}
			
			if (chain.process(request, thread)) {
				return true;
			} else {
				// internal server error. Try to continue and keep processing
				outputError(toSocket, "Unable to process request: " + request);
				return true;
			}
		} catch (IOException e) {
			System.out.println("Connection reset");
			return false;
		}
	}

	/** Output proper response to client. */
	void output(PrintWriter toSocket, String value) {
		toSocket.println(0);
		toSocket.println(value);
	}

	/** Output error to the client. */
	void outputError(PrintWriter toSocket, String error) {
		toSocket.println(-1);
		toSocket.println(error);
	}


}
