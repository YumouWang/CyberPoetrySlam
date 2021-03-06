package views;

import common.Constants;
import controllers.ButtonController;
import controllers.MouseInputController;
import controllers.MoveWordController;
import controllers.UndoWithMemento;
import models.AbstractWord;
import models.GameState;
import models.Position;
import models.Word;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.util.*;


/**
 * The main view that tracks all other views
 *
 * @author Yumou Jian
 * @version 10/4/2014
 */
public class MainView extends JFrame implements Serializable {

    /**
     *Serialized ID for MainView
     */
    private static final long serialVersionUID = -4809871727043385666L;
    /**
     * Collections of protectedAreaWords and unprotectedAreaWords in MainView
     */
    Hashtable<Long, AbstractWordView> protectedAreaWords;
    ArrayList<AbstractWordView> unprotectedAreaWords;
    /**
     * Container and SelectionBox in MainView
     */
    Container contentPane;
    SelectionBox selectionBox;
    /**
     * Stacks to store undo mementos
     */
    Stack<UndoWithMemento> moves = new Stack<UndoWithMemento>();
    Stack<UndoWithMemento> redoMoves = new Stack<UndoWithMemento>();
    GameState gameState;
    /**
     * Components of panel in MainView
     */
    private JPanel panel;
    private ExploreArea exploreArea;
    private SwapAreaView swapAreaView;
    private JButton btnRedo;
    private JButton btnUndo;
    private JButton btnPublish;
    private MouseInputController mouseInputController;

    /**
     * Constructor
     *
     * @param gameState The GameState that this view represents
     */

    public MainView(GameState gameState, UndoWithMemento memento) {
        this.gameState = gameState;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 685);
        contentPane = getContentPane();
        contentPane.setLayout(null);

        setTitle("CyberPoetrySlam");

        panel = new JPanel();
        panel.setBorder(new LineBorder(Color.BLACK));
        panel.setBounds(0, 0, Constants.AREA_WIDTH, Constants.AREA_HEIGHT);
        panel.setLayout(null);

        protectedAreaWords = new Hashtable<Long, AbstractWordView>();
        unprotectedAreaWords = new ArrayList<AbstractWordView>();

        if (memento == null) {
            Random random = new Random();
            Collection<AbstractWord> protectedWords = gameState
                    .getProtectedArea().getAbstractWordCollection();
            for (AbstractWord word : protectedWords) {
                int x = random.nextInt(Constants.AREA_WIDTH - 100);
                int y = random.nextInt(Constants.AREA_HEIGHT);
                WordView view = new WordView((Word) word, new Position(x, y));
                panel.add(view.label);
                addProtectedAbstractWordView(view);
            }

            Collection<AbstractWord> unprotectedWords = gameState
                    .getUnprotectedArea().getAbstractWordCollection();
            for (AbstractWord word : unprotectedWords) {
                int x = random.nextInt(Constants.AREA_WIDTH - 100);
                int y = random.nextInt(Constants.AREA_HEIGHT
                        - Constants.PROTECTED_AREA_HEIGHT - 20)
                        + Constants.PROTECTED_AREA_HEIGHT;
                WordView view = new WordView((Word) word, new Position(x, y));
                panel.add(view.label);
                addUnprotectedAbstractWordView(view);
            }
        } else {
            memento.loadState(this, gameState);
        }

        contentPane.add(panel);

        JLabel label = new JLabel("");
        label.setOpaque(true);
        label.setBounds(0, Constants.PROTECTED_AREA_HEIGHT, 716, 2);
        label.setBackground(Color.black);
        panel.add(label);

        btnPublish = new JButton("PUBLISH");
        btnPublish.setMargin(new Insets(1, 1, 1, 1));
        btnPublish.setBounds(0, 0, 70, 20);
        btnPublish.setEnabled(false);
        panel.add(btnPublish);

        btnRedo = new JButton("REDO");
        btnRedo.setBounds(72, 0, 70, 20);
        btnRedo.setEnabled(false);
        panel.add(btnRedo);

        btnUndo = new JButton("UNDO");
        btnUndo.setBounds(144, 0, 70, 20);
        btnUndo.setEnabled(false);
        panel.add(btnUndo);

