package main;

import views.MainView;

import javax.swing.*;

/**
 * The launcher for creating the UI in task 2
 *
 * Created by Nathan on 10/3/2014.
 */
public class LauncherTask2 {

    public static void main(String[] args) {
        JFrame frame = MainView.getInstance();
        frame.setVisible(true);
    }
}
