package views;

import model.Position;
import model.Word;
import model.WordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.HashSet;

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

        Word myWordOne = new Word("Monty Python", WordType.INTERJECTION);
        AbstractWordView wordViewOne = new AbstractWordView(myWordOne, new Position(100, 50));
        Word myWordTwo = new Word("Flying Circus", WordType.CONJUNCTION);
        AbstractWordView wordViewTwo = new AbstractWordView(myWordTwo, new Position(50, 100));

        words.add(wordViewOne);
        words.add(wordViewTwo);

        contentPane.add(wordViewOne.getLabel());
        contentPane.add(wordViewTwo.getLabel());
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