        ButtonController buttonController = new ButtonController(this, gameState);
        btnPublish.addActionListener(buttonController);
        btnRedo.addActionListener(buttonController);
        btnUndo.addActionListener(buttonController);

        exploreArea = new ExploreArea(gameState, this);
        JPanel explorePanel = exploreArea.contentPane;
        explorePanel.setBorder(new LineBorder(Color.BLACK));
        explorePanel.setBounds(716, 310, 284, 353);
        contentPane.add(explorePanel);
        explorePanel.setLayout(null);

        // Create the swap area view
        swapAreaView = new SwapAreaView(new Position(716, 0), 284, 310, this, gameState);
        contentPane.add(swapAreaView.getPanel());

        // Puts the selectionBox on the pane in front of the words.
        // The selectionBox can be set to visible or not from the selectionBox class
        selectionBox = new SelectionBox();
        setGlassPane(selectionBox);

        mouseInputController = new MouseInputController(this, gameState);
        this.addMouseInputController(mouseInputController);
    }
/**
 * Add AbstractWordView to the protected area
 * @param newWord
 */
    public void addProtectedAbstractWordView(AbstractWordView newWord) {
        protectedAreaWords.put(newWord.getWord().getId(), newWord);
    }
/**
 * Add AbstractWordView to the unprotected area
 * @param newWord
 */
    public void addUnprotectedAbstractWordView(AbstractWordView newWord) {
        unprotectedAreaWords.add(newWord);
    }

    /**
     * Removes a word view from the protected area
     *
     * @param oldWord The word to remove
     * @return Returns whether the word was successfully removed
     */
    public boolean removeProtectedAbstractWordView(AbstractWordView oldWord) {
        AbstractWordView removed = protectedAreaWords.remove(oldWord.getWord()
                .getId());
        return oldWord.equals(removed);
    }
/**
 * Remove an AbstractWordView from unprotected area and check whether it succeeds
 * @param oldWord
 * @return boolean
 */
    public boolean removeUnprotectedAbstractWordView(AbstractWordView oldWord) {
        return unprotectedAreaWords.remove(oldWord);
    }

    /**
     * Refreshes the display
     */
    public void refresh() {
        revalidate();
        repaint();
    }

    /**
     * Adds a MouseInputController to the MouseListeners and
     * MouseMotionListeners
     *
     * @param controller The controller to handle user input
     */
    public void addMouseInputController(MouseAdapter controller) {
        panel.addMouseListener(controller);
        panel.addMouseMotionListener(controller);
    }
/**
 * Get some AbstractWordView from protected area by its unique ID 
 * @param id
 * @return AbstractWordView
 */
    public AbstractWordView getProtectedAbstractWordById(long id) {
        return protectedAreaWords.get(id);
    }
/**
 * Get some AbstractWordView from unprotected area by its unique ID
 * @param id
 * @return AbstractWordView
 */
    public AbstractWordView getUnprotectedAbstractWordById(long id) {
        for (AbstractWordView abstractWordView : unprotectedAreaWords) {
            if (abstractWordView.getWord().getId() == id) {
                return abstractWordView;
            }
        }
        return null;
    }

    /**
     * Gets all the protected word views in this MainView
     *
     * @return A collection of word views
     */
    public Collection<AbstractWordView> getProtectedAreaWords() {
        return protectedAreaWords.values();
    }

    /**
     * Gets all the unprotected word views in this MainView
     *
     * @return A collection of word views
     */
    public Collection<AbstractWordView> getUnprotectedAreaWords() {
        return unprotectedAreaWords;
    }
