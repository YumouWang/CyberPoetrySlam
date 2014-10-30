package views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import models.AbstractWord;
import models.GameState;
import models.Position;
import models.Word;
import common.Constants;
import controllers.ButtonController;

/**
 * The main view that tracks all other views
 *
 * Created by Yumou on 10/4/2014.
 */
public class MainView extends JFrame {

    Hashtable<Long, AbstractWordView> protectedAreaWords;
    Hashtable<Long, AbstractWordView> unprotectedAreaWords;
	Container contentPane;
	public JPanel panel;
	public ExploreArea exploreArea;
    SelectionBox selectionBox;
    public JButton btnRedo;
    public JButton btnUndo;
    public JButton btnSave;

    /**
     * Constructor
     * @param gameState The GameState that this view represents
     */
	public MainView(GameState gameState) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 475);
        contentPane = getContentPane();
        contentPane.setLayout(null);
        
        setTitle("CyberPoetrySlam");

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(0, 0, 400, 437);
		panel.setLayout(null);

		protectedAreaWords = new Hashtable<Long, AbstractWordView>();
		unprotectedAreaWords = new Hashtable<Long, AbstractWordView>();

		Random random = new Random();
		Collection<AbstractWord> protectedWords = gameState.getProtectedArea().getAbstractWordCollection();
		for (AbstractWord word : protectedWords) {
			int x = random.nextInt(300);
			int y = random.nextInt(200);
			WordView view = new WordView((Word)word, new Position(x, y));
            panel.add(view.label);
			addProtectedAbstractWordView(view);
		}
		
		Collection<AbstractWord> unprotectedWords = gameState
				.getUnprotectedArea().getAbstractWordCollection();
		for (AbstractWord word : unprotectedWords) {
			int x = random.nextInt(300);
			int y = random.nextInt(100) + 300;
			WordView view = new WordView((Word)word, new Position(x, y));
            panel.add(view.label);
			addUnprotectedAbstractWordView(view);
		}
		contentPane.add(panel);
		
		JLabel label = new JLabel("");
		label.setOpaque(true);
		label.setBounds(0, 250, 400, 2);
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
		//JPanel explorePanel = new JPanel();
		JPanel explorePanel = exploreArea.contentPane;
		explorePanel.setBorder(new LineBorder(Color.BLACK));
		explorePanel.setBounds(400, 184, 284, 253);
		contentPane.add(explorePanel);
		explorePanel.setLayout(null);

		JPanel swapPanel = new JPanel();
		swapPanel.setBorder(new LineBorder(Color.BLACK));
		swapPanel.setBounds(400, 0, 284, 184);
		contentPane.add(swapPanel);
		swapPanel.setLayout(null);

        // Puts the selectionBox on the pane in front of the words.
        // The selectionBox can be set to visible or not from the selectionBox class
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
     * @param oldWord The word to remove
     * @return Returns whether the word was successfully removed
     */
    public boolean removeProtectedAbstractWordView(AbstractWordView oldWord) {
        AbstractWordView removed = protectedAreaWords.remove(oldWord.getWord().getId());
        return removed.equals(oldWord);
    }
    
    public boolean removeUnprotectedAbstractWordView(AbstractWordView oldWord) {
        AbstractWordView removed = unprotectedAreaWords.remove(oldWord.getWord().getId());
        return removed.equals(oldWord);
    }
	
	@Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawLine(10, Constants.PROTECTED_AREA_HEIGHT, 405, Constants.PROTECTED_AREA_HEIGHT);
    }

    /**
     * Refreshes the display
     */
	public void refresh() {
        revalidate();
		repaint();
	}

    /**
     * Adds a MouseInputController to the MouseListeners and MouseMotionListeners
     * @param controller The controller to handle user input
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
     * @return A collection of word views
     */
	public Collection<AbstractWordView> getProtectedAreaWords() {
		return protectedAreaWords.values();
	}

    /**
     * Gets all the unprotected word views in this MainView
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
     * @param position
     * @return
     */
    public boolean isInProtectedArea(Position position) {
        return position.getY() < Constants.PROTECTED_AREA_HEIGHT;
    }
    
    /**
     * check a wordView moving to toPosition is out of bounds or not
     * @param wordView
     * @param toPosition
     * @return
     */
    public boolean isMoveOutOfBounds(AbstractWordView wordView, Position toPosition) {
    	// get AbsctracWordView width
    	int width = 0;
    	if(wordView instanceof WordView) {
    		width = wordView.getWord().getValue().replaceAll(" ", "").length() * 8;
    	}
    	else if(wordView instanceof RowView) {
    		width = wordView.getWord().getValue().replaceAll(" ", "").length() * 8;
    	}
    	// if it is a poemView, get is maximum width
    	else {
    		PoemView poemView = (PoemView) wordView;
    		Collection<RowView> rowViewList = poemView.getRowViews();
    		for(RowView rowView : rowViewList) {
    			if(rowView.getWord().getValue().replaceAll(" ", "").length() * 8 > width) {
    				width = rowView.getWord().getValue().replaceAll(" ", "").length() * 8;
    			}
    		}
    	}
    	
    	if(toPosition.getX() < 0) {
    		return true;
    	}
		else if(toPosition.getX() + width > 400) {
			return true;
		}
		else if(toPosition.getY() < 20) {
			return true;
		}
		else if(toPosition.getY() + 20 > 437) {
			return true;
		}
    	else {
    		return false;
    	}
    }
	
}
