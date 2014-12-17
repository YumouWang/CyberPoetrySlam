package controllers;

import common.Constants;
import models.GameState;
import views.MainView;

import java.io.*;

/**
 * Controller in charge of loading and writing state to a file
 *
 * @author Jian
 * @author Nathan
 * @version 12/14/2014
 */
public class LoadStateController {

    public UndoWithMemento loadMemento() {
        ObjectInputStream ois = null;
        UndoWithMemento m = null;
        if (new File(Constants.MEMENTO_STORAGE).exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(Constants.MEMENTO_STORAGE));
                m = (UndoWithMemento) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // close down safely
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        return m;
    }

    public void storeState(MainView mainView, GameState gameState) {
        File mementoFile = new File(Constants.MEMENTO_STORAGE);
        if (!mementoFile.exists()) {
            try {
                mementoFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(Constants.MEMENTO_STORAGE));
            oos.writeObject(new UndoWithMemento(mainView));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