/**
 * Get the selection box created by mouse in MainView
 * @return SelectionBox
 */
    public SelectionBox getSelectionBox() {
        return selectionBox;
    }

    /**
     * check a position is in protected area or in unprotected area
     *
     * @param position
     * @return
     */
    public boolean isInProtectedArea(Position position) {
        return position.getY() < Constants.PROTECTED_AREA_HEIGHT;
    }

    /**
     * check a wordView moving to toPosition is out of bounds or not
     *
     * @param wordView
     * @param toPosition
     * @return
     */
    public boolean isMoveOutOfBounds(AbstractWordView wordView, Position toPosition) {
        if (toPosition.getX() + wordView.getFurthestLeft() < 0) {
            return true;
        } else if (toPosition.getX() + wordView.getFurthestRight() > 716) {
            return true;
        } else if (toPosition.getY() < 20) {
            return true;
        } else if (toPosition.getY() + 20 > 663) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Collection<AbstractAreaWords>
     */
    public Collection<AbstractWordView> getUnprotectedWordView() {
        return this.unprotectedAreaWords;
    }

    /**
     * @return Collection<AbstractAreaWords>
     */
    public Collection<AbstractWordView> getProtectedWordView() {
        return this.protectedAreaWords.values();
    }

    /**
     * Add move into undo stack
     *
     * @param move
     */
    public void recordUndoMove(UndoWithMemento move) {
        moves.add(move);
        if (!moves.isEmpty()) {
            this.getUndoButton().setEnabled(true);
        }
    }

    /**
     * Add move into redo stack
     *
     * @param move
     */
    public void recordRedoMove(UndoWithMemento move) {
        redoMoves.add(move);
        if (!redoMoves.isEmpty()) {
            this.getRedoButton().setEnabled(true);
        }
    }

    /**
     * remove last undo stack and do the correspoding operation
     *
     * @return undoMove
     */
    public UndoWithMemento removeLastUndoMove() {
        if (moves.isEmpty()) {
            return null;
        }
        return moves.pop();
    }

    /**
     * remove last redo stack and do the correspoding operation
     *
     * @return redoMove
     */
    public UndoWithMemento removeLastRedoMove() {
        if (redoMoves.isEmpty()) {
            return null;
        }
        return redoMoves.pop();
    }

    /**
     * get the undomoves stack
     *
     * @return Stack<UndoMove>
     */
    public Stack<UndoWithMemento> getUndoMoves() {
        return this.moves;
    }

    /**
     * get the redomoves stack
     *
     * @return Stack<UndoMove>
     */
    public Stack<UndoWithMemento> getRedoMoves() {
        return this.redoMoves;
    }
/**
 * Get the ExploreArea from the MainView 
 * @return ExploreArea
 */
    public ExploreArea getExploreArea() {
        return exploreArea;
    }
/**
 * Get the SwapAreaView from the MainView
 * @return SwapAreaView
 */
    public SwapAreaView getSwapAreaView() {
        return swapAreaView;
    }
/**
 * Get the Publish JButton from the MainView
 * @return JButton
 */
    public JButton getPublishButton() {
        return btnPublish;
    }
/**
 * Get the Redo JButton from the MainView
 * @return JButton
 */
    public JButton getRedoButton() {
        return btnRedo;
    }
/**
 * Get the Undo JButton from the MainView
 * @return JButton
 */
    public JButton getUndoButton() {
        return btnUndo;
    }
/**
 * Get the MouseInputController from MainView
 * @return MouseInputController
 */
    public MouseInputController getMouseInputController() {
        return mouseInputController;
    }
/**
 * Add the lable of a WordView into the MainView
 * @param wordView
 */
    public void addLabelOf(WordView wordView) {
        panel.add(wordView.label);
    }
/**
 * Remove the lable of a WordVIew from the MainVIew
 * @param wordView
 */
    public void removeLabelOf(WordView wordView) {
        panel.remove(wordView.label);
    }
/**
 * Clear the whole panel of the MainView
 */
    public void clearPanel() {
        Collection<AbstractWordView> collection = new HashSet<AbstractWordView>();
        collection.addAll(protectedAreaWords.values());
        MoveWordController moveWordController = new MoveWordController(this, this.gameState);
        for (AbstractWordView wordView : collection) {
            if (wordView instanceof WordView) {
                moveWordController.unprotectWord(wordView);
            } else if (wordView instanceof RowView) {
                moveWordController.releaseRow((RowView) wordView);
            } else {
                moveWordController.releasePoem((PoemView) wordView);
            }
        }
        collection = new HashSet<AbstractWordView>();
        collection.addAll(unprotectedAreaWords);
        for (AbstractWordView wordView : collection) {
            removeLabelOf((WordView) wordView);
            removeUnprotectedAbstractWordView(wordView);
        }
    }
}
