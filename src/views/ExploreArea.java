package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controllers.Search;

public class ExploreArea extends JFrame {

	public JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private JComboBox<String> comboBox;
	private String input;
	private JTable table;
	private JScrollPane jScrollPane;
	private JButton btnNewButton;
	
	Object[][] cellData;
	/**
	 * Create the frame.
	 */
	public ExploreArea() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 278, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 284, 253);
		
		btnNewButton = new JButton("Search");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		
		btnNewButton.setBounds(174, 11, 78, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(20, 11, 144, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
//		textArea = new JTextArea();
//		textArea.setBounds(44, 81, 346, 67);
//		contentPane.add(textArea);
		
//		Search s = Search.getInstance();
//		s.initTable();
//		textArea.setText(s.wordtable.toString());
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(20, 44, 232, 21);
		contentPane.add(comboBox);
		comboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { null, "noun", "adverb", "adjective", "verb" }));
		
		
		Search.getInstance().initTable();
		//System.out.println(Search.wordtable);
		cellData = new String[Search.wordtable.size()][2];
		int i = 0;
		String[] columnNames = {"Word", "WordType"};
		for(Iterator it = Search.wordtable.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			String value = Search.wordtable.get(key);
			//cellData[i] = new String[2]; 
			cellData [i][0] = key;
			cellData [i][1] = value;
			i++;
		}
		
		table = new JTable(cellData,columnNames);
		
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(44, 172, 346, 67);
		
		jScrollPane = new JScrollPane();
		jScrollPane.setBounds(20, 75, 232, 168);
		contentPane.add(jScrollPane);
		jScrollPane.setViewportView(table);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Search search = new Search();
				search.initTable();
				if(textField.getText().equals("")) {
				}
				if(comboBox.getSelectedIndex() == -1) {
					input = "";
				}
				else {
					input =  comboBox.getSelectedItem().toString();
				}
				Hashtable<String, String> result = search.search(textField.getText(), input);
//				if(result.isEmpty()) {
//					textArea.setText("No result!");
//				}
//				else {
//					textArea.setText(result.toString());
//				}
				
				for(int rowNum = 0; rowNum < cellData.length; rowNum ++) {
					cellData [rowNum][0] = null;
					cellData [rowNum][1] = null;
				}
				int i = 0;
				for(Iterator it = result.keySet().iterator(); it.hasNext();) {
					String key = it.next().toString();
					String value = result.get(key);
					cellData [i][0] = key;
					cellData [i][1] = value;
					i++;
				}
				
				table.updateUI();
						  
			}
		});
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
	            public void keyPressed(KeyEvent e) {
	                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	                	textArea.setText(textField.getText() + comboBox.getSelectedItem());
	                	Search search = new Search();
	    				search.initTable();
	    				if(textField.getText().equals("")) {
	    				}
	    				if(comboBox.getSelectedIndex() == -1) {
	    					input = "";
	    				}
	    				else {
	    					input =  comboBox.getSelectedItem().toString();
	    				}
	    				Hashtable<String, String> result = search.search(textField.getText(), input);
//	    				if(result.isEmpty()) {
//	    					textArea.setText("No result!");
//	    				}
//	    				else {
//	    					textArea.setText(result.toString());
//		                }		
	                }
	            }
	        });     
	}
}
