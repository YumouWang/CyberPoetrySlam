package views;

import controllers.MouseInputController;
import models.AbstractWord;
import models.GameState;
import models.Position;

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

    /**
     * Constructor
     * @param gameState The GameState that this view represents
     */
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
            WordView view = new WordView(word, new Position(x, y));
            addWordView(view);
        }
    }

    /**
     * Adds a word view to the view
     * @param newWord The word to add
     */
    public void addWordView(WordView newWord) {
        words.add(newWord);
        contentPane.add(newWord.label);
    }

    /**
     * Removes a word view from the view
     * @param oldWord The word to remove
     * @return Returns whether the word was succesfully removed
     */
    public boolean removeWordView(WordView oldWord) {
        contentPane.remove(oldWord.label);
        return words.remove(oldWord);
    }

    /**
     * Refreshes the display
     */
    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }

    /**
     * Adds a MouseInputController to the MouseListeners and MouseMotionListeners
     * @param controller The controller to handle user input
     */
    public void addMouseInputController(MouseInputController controller) {
        contentPane.addMouseListener(controller);
        contentPane.addMouseMotionListener(controller);
    }

    /**
     * Gets all the word views in this MainView
     * @return A collection of word views
     */
    public Collection<AbstractWordView> getWords() {
        return words;
    }
}
