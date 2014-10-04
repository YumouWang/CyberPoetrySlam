package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Area extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Area() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		UnprotectedArea upa = new UnprotectedArea();
		upa.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		ProtectedArea pa = new ProtectedArea();
		
	
		upa.setBounds(0, 141, 433, 121);
		contentPane.setLayout(null);
		contentPane.add(upa);
		upa.setLayout(null);
		
		pa.setBounds(0, 0, 433, 141);
		contentPane.setLayout(null);
		contentPane.add(pa);
		pa.setLayout(null);
		
	}

}
