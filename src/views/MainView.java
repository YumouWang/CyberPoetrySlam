package views;

import model.Position;
import model.Word;
import model.WordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

/**
 * The main view that tracks all other views
 *
 * Created by Nathan on 10/3/2014.
 */
public class MainView extends JFrame implements MouseListener, MouseMotionListener {

    Collection<AbstractWordView> words;

    AbstractWordView selectedWord;
    JPanel contentPane;
    Position mouseDownPosition;

    public MainView() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 408);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        words = new HashSet<AbstractWordView>();

        Hashtable<String,WordType> wordsList = new Hashtable<String,WordType>();
        wordsList.put("word1", WordType.ADJECTIVE);
        wordsList.put("word2", WordType.ADVERB);
        wordsList.put("word3", WordType.NOUN);
        wordsList.put("word4", WordType.VERB);
        
        Word[] wordList = new Word[wordsList.size()];
        AbstractWordView[] wordViewList = new AbstractWordView[wordsList.size()]; 

        int i = 0;
        for(Iterator it = wordsList.keySet().iterator(); it.hasNext();) {
        	String key = it.next().toString();
        	WordType value = wordsList.get(key);
        	wordList[i] = new Word(key,value);
        	Random random = new Random();
        	int x = random.nextInt(300);
        	int y = random.nextInt(200);
        	wordViewList[i] = new AbstractWordView(wordList[i], new Position(x, y));
        	words.add(wordViewList[i]);
        	contentPane.add(wordViewList[i].getLabel());
        	i ++;
        }
        contentPane.addMouseListener(this);
        contentPane.addMouseMotionListener(this);
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
            selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
            boolean isOverlapping = false;
            boolean isAdjacent = false;
            for (AbstractWordView word : words) {
                if(!word.equals(selectedWord)) {
                    if (word.isOverlapping(selectedWord)) {
                        isOverlapping = true;
                    }
                    AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
                    if(adjacencyType != AdjacencyType.NOT_ADJACENT) {
                        isAdjacent = true;
                        word.label.setBackground(Color.GREEN);
                    } else {
                        word.label.setBackground(Color.LIGHT_GRAY);
                    }
                }
            }
            if(isOverlapping) {
                selectedWord.label.setBackground(Color.RED);
            } else if (isAdjacent) {
                selectedWord.label.setBackground(Color.GREEN);
            } else {
                selectedWord.label.setBackground(Color.LIGHT_GRAY);
            }
        }
        mouseDownPosition = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
