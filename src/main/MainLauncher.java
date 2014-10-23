package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import controllers.MouseInputController;
import models.GameState;
import models.protectedWordMemento;
import models.unprotectedWordMemento;
import views.MainView;

/**
 * The main launcher for starting the program
 *
 * Created by Yumou on 10/3/2014.
 */
public class MainLauncher {
	
	static final String protectedWordStorage = "protectedWords.Storage";
	static final String unprotectedWordStorage = "unprotectedWords.Storage";
	
	//location1 is where we stores unprotectedWords
		//location2 is where we stores protectedWords
		static void storeState(GameState g, String location1, String location2) {
			ObjectOutputStream oos1 = null;
			ObjectOutputStream oos2 = null;
			try {
				oos1 = new ObjectOutputStream(new FileOutputStream(location1));
				oos2 = new ObjectOutputStream(new FileOutputStream(location2));
				//capture current gameState and write current state into a file
				//current gameState divides into 2 parts: protectedwords and unprotectedWords (simplify)
				oos1.writeObject(g.getUnprotectedWordState());
				oos2.writeObject(g.getProtectedWordState());
			} catch (Exception e) {
				System.out.println("Error/exception happens in storeState function");
			}
			
			if (oos1 != null || oos2 != null) {
				try { 
				oos1.close(); 
				oos2.close();
				} catch (IOException ioe) { } 
			}
			
		}
		
		//location1 is where we stores unprotectedWords
		//location2 is where we stores protectedWords
		static GameState loadState(String location1, String location2) {
			 ObjectInputStream ois1 = null;
			 ObjectInputStream ois2 = null;
			 //Here we need a different GameState Constructor that initializes all attributes in GameState (not hard-coded)
			 GameState b = new GameState();
			 try {
				 ois1 = new ObjectInputStream (new FileInputStream(location1));
				 ois2 = new ObjectInputStream (new FileInputStream(location2));
				 //Read content in the file and cast to unprotectedWordMemento/protectedWordMemento
				 unprotectedWordMemento un = (unprotectedWordMemento) ois1.readObject();
				 protectedWordMemento p = (protectedWordMemento) ois2.readObject();
				 ois1.close();
				 ois2.close();
				 //Restore to last state using this restore() function
				 b.restoreUnprotectedWords(un);
				 b.restoreProtectedWords(p);
				 //After this is done, why followings print out 0 instead?
				 System.out.println(b.getProtectedWordView().size());
				 System.out.println(b.getUnprotectedWordView().size());
			 } catch (Exception e) {
				 // unable to perform restore
				 System.out.println("Unable to perform restore");
			 }
			 
			 // close down safely
			 if (ois1 != null || ois2 != null) {
				 try { 
					 ois1.close(); 
					 ois2.close();
				 } catch (IOException ioe) { }
			 }
			 
			 return b;
		}

	
	
	
	/**
	 * Launch the application.
	 */
	/* rewrite a new main function.
	public static void main(String[] args) {
        // Initialize the GameState object
        GameState gameState = new GameState();
        // Initialize the MainView pointing at the GameState
        MainView mainView = new MainView(gameState);
        // Add a controller to handle user input
        mainView.addMouseInputController(new MouseInputController(mainView, gameState));
        // Display the view
        mainView.setVisible(true);
	}
	*/
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//mode is used to detect whether to initialize or restore from last state
		String mode = "new";
	    final GameState gameState;
		try{
			File protectedfile = new File(protectedWordStorage);
			File unprotectedfile = new File(unprotectedWordStorage);
			if (protectedfile.exists() && unprotectedfile.exists()) {
				FileReader protectedfr = new FileReader(protectedWordStorage);
				FileReader unprotectedfr = new FileReader(unprotectedWordStorage);
				if (protectedfr.read() == -1 || unprotectedfr.read() == -1) {
					//use read() to see if configuration file is empty or not
					System.out.println("Configuration file is EMPTY");
				} 
				else {
					mode = "used";
					System.out.println("Configuration file is NOT EMPTY");
				}
			} 
			else {
				//error (no confuration file) happens during reading files
				System.out.println("Configuration file does not exist");
				return;
			}
		}
		catch(IOException ex){
			//errors happen
			System.out.println("Error happens during initialization");
			return;
		}
		System.out.println(mode);
		if(mode.matches("used")){
			//this is the case we need to restore back to last state
			gameState = loadState(unprotectedWordStorage,protectedWordStorage);
		}
		else{
			//This case we just need to initilize the game
			gameState = new GameState("Hello");
		}
		//Initialize a mainview pointing to a gameState
		MainView view = new MainView(gameState,mode);
		//add mouse controllers to monitor the events
		view.addMouseInputController(new MouseInputController(view, gameState));
		view.addWindowListener (new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			storeState(gameState, unprotectedWordStorage,protectedWordStorage);
			System.exit(0);
			}
		});
		//Display the view
		view.setVisible(true);
	}
}
