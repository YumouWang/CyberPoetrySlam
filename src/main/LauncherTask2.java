package main;

import models.GameState;
import views.MainView;

import javax.swing.*;

/**
 * The launcher for creating the UI in task 2
 *
 * Created by Nathan on 10/3/2014.
 */
public class LauncherTask2 {

    public static void main(String[] args) {
        JFrame frame = new MainView(new GameState());
        frame.setVisible(true);
    }
}
