package views;

import controllers.MouseInputController;
import models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

/**
 * The main view that tracks all other views
 *
 * Created by Nathan on 10/3/2014.
 */
public class MainView extends JFrame {

    Collection<AbstractWordView> words;
    JPanel contentPane;

    public MainView(GameState gameState) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 408);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        words = new HashSet<AbstractWordView>();

        Random random = new Random();
        Collection<AbstractWord> words = gameState.getProtectedArea().getAbstractWordCollection();
        for(AbstractWord word: words) {
            int x = random.nextInt(300);
            int y = random.nextInt(200);
            AbstractWordView view = new AbstractWordView(word, new Position(x, y));
            addAbstractWordView(view);
        }

        MouseInputController mouseInputController = new MouseInputController(this, gameState);
        contentPane.addMouseListener(mouseInputController);
        contentPane.addMouseMotionListener(mouseInputController);
    }

    public void addAbstractWordView(AbstractWordView newWord) {
        words.add(newWord);
        contentPane.add(newWord.label);
    }

    public boolean removeAbstractWordView(AbstractWordView oldWord) {
        contentPane.remove(oldWord.label);
        return words.remove(oldWord);
    }

    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }

    public Collection<AbstractWordView> getWords() {
        return words;
    }
}
