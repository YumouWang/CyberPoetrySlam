package broker.ipc;

import broker.util.IProtocol;
import broker.util.LoginMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** Thread to handle individual requests from a client. */
public class BrokerThread extends Thread {
	Socket client;		       // Socket used by thread to communicate with client.
	BufferedReader fromClient; // Used to process strings from client. 
	PrintWriter toClient;      // Used to send strings to client.
	IProtocolHandler handler;  // Handler to process protocol.
	String id;                 // client-assigned unique ID.
	
	/**
	 * Given a socket with which to communicate to the client allocates 
	 * objects to handle input/output to client.
	 */
	BrokerThread (Socket s, IProtocolHandler h) throws IOException {
		fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
		toClient = new PrintWriter (s.getOutputStream(), true);
		client = s;
		handler = h;
		
		h.setBrokerThread(this);
	}

	/** Directly sends command to client. Use with caution! */
	public void sendMessage(String msg) {
		toClient.println(msg);
	}
	
	/** Returns Id under which we registered. */
	public String getID() { return id; }
	
	/**
	 * Thread receives strings from the client, processes them and returns
	 * result back to the client. 
	 * 
	 * If protocol is violated, then immediately close connection to client.
	 */
	public void run() {
		// must first get id
		// Retrieve request from client as a single string.
		String loginRequest = null;
		try {
			loginRequest = fromClient.readLine();
		} catch (IOException e1) {
			// failed to login? Just drop
			return;
		}
		
		// form LOGIN:long-id
		id = LoginMessage.getID(loginRequest);
		Broker.register(id, this);
		handler.setID(id);
		
		toClient.println(IProtocol.loginMsg + IProtocol.separator + IProtocol.okStatus);
		
	    // have handler manage the protocol until it decides it is done.
		while (handler.process(fromClient, toClient)) {
			
		}

		Broker.unregister(id);
		
		// close communication to client.
		try {
			fromClient.close();
			toClient.close();
			client.close();
		} catch (IOException e) {
			System.err.println("Unable to close connection:" + e.getMessage());
		}
	}
}
