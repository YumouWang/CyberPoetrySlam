package broker.ipc;

import broker.BrokerClient;
import broker.ipc.Broker;
import broker.ipc.ProcessMessageHandler;
import broker.server.controller.ConfirmSwapController;
import broker.server.controller.DenySwapController;
import broker.server.controller.RequestSwapController;
import broker.util.IProtocol;
import broker.util.Swap;
import junit.framework.TestCase;

import java.io.IOException;

public class TestBroker extends TestCase {
	public static final int myport = 9876;
	Broker server;
	Thread brokerThread;
	
	protected void setUp() {
		brokerThread = new Thread() {
			public void run() {
		
				server = new Broker(myport);
				
				try {
					server.bind();
				} catch (IOException ioe) {
					fail ("unable to launch broker");
				}
				
				ProcessMessageHandler confirmSwap = new ConfirmSwapController(null);
				ProcessMessageHandler denySwap    = new DenySwapController(confirmSwap);
				ProcessMessageHandler chain       = new RequestSwapController(denySwap);
				
				// begin processing
				try {
					server.process(chain);
				} catch (IOException e) {
					// ignore termination conditions
				}
			}
		};
		
		brokerThread.start();
			
	} 
	
	protected void tearDown() {
		try {
			server.shutdown();
		} catch (Exception e) {
			System.err.println("unexpectedly encountered error when shutting down server");
		}
	}
	
	public void testContact() {
		BrokerClient client = new BrokerClient("localhost", myport);

		if (!client.connect()) {
			fail("Unable to connect to broker.");
		}
		
		// the above handles login. Here send simple message to request swap, but no other clients
		// so it will be denied.
		
		Swap s = new Swap(client.getID(), "*", 1, new String[]{"*"}, new String [] {"sample"},
				new String[]{"*"}, new String [] {"other"});
		client.getBrokerOutput().println(IProtocol.requestSwapMsg + IProtocol.separator + s.flatten());
		
		try {
			String msg = client.getBrokerInput().readLine();
			System.out.println(msg);
			assertTrue (msg.startsWith(IProtocol.denySwapMsg));
		} catch (IOException e) {
			fail ("unable to process message from broker.");
		}
		
		client.shutdown();
	}
}
