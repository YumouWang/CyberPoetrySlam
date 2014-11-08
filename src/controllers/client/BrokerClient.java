package controllers.client;

import broker.util.IProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

/**
 * This is the part of your client code that reaches out to the WELL KNOWN broker to
 * make swaps.
 *
 * Taken directly from professor Heineman's code
 *
 * @author gheineman
 * @version 11/7/14
 */
public class BrokerClient {
	
	/** Well-known host name. */
	final String host;
	
	/** Port on which host is listening. */
	final int port;
	
	/** 
	 * Status of client. 
	 * OK -- nothing pending, connection valid
	 *  
	 */
	String status;
	
	/** Socket to communicate with broker. */
	Socket broker;
	
	/** Statistically Unique ID for communicating to broker. */
	String id;
	
	/** Means of communication to/from broker. */
	PrintWriter toBroker;
	BufferedReader fromBroker;
	
	/** Return our current status. */
	public String getStatus() { return status; }
	
	/** Return our id. */
	public String getID() { return id; }
	
	/** Return entity from which we get broker messages. */
	public BufferedReader getBrokerInput() { return fromBroker; }
	
	/** Return entity to which we write broker commands. */
	public PrintWriter getBrokerOutput() { return toBroker; }
	
	/**
	 * Generate a statistically unique identifier.
	 *  
	 * Stored in idStorage
	 */
	public static String generateID() {
		return UUID.randomUUID().toString();
	}
	
	public BrokerClient(String brokerHost, int brokerPort) {
		this.host = brokerHost;
		this.port = brokerPort;
	}
	
	/**
	 * Attempt to connect to broker.
	 * 
	 * On success, server Socket is ready for communication.
	 *   
	 * @return Success of operation.
	 */
	public boolean connect() {
		try {
			broker = new Socket (host, port);
			toBroker = new PrintWriter (broker.getOutputStream(), true);
			fromBroker = new BufferedReader (new InputStreamReader(broker.getInputStream()));
		} catch (IOException ioe) {
			return false;
		}
		
		// once connected, initiate first handshake.
		// tell server who I am (through my statistically unique ID)
		id = generateID();
		toBroker.println(IProtocol.loginMsg + IProtocol.separator + id);
		
		// get my status from the broker
		try {
			status = fromBroker.readLine();
		} catch (IOException e) {
			System.err.println("Unable to retrieve status from broker.");
			return false;
		}
		return true;
	}

	/** Terminate connection to broker. */
	public void shutdown() {
		try {
			if (broker != null) {
				broker.close();
			}
		} catch (IOException e) {
			System.out.println("Unable to terminate broker connection");
		}
	}
}
