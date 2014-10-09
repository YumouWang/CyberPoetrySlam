package views;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.AbstractWord;
import models.GameState;
import models.Position;
import controllers.MouseController;

public class View extends JFrame {
	
	Collection<AbstractWordView> protectedAreaWords;
	Collection<AbstractWordView> unprotectedAreaWords;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GameState gameState = new GameState();
		View view = new View(gameState);
		view.addMouseInputController(new MouseController(view, gameState));
        view.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public View(GameState gameState) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 0, 400, 437);
		//contentPane.add(panel);
		
		protectedAreaWords = new HashSet<AbstractWordView>();
		unprotectedAreaWords = new HashSet<AbstractWordView>();

        Random random = new Random();
        Collection<AbstractWord> words = gameState.getProtectedArea().getAbstractWordCollection();
        for(AbstractWord word: words) {
            int x = random.nextInt(300);
            int y = random.nextInt(200);
            AbstractWordView view = new AbstractWordView(word, new Position(x, y));
            addAbstractWordView(view);
        }
        contentPane.add(panel);
        
        JPanel explorePanel = new JPanel();
        explorePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        explorePanel.setBounds(400, 184, 284, 253);
        contentPane.add(explorePanel);
        explorePanel.setLayout(null);
        
        JPanel swapPanel = new JPanel();
        swapPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        swapPanel.setBounds(400, 0, 284, 184);
        contentPane.add(swapPanel);
        swapPanel.setLayout(null);
	}
	
	public void addAbstractWordView(AbstractWordView newWord) {
        unprotectedAreaWords.add(newWord);
        contentPane.add(newWord.label);
    }
	
	public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
	
	public void addMouseInputController(MouseController controller) {
        contentPane.addMouseListener(controller);
        contentPane.addMouseMotionListener(controller);
    }
	
	public Collection<AbstractWordView> getProtectedAreaWords() {
        return protectedAreaWords;
    }
	
	public Collection<AbstractWordView> getUnprotectedAreaWords() {
        return unprotectedAreaWords;
    }
	
	
}
