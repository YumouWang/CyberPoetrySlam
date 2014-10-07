package views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;

import models.GameState;
import models.Position;

public class AreaView extends JFrame {

	JSplitPane splitPane;
	JPanel p1, p2, p3;
	JButton bt;
	UnprotectedArea upa;
	ProtectedArea pa;

	public AreaView(GameState gameState) {

		upa = new UnprotectedArea(this, gameState);
		upa.Unprotect(gameState);
		upa.setBorder(new LineBorder(new Color(0, 0, 0)));

		pa = new ProtectedArea(gameState);
		pa.setBorder(new LineBorder(new Color(0, 0, 0)));

		p1 = new JPanel();
		p2 = new JPanel();
		p1 = pa;
		p2 = upa;
		//setLayout(null);
		p1.setLayout(null);
		p2.setLayout(null);

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p1, p2);
        splitPane.setDividerLocation(150);
		add(splitPane, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setResizable(false);
		// setVisible(true);
		setTitle("CyberPoetrySlam");
	}

	public void removeAbstractWordView(AbstractWordView oldWord) {
		// contentPane.remove(oldWord.label);
		System.out.println(oldWord.getWord().getValue() + "---");
		upa.remove(oldWord.label);
		addAbstractWordView(oldWord);
		// oldWord.label.setVisible(false);
		// contentPane
		splitPane.updateUI();

	}

	public void addAbstractWordView(AbstractWordView newWord) {
		int x = 0;
		int y = 0;
		AbstractWordView view = new AbstractWordView(newWord.getWord(),
				new Position(x, y));
		// words.add(view);
		this.p1.remove(pa.panel);
		this.p1.add(view.label);
		this.pa.words.add(view);
//		view.label.addMouseListener(pa);
//		view.label.addMouseMotionListener(pa);
		// pa.add(pa.panel);
		this.p1.add(pa.panel);
		addMouseListener(this.pa);
		addMouseMotionListener(this.pa);
		//pa.updateUI();
	}
}
