package views;

import controllers.MouseInputController;
import models.AbstractWord;
import models.GameState;
import models.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;

/**
 * The main view that tracks all other views
 *
 * Created by Nathan on 10/3/2014.
 */
public class MainView extends JFrame {

    Hashtable<Long, AbstractWordView> words;
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

        words = new Hashtable<Long, AbstractWordView>();

        Random random = new Random();
        Collection<AbstractWord> words = gameState.getProtectedArea().getAbstractWordCollection();
        for(AbstractWord word: words) {
            int x = random.nextInt(300);
            int y = random.nextInt(200);
            WordView view = new WordView(word, new Position(x, y));
            contentPane.add(view.label);
            addAbstractWordView(view);
        }
    }

    /**
     * Adds a word view to the view
     * @param newWord The word to add
     */
    public void addAbstractWordView(AbstractWordView newWord) {
        words.put(newWord.getWord().getId(), newWord);
    }

    /**
     * Removes a word view from the view
     * @param oldWord The word to remove
     * @return Returns whether the word was successfully removed
     */
    public boolean removeAbstractWordView(AbstractWordView oldWord) {
        AbstractWordView removed = words.remove(oldWord.getWord().getId());
        return removed.equals(oldWord);
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
        return words.values();
    }

    public AbstractWordView getAbstractWordById(long id) {
        return words.get(id);
    }
}
