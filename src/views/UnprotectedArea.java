package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import models.AbstractWord;
import models.GameState;
import models.Position;
import models.Word;
import models.WordType;

import javax.swing.border.LineBorder;

public class UnprotectedArea extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * Create the panel.
	 */
	AreaView areaView;
	GameState gameState;
	Collection<AbstractWordView> words;
	public AbstractWordView selectedWord;
	Position mouseDownPosition;
	JPopupMenu popupMenu;
	JPanel panel;
	MenuItemMonitor menuItemMonitor;
	
	public AbstractWordView clickedWord;
	public AbstractWordView currentWord;
	public Word[] wordList;
	public AbstractWordView[] wordViewList;
	
	public UnprotectedArea(AreaView areaView, GameState gameState) {
        this.areaView = areaView;
        this.gameState = gameState;
    }
	
	public void Unprotect(GameState gameState) {
		words = new HashSet<AbstractWordView>();
		
		Collection<AbstractWord> wordsList = new HashSet<AbstractWord>();
		
		wordsList = gameState.getProtectedArea().getAbstractWordCollection();
		
        wordList = new Word[wordsList.size()];
        wordViewList = new AbstractWordView[wordsList.size()];
        selectedWord = null;
        
        for(AbstractWord word: wordsList) {
        	
        	Random random = new Random();
        	int x = random.nextInt(300);
        	int y = random.nextInt(100);
        	AbstractWordView view = new AbstractWordView(word, new Position(x, y));
        	words.add(view);
        	add(view.label);
        }
        setLayout(null);
        
        panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(0, 0, 450, 300);
        add(panel);
        
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
	}
        
        @Override
        public void mousePressed(MouseEvent e) {
            mouseDownPosition = new Position(e.getX(), e.getY());
            selectedWord = null;
            for(AbstractWordView word: words) {
                if(word.isClicked(mouseDownPosition)) {
                    selectedWord = word;       
                    break;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(selectedWord != null) {
                Position positionDiff = new Position(e.getX() - mouseDownPosition.getX(), e.getY() - mouseDownPosition.getY());
                Position originalPosition = new Position(selectedWord.getPosition().getX(), selectedWord.getPosition().getY());
                selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
                if(selectedWord.getPosition().getX() < 0 || selectedWord.getPosition().getX() > 370 || selectedWord.getPosition().getY() < 0 || selectedWord.getPosition().getY() > 110) {
                	selectedWord.moveTo(originalPosition);
                }
            }
            
            mouseDownPosition = new Position(e.getX(), e.getY());
        }

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {			
			if(selectedWord != null) {
				menuItemMonitor = new MenuItemMonitor();
				popupMenu = new JPopupMenu();
				String command = "protect"; 
				JMenuItem item = new JMenuItem(command);
				popupMenu.add(item);
				item.setActionCommand(command); 
				item.addActionListener(menuItemMonitor); 
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());  
				}
			}
		}
		
		private class MenuItemMonitor implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent event) {
	            System.out.println(selectedWord.getWord().getValue());
	            //selectedWord.label.setVisible(false);
	            areaView.removeAbstractWordView(selectedWord);
	        } 
	    } 
}
