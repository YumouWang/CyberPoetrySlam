package broker.ipc;

import broker.server.ProtocolHandler;
import broker.util.IProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * Java Implementation of CyberPoetrySlam Broker.
 * 
 * Chain of responsibility not defined HERE since that is driven by application protocol.
 */
public class Broker {
	public static final int   defaultPort = 9172;
	
	ServerSocket serverSocket = null;     // accept connections on this socket
	int state = 0;                        // 0=inactive, 1=accepting
	int brokerPort;
	
	/** Known client IP. */
	static Hashtable<String,Client> threads = new Hashtable<String,Client>();
	static ArrayList<Client> clients = new ArrayList<Client>();
	
	public Broker () {
		this(defaultPort);
	}
	
	public Broker(int p) {
		brokerPort = p;
	}
	
	/** Bind Broker. */
	public void bind() throws IOException {
		serverSocket = new ServerSocket(brokerPort);
		state = 1; 
	}

	/**
	 * Execute main server loop which receives client connection requests
	 * and spawns threads to execute each one. Process will handle all
	 * requests while state is 1 (accepting). Once no longer
	 * accepting requests, this method will shutdown the server. 
	 */
	public void process(ProcessMessageHandler chain) throws IOException {
		while (state == 1) {
			Socket client = serverSocket.accept();

			// spawn thread and move back to receiving connections.
			new BrokerThread(client, new ProtocolHandler(chain)).start();
		} 

		shutdown();
	}

	/** Shutdown the broker. */
	void shutdown() throws IOException {
		if (serverSocket != null) {
			serverSocket.close();
			serverSocket = null;
			state = 0;
		}
	}

	/**
	 * Get random client (other than self).
	 * */
	public static Client getRandomClient(BrokerThread self) {
		Random r = new Random();
		
		
		int idx = Math.abs(r.nextInt()) % clients.size();
		int ctr = 0;
		while (ctr++ < 10 && self == clients.get(idx).thread) {
			idx = Math.abs(r.nextInt()) % clients.size();
		}
		
		if (clients.get(idx).thread == self) { return null; }
		return clients.get(idx);		
	}
	
	/** Return client associated with id. */
	public static Client getClient(String id) { return threads.get(id); }
	
	/** Register thread so broker can use it later. */
	public static void register(String id, BrokerThread thread) {
		Client exist = threads.get(id);
		if (exist == null) {
			exist = new Client(id, thread);
			exist.setStatus(IProtocol.okStatus);
			clients.add(exist);
			threads.put(id, exist);
		}
		System.out.println(threads.size() + " Connected clients.");
	}

	/** Unregister thread so broker keeps track. */
	public static void unregister(String id) {
		Client oldOne = threads.remove(id);
		clients.remove(oldOne);
		System.out.println(threads.size() + " Connected clients.");
	}
}