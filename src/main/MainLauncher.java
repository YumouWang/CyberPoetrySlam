package main;

import controllers.swap.BrokerConnectionController;
import controllers.swap.ConnectionException;
import controllers.swap.SwapController;
import models.GameState;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import views.MainView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * The main launcher for starting the program
 * @author Yumou
 * @author Jian
 * @author Nathan
 * @version 10/3/2014
 */
public class MainLauncher implements Serializable {

	/**
	 * protectedWordStorage/unprotectedWordStorage is place for Memento
	 */
	private static final long serialVersionUID = 623363781824512600L;
	static final String protectedWordStorage = "protectedWords.storage";
	static final String unprotectedWordStorage = "unprotectedWords.storage";

	/**
	 * Stores the state of game when we trying to close the program
	 * @param mainView
	 * @param location1 UnprotectedWordStorage
	 * @param location2 ProtectedWordStorage
	 */
	static void storeState(MainView mainView, String location1, String location2) {
		File unprotectedWord = new File(location1);
		File protectedWord = new File(location2);
		if (!unprotectedWord.exists()) {
			try {
				unprotectedWord.createNewFile();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (!protectedWord.exists()) {
			try {
				unprotectedWord.createNewFile();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		ObjectOutputStream oos1 = null;
		ObjectOutputStream oos2 = null;
		try {
			oos1 = new ObjectOutputStream(new FileOutputStream(location1));
			oos2 = new ObjectOutputStream(new FileOutputStream(location2));
			oos1.writeObject(mainView.getUnprotectedState());
			oos2.writeObject(mainView.getProtectedState());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (oos1 != null || oos2 != null) {
			try {
				oos1.close();
				oos2.close();
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
	}

	/**
	 * Load the unprotected state
	 * @param location
	 * @return UnprotectedMemento
	 */
	static UnprotectedMemento loadUnprotectedMemento(String location) {
		ObjectInputStream ois = null;
		UnprotectedMemento un = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(location));
			un = (UnprotectedMemento) ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}

		return un;
	}

	/**
	 * Load protected state
	 * @param location
	 * @return ProtectedMemento
	 */
	static ProtectedMemento loadProtectedMemento(String location) {
		ObjectInputStream ois = null;
		ProtectedMemento p = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(location));
			p = (ProtectedMemento) ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// close down safely
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}

		return p;
	}

	/**
	 * 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		UnprotectedMemento un = loadUnprotectedMemento(unprotectedWordStorage);
		ProtectedMemento p = loadProtectedMemento(protectedWordStorage);
		// Initialize the GameState object
		final GameState gameState = new GameState(un, p);
		// Initialize the MainView pointing at the GameState
		final MainView mainView = new MainView(gameState, un, p);
		// Add a controller to handle user input
		// mainView.addMouseInputController(new MouseInputController(mainView,
		// gameState));
		mainView.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SwapController swapController = new SwapController(mainView, gameState);
				swapController.cancelPendingSwaps();
				storeState(mainView, unprotectedWordStorage,
						protectedWordStorage);
				System.exit(0);
			}
		});
		// Display the view
		mainView.setVisible(true);

		// Attempt to connect to the broker immediately on startup
		// If we can't then disable the swap area
		try {
			BrokerConnectionController.getConnection(mainView, gameState);
			mainView.getSwapAreaView().enable();
		} catch (ConnectionException e) {
			mainView.getSwapAreaView().disable();
		}
	}
}
