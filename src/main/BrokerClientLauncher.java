package main;

import broker.handler.IHandleBrokerMessage;
import broker.handler.ReaderThread;
import broker.util.ConfirmSwapMessage;
import broker.util.IProtocol;
import broker.util.MatchSwapMessage;
import broker.util.Swap;
import controllers.client.BrokerClient;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used to manually test client-side broker without having working client yet.
 *
 * @author gheineman
 * @version 11/7/2014
 */
public class BrokerClientLauncher implements IHandleBrokerMessage {
	
	/** hack state. */
	ArrayList<String> initialWords = new ArrayList<String>();
	
	BrokerClient broker;
	
	public static void main(String args[]) throws Exception {
		new BrokerClientLauncher().execute();
	}

	@Override
	public void brokerGone() {
		System.out.println("Broker Gone");
	}
	
	public void execute() {
//        broker = new BrokerClient("gheineman.cs.wpi.edu", 9172);
        broker = new BrokerClient("localhost", 9172);

		if (!broker.connect()) {
			System.err.println("unable to connect to broker");
			broker.shutdown();
		}
		
		initialWords.add("sample");
		initialWords.add("sample");
		initialWords.add("sample");
		initialWords.add("other");
		initialWords.add("other");
		
		// at this point we are connected, and we will block waiting for 
		// any messages from the broker. These will be sent to the process
		System.out.println("Status:" + broker.getStatus());
		
		// start thread to process commands from broker.
		ReaderThread thread = new ReaderThread(broker, this);
		thread.start();
		
		// nothing else happens here. Demonstrating one plausible scenario
		Scanner sc = new Scanner(System.in);
		System.out.println("Once both clients are logged in, press return to request swap");
		sc.nextLine();
		
		System.out.println("Current State:" + initialWords);
		
		// swap sample and sample for 'other' and  'other'
		String swap = "2:noun:noun:sample:sample:noun:noun:other:other";
		thread.sendMessage(IProtocol.requestSwapMsg + IProtocol.separator +
							broker.getID() + IProtocol.separator + "*" + IProtocol.separator +
							swap);
		
		// broker handles this and confirms back with an appropriate execution
		System.out.println("Observer response");
		sc.nextLine();
		
		System.out.println("Current State:" + initialWords);
		
	}

	/**
	 * Note: Your code will have to provide a suitable implementation that integrates
	 * with your individual board.
	 * 
	 * This demonstration example shows the raw code. Check out the WordMap application
	 * for how I've done it, then go and do the same in your code.
	 */
	@Override
	public void process(BrokerClient broker, String msg) {
		System.out.println("Process message:" + msg);
		
		if (msg.startsWith(IProtocol.denySwapMsg)) {
			System.out.println("Denied swap request");
			return;
		}
		
		if (msg.startsWith(IProtocol.matchSwapMsg)) {
			Swap s = MatchSwapMessage.getSwap(msg);
			System.out.println("Third party trying a swap:" + s.flatten());
			
			ArrayList<String> tempRemove = new ArrayList<String>();
			boolean failed = false;
			for (int i = 0; i < s.requestWords.length; i++) {
				int idx = initialWords.indexOf(s.requestWords[i]);
				if (idx != -1) {
					tempRemove.add(initialWords.remove(idx));
				} else {
					failed = true;
					break;
				}
			}
			
			// remove only happens with confirmSwap
			initialWords.addAll(tempRemove);
			
			if (failed) {
				System.out.println("Unable to satisfy swap request");
				broker.getBrokerOutput().println(IProtocol.denySwapMsg +
						IProtocol.separator + s.requestor_id);
				return;
			}
			
			System.out.println("Accepting satisfy swap request");
			// what should we do? Agree of course! Here is where your code would
			// normally "convert" wildcards into actual words in your board state.
			// for now this is already assumed (note sample/other swap)
			broker.getBrokerOutput().println(IProtocol.confirmSwapMsg +
							IProtocol.separator + s.requestor_id +
							IProtocol.separator + s.acceptor_id +
							IProtocol.separator + s.flatten());
			return;
		}
		
		if (msg.startsWith(IProtocol.confirmSwapMsg)) {
			Swap s = ConfirmSwapMessage.getSwap(msg);
			System.out.println("Prior State:" + initialWords);
			// carry out the swap.
			if (broker.getID().equals(s.requestor_id)) {
				// We made the request: remove offer words and take request words 
				for (int i = 0; i < s.n; i++) {
					initialWords.remove(s.offerWords[i]);
				}
				for (int i = 0; i < s.n; i++) {
					initialWords.add(s.requestWords[i]);
				}
			} else {
				// We accepted request: remove request words and take offer words
				// We made the request: remove offer words and take request words 
				for (int i = 0; i < s.n; i++) {
					initialWords.remove(s.requestWords[i]);
				}
				for (int i = 0; i < s.n; i++) {
					initialWords.add(s.offerWords[i]);
				}
			}
			
			System.out.println("Updated State:" + initialWords);
			return;
		}
	}
}
