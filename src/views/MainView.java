package views;

import model.Position;
import model.Word;
import model.WordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Search;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

/**
 * The main view that tracks all other views
 * Created by Nathan on 10/3/2014.
 */
public class MainView extends JFrame implements MouseListener {

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

        Hashtable<String,WordType> wordslist = new Hashtable<String,WordType>();
        wordslist.put("word1", WordType.ADJECTIVE);
        wordslist.put("word2", WordType.ADVERB);
        wordslist.put("word3", WordType.NOUN);
        wordslist.put("word4", WordType.VERB);
        
        Word[] wordlist = new Word[wordslist.size()];
        AbstractWordView[] wordViewList = new AbstractWordView[wordslist.size()]; 
        
        
        Enumeration e = wordslist.keys();
        int i = 0;
        for(Iterator it = wordslist.keySet().iterator(); it.hasNext();) {
        	String key = it.next().toString();
        	WordType value = wordslist.get(key);
        	wordlist[i] = new Word(key,value);
        	Random random = new Random();
        	int x = random.nextInt(300);
        	int y = random.nextInt(200);
        	wordViewList[i] = new AbstractWordView(wordlist[i], new Position(x, y));
        	words.add(wordViewList[i]);
        	contentPane.add(wordViewList[i].getLabel());
        	i ++;
        }
        
//        Word myWordOne = new Word("Monty Python", WordType.INTERJECTION);
//        AbstractWordView wordViewOne = new AbstractWordView(myWordOne, new Position(100, 50));
//        Word myWordTwo = new Word("Flying Circus", WordType.CONJUNCTION);
//        AbstractWordView wordViewTwo = new AbstractWordView(myWordTwo, new Position(50, 100));
//
//        words.add(wordViewOne);
//        words.add(wordViewTwo);
//
//        contentPane.add(wordViewOne.getLabel());
//        contentPane.add(wordViewTwo.getLabel());
        contentPane.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

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
    public void mouseReleased(MouseEvent e) {
        Position positionDiff = new Position(e.getX() - mouseDownPosition.getX(), e.getY() - mouseDownPosition.getY());
        selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
