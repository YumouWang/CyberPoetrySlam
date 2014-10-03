package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.Position;

/**
 * The main view that tracks all other views
 * Created by Nathan on 10/3/2014.
 */
public class MainView extends JFrame implements MouseListener {

    JLabel label;
    JPanel contentPane;
    Position mouseDownPosition;

    public MainView() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 408);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        label = new JLabel("text");
        label.setBounds(100, 100, 30, 20);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setBackground(Color.LIGHT_GRAY);
        label.setOpaque(true);
        contentPane.add(label);
        label.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownPosition = new Position(e.getX(), e.getY());
//        label.setText(mouseDownPosition.getX() + ", " + mouseDownPosition.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position positionDiff = new Position(e.getX() - mouseDownPosition.getX(), e.getY() - mouseDownPosition.getY());
        label.setBounds(label.getX() + positionDiff.getX(), label.getY() + positionDiff.getY(), label.getWidth(), label.getHeight());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
