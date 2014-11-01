package views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

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
import models.protectedMemento;
import models.unprotectedMemento;
import common.Constants;
import controllers.ButtonController;

/**
 * The main view that tracks all other views
 * 
 * Created by Yumou on 10/4/2014.
 */
public class MainView extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4809871727043385666L;
	Hashtable<Long, AbstractWordView> protectedAreaWords;
	Hashtable<Long, AbstractWordView> unprotectedAreaWords;
	Collection<AbstractWordView> unprotectedWordViews;
	Collection<AbstractWordView> protectedWordViews;
	Container contentPane;
	public JPanel panel;
	public ExploreArea exploreArea;
	SelectionBox selectionBox;
	public JButton btnRedo;
	public JButton btnUndo;
	public JButton btnSave;
	public JButton btnSwap;

	/**
	 * Constructor
	 * 
	 * @param gameState
	 *            The GameState that this view represents
	 */
	public MainView(GameState gameState, unprotectedMemento un,
			protectedMemento p) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = getContentPane();
		contentPane.setLayout(null);

		setTitle("CyberPoetrySlam");

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(0, 0, Constants.AREA_WIDTH, Constants.AREA_HEIGHT);
		panel.setLayout(null);

		protectedAreaWords = new Hashtable<Long, AbstractWordView>();
		unprotectedAreaWords = new Hashtable<Long, AbstractWordView>();
		unprotectedWordViews = new HashSet<AbstractWordView>();
		protectedWordViews = new HashSet<AbstractWordView>();

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
				protectedWordViews.add(view);
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
				unprotectedWordViews.add(view);
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
				unprotectedWordViews.add(view);
			}
			for (AbstractWordView abs : protectedWordViewTemps) {
				if (abs instanceof WordView) {
					Position position = abs.getPosition();
					WordView wordView = (WordView) abs;
					Word w = wordView.getWord();
					WordView view = new WordView(w, position);
					panel.add(view.label);
					addProtectedAbstractWordView(view);
					protectedWordViews.add(view);
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
					protectedWordViews.add(rowView);
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
					PoemView poemView = new PoemView(poem, abs.getPosition(),
							this);
					protectedWordViews.add(poemView);
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

		btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSave.setBounds(0, 0, 70, 20);
		panel.add(btnSave);

		btnRedo = new JButton("REDO");
		btnRedo.setBounds(72, 0, 70, 20);
		panel.add(btnRedo);

		btnUndo = new JButton("UNDO");
		btnUndo.setBounds(144, 0, 70, 20);
		panel.add(btnUndo);

		ButtonController buttonController = new ButtonController(this);
		btnSave.addActionListener(buttonController);
		btnRedo.addActionListener(buttonController);
		btnUndo.addActionListener(buttonController);

		exploreArea = new ExploreArea(gameState);
		// JPanel explorePanel = new JPanel();
		JPanel explorePanel = exploreArea.contentPane;
		explorePanel.setBorder(new LineBorder(Color.BLACK));
		explorePanel.setBounds(716, 310, 284, 353);
		contentPane.add(explorePanel);
		explorePanel.setLayout(null);

		JPanel swapPanel = new JPanel();
		swapPanel.setBorder(new LineBorder(Color.BLACK));
		swapPanel.setBounds(716, 0, 284, 310);
		contentPane.add(swapPanel);
		swapPanel.setLayout(null);

		btnSwap = new JButton("SWAP");
		btnSwap.setBounds(0, 0, 70, 20);
		swapPanel.add(btnSwap);
		btnSwap.addActionListener(buttonController);

		// Puts the selectionBox on the pane in front of the words.
		// The selectionBox can be set to visible or not from the selectionBox
		// class
		selectionBox = new SelectionBox();
		setGlassPane(selectionBox);
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
		AbstractWordView removed = protectedAreaWords.remove(oldWord.getWord().getId());
		return oldWord.equals(removed);
	}

	public boolean removeUnprotectedAbstractWordView(AbstractWordView oldWord) {
		AbstractWordView removed = unprotectedAreaWords.remove(oldWord.getWord().getId());
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
	public boolean isMoveOutOfBounds(AbstractWordView wordView, Position toPosition) {
		// get AbsctracWordView width
		int width = 0;
		if (wordView instanceof WordView) {
			width = wordView.getWord().getValue().replaceAll(" ", "").length() * 8;
		} else if (wordView instanceof RowView) {
			width = wordView.getWord().getValue().replaceAll(" ", "").length() * 8;
		}
		// if it is a poemView, get is maximum width
		else {
			PoemView poemView = (PoemView) wordView;
			Collection<RowView> rowViewList = poemView.getRowViews();
			for (RowView rowView : rowViewList) {
				if (rowView.getWord().getValue().replaceAll(" ", "").length() * 8 > width) {
					width = rowView.getWord().getValue().replaceAll(" ", "").length() * 8;
				}
			}
		}

		if (toPosition.getX() < 0) {
			return true;
		} else if (toPosition.getX() + width > 716) {
			return true;
		} else if (toPosition.getY() < 20) {
			return true;
		} else if (toPosition.getY() + 20 > 663) {
			return true;
		} else {
			return false;
		}
	}

	public unprotectedMemento getUnprotectedState() {
		return new unprotectedMemento(this.unprotectedAreaWords.values());
	}

	public protectedMemento getProtectedState() {
		return new protectedMemento(this.protectedAreaWords.values());
	}

	public Collection<AbstractWordView> getUnprotectedWordView() {
		// return this.unprotectedWordViews;
		return this.unprotectedAreaWords.values();
	}

	public Collection<AbstractWordView> getProtectedWordView() {
		// return this.protectedWordViews;
		return this.protectedAreaWords.values();
	}

}
