package views;

import common.Constants;
import controllers.ButtonController;
import controllers.MouseInputController;
import models.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import models.AbstractWord;
import models.GameState;
import models.Poem;
import models.Position;
import models.Row;
import models.Word;
import models.ProtectedMemento;
import models.UnprotectedMemento;
import common.Constants;
import controllers.ButtonController;
import controllers.UndoMove;


/**
 * The main view that tracks all other views
 * 
 * @author Yumou Jian
 * @version 10/4/2014
 */
public class MainView extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4809871727043385666L;
	Hashtable<Long, AbstractWordView> protectedAreaWords;
	Hashtable<Long, AbstractWordView> unprotectedAreaWords;
	Container contentPane;
	private JPanel panel;
	private ExploreArea exploreArea;
	private SwapAreaView swapAreaView;
	SelectionBox selectionBox;
	
	Stack<UndoMove> moves = new Stack<UndoMove>();
	Stack<UndoMove> redoMoves = new Stack<UndoMove>();

	private JButton btnRedo;
	private JButton btnUndo;
	private JButton btnPublish;
	private MouseInputController mouseInputController;


	/**
	 * Constructor
	 * 
	 * @param gameState
	 *            The GameState that this view represents
	 */

	public MainView(GameState gameState, UnprotectedMemento un, ProtectedMemento p) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setSize(1027, 735);
		contentPane = getContentPane();
		contentPane.setLayout(null);

		setTitle("CyberPoetrySlam");

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(0, 0, Constants.AREA_WIDTH, Constants.AREA_HEIGHT);
		panel.setLayout(null);

		protectedAreaWords = new Hashtable<Long, AbstractWordView>();
		unprotectedAreaWords = new Hashtable<Long, AbstractWordView>();

		if (p == null && un == null) {
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
			Collection<AbstractWordView> unprotectedWordViewTemps = un
					.getUnprotectedView();
			Collection<AbstractWordView> protectedWordViewTemps = p
					.getProtectedView();
			for (AbstractWordView abs : unprotectedWordViewTemps) {
				Position position = abs.getPosition();
				WordView wordView = (WordView) abs;
				Word w = wordView.getWord();
				WordView view = new WordView(w, position);
				panel.add(view.label);
				addUnprotectedAbstractWordView(view);
			}
			for (AbstractWordView abs : protectedWordViewTemps) {
				if (abs instanceof WordView) {
					Position position = abs.getPosition();
					WordView wordView = (WordView) abs;
					Word w = wordView.getWord();
					WordView view = new WordView(w, position);
					panel.add(view.label);
					addProtectedAbstractWordView(view);
					System.out.println(wordView.getWord());
				} else if (abs instanceof RowView) {
					Row r = (Row) abs.getWord();
					List<WordView> list = ((RowView) abs).getWordViews();
					for (WordView w : list) {
						panel.add(w.label);
						addProtectedAbstractWordView(w);
					}
					RowView rowView = new RowView(r, abs.getPosition(), this);
					addProtectedAbstractWordView(rowView);
					for (WordView w : list) {
						removeProtectedAbstractWordView(w);
					}
				} else {
					// This is for PoemView
					Poem poem = (Poem) abs.getWord();
					List<RowView> rowList = ((PoemView) abs).getRowViews();
					for (RowView rowView : rowList) {
						List<WordView> list = rowView.getWordViews();
						for (WordView w : list) {
							panel.add(w.label);
							addProtectedAbstractWordView(w);
						}

						addProtectedAbstractWordView(rowView);
					}
					PoemView poemView = new PoemView(poem, abs.getPosition(), this);
					addProtectedAbstractWordView(poemView);
					for (RowView rowView : rowList) {
						List<WordView> list = rowView.getWordViews();
						for (WordView w : list) {
							removeProtectedAbstractWordView(w);
						}
						removeProtectedAbstractWordView(rowView);
					}
				}

			}
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
		panel.add(btnRedo);

		btnUndo = new JButton("UNDO");
		btnUndo.setBounds(144, 0, 70, 20);
		panel.add(btnUndo);

		ButtonController buttonController = new ButtonController(this, gameState);
		btnPublish.addActionListener(buttonController);
		btnRedo.addActionListener(buttonController);
		btnUndo.addActionListener(buttonController);

		exploreArea = new ExploreArea(gameState, this);
		// JPanel explorePanel = new JPanel();
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

	public void addProtectedAbstractWordView(AbstractWordView newWord) {
		protectedAreaWords.put(newWord.getWord().getId(), newWord);
	}

	public void addUnprotectedAbstractWordView(AbstractWordView newWord) {
		unprotectedAreaWords.put(newWord.getWord().getId(), newWord);
	}

	/**
	 * Removes a word view from the protected area
	 * 
	 * @param oldWord
	 *            The word to remove
	 * @return Returns whether the word was successfully removed
	 */
	public boolean removeProtectedAbstractWordView(AbstractWordView oldWord) {
		AbstractWordView removed = protectedAreaWords.remove(oldWord.getWord()
				.getId());
		return oldWord.equals(removed);
	}

	public boolean removeUnprotectedAbstractWordView(AbstractWordView oldWord) {
		AbstractWordView removed = unprotectedAreaWords.remove(oldWord
				.getWord().getId());
		return oldWord.equals(removed);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// g.drawLine(10, Constants.PROTECTED_AREA_HEIGHT, 405,
		// Constants.PROTECTED_AREA_HEIGHT);
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
	 * @param controller
	 *            The controller to handle user input
	 */
	public void addMouseInputController(MouseAdapter controller) {
		panel.addMouseListener(controller);
		panel.addMouseMotionListener(controller);
	}

	public AbstractWordView getProtectedAbstractWordById(long id) {
		return protectedAreaWords.get(id);
	}

	public AbstractWordView getUnprotectedAbstractWordById(long id) {
		return unprotectedAreaWords.get(id);
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
		return unprotectedAreaWords.values();
	}

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
	public boolean isMoveOutOfBounds(AbstractWordView wordView,
			Position toPosition) {
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

	public UnprotectedMemento getUnprotectedState() {
		return new UnprotectedMemento(this.unprotectedAreaWords.values());
	}

	public ProtectedMemento getProtectedState() {
		return new ProtectedMemento(this.protectedAreaWords.values());
	}

	public Collection<AbstractWordView> getUnprotectedWordView() {
		return this.unprotectedAreaWords.values();
	}

	public Collection<AbstractWordView> getProtectedWordView() {
		return this.protectedAreaWords.values();
	}
	
	public void recordUndoMove(UndoMove move) {
		moves.add(move);
		if(!moves.isEmpty()) {
			this.getUndoButton().setEnabled(true);
		}
	}
	
	public void recordRedoMove(UndoMove move) {
		redoMoves.add(move);
		if(!redoMoves.isEmpty()) {
			this.getRedoButton().setEnabled(true);
		}
	}

	public UndoMove removeLastUndoMove() {
		if (moves.isEmpty()) { return null; }
		return moves.pop();
	}
	
	public UndoMove removeLastRedoMove() {
		if (redoMoves.isEmpty()) { return null; }
		return redoMoves.pop();
	}

	public Stack<UndoMove> getUndoMoves(){
		return this.moves;
	}
	
	public Stack<UndoMove> getRedoMoves() {
		return this.redoMoves;
	}
	
	public ExploreArea getExploreArea() {
		return exploreArea;
	}

	public SwapAreaView getSwapAreaView() { return swapAreaView; }

	public JButton getPublishButton() {
		return btnPublish;
	}

	public JButton getRedoButton() {
		return btnRedo;
	}

	public JButton getUndoButton() {
		return btnUndo;
	}

	public MouseInputController getMouseInputController() {
		return mouseInputController;
	}

	public void addLabelOf(WordView wordView) {
		panel.add(wordView.label);
	}

	public void removeLabelOf(WordView wordView) {
		panel.remove(wordView.label);
	}
}
