package views;

import java.awt.Color;
import java.awt.Graphics;
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

public class MainGUI extends JFrame {
	
	Collection<AbstractWordView> protectedAreaWords;
	Collection<AbstractWordView> unprotectedAreaWords;
	private JPanel contentPane;
	JPanel panel;
	Graphics g;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GameState gameState = new GameState();
		MainGUI view = new MainGUI(gameState);
		view.addMouseInputController(new MouseController(view, gameState));
        view.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public MainGUI(GameState gameState) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 0, 400, 437);
		panel.setLayout(null);
		g = panel.getGraphics();
		//g.drawLine(0, 0, 50, 50);
		//contentPane.add(panel);
		
		protectedAreaWords = new HashSet<AbstractWordView>();
		unprotectedAreaWords = new HashSet<AbstractWordView>();

        Random random = new Random();
        Collection<AbstractWord> protectedWords = gameState.getProtectedArea().getAbstractWordCollection();
        for(AbstractWord word: protectedWords) {
            int x = random.nextInt(300);
            int y = random.nextInt(200);
            WordView view = new WordView(word, new Position(x, y));
            addProtectedWordView(view);
        }
        
        Collection<AbstractWord> unprotectedWords = gameState.getUnprotectedArea().getAbstractWordCollection();
        for(AbstractWord word: unprotectedWords) {
            int x = random.nextInt(300);
            int y = random.nextInt(100) + 300;
            WordView view = new WordView(word, new Position(x, y));
            addUnprotectedWordView(view);
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
	
	public void addProtectedWordView(WordView newWord) {
        protectedAreaWords.add(newWord);
        panel.add(newWord.label);
    }
	
	public void addUnprotectedWordView(WordView newWord) {
        unprotectedAreaWords.add(newWord);
        panel.add(newWord.label);
    }
	
	public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }
	
	public void addMouseInputController(MouseController controller) {
        panel.addMouseListener(controller);
        panel.addMouseMotionListener(controller);
    }
	
	public Collection<AbstractWordView> getProtectedAreaWords() {
        return protectedAreaWords;
    }
	
	public Collection<AbstractWordView> getUnprotectedAreaWords() {
        return unprotectedAreaWords;
    }
	
	
}
