package views;

import controllers.MouseInputController;
import models.Position;
import models.Word;
import models.WordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

/**
 * The main view that tracks all other views
 *
 * Created by Nathan on 10/3/2014.
 */
public class MainView extends JFrame {

    static MainView instance;

    Collection<AbstractWordView> words;
    JPanel contentPane;

    public static MainView getInstance() {
        if(instance == null) {
            instance = new MainView();
        }
        return instance;
    }

    private MainView() {
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
        	contentPane.add(wordViewList[i].label);
        	i ++;
        }
        MouseInputController mouseInputController = MouseInputController.getInstance();
        contentPane.addMouseListener(mouseInputController);
        contentPane.addMouseMotionListener(mouseInputController);
    }

    public void addAbstractWordView(AbstractWordView newWord) {
        words.add(newWord);
        contentPane.add(newWord.label);
    }

    public boolean removeAbstractWordView(AbstractWordView oldWord) {
        contentPane.remove(oldWord.label);
        return words.remove(oldWord);
    }

    public void refresh() {
        contentPane.revalidate();
        contentPane.repaint();
    }

    public Collection<AbstractWordView> getWords() {
        return words;
    }
}
