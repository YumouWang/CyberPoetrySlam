package views;

import controllers.SearchController;
import models.AbstractWord;
import models.GameState;
import models.Word;
import models.WordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Collection;

/**
 * Class for explore area
 * 
 * Created by Yumou on 10/3/2014.
 */
public class ExploreArea extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3913212348104753802L;
	public JPanel contentPane;
	private JTextField textField;
	private JComboBox<String> comboBox;
	private String input;
	private JTable table;
	private JScrollPane jScrollPane;
	private JButton btnNewButton;
	private Object[][] cellData;
	GameState gameState;
	MainView mainView;
	SearchController search;

	/**
	 * Create the frame.
	 */
	public ExploreArea(GameState gameState) {
		this.gameState = gameState;
		this.search = new SearchController(mainView, gameState);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

		// textArea = new JTextArea();
		// textArea.setBounds(44, 81, 346, 67);
		// contentPane.add(textArea);

		// Search s = Search.getInstance();
		// s.initTable();
		// textArea.setText(s.wordtable.toString());

		comboBox = new JComboBox<String>();
		comboBox.setBounds(20, 44, 232, 21);
		contentPane.add(comboBox);
		comboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
				new String[] { null, WordType.ADJECTIVE.name(),
						WordType.ADVERB.name(), WordType.CONJUNCTION.name(),
						WordType.INTERJECTION.name(), WordType.NOUN.name(),
						WordType.POSTFIX.name(), WordType.PREFIX.name(),
						WordType.PREPOSITION.name(), WordType.PRONOUN.name(),
						WordType.VERB.name() }));

		// Search.getInstance().initTable();
		// System.out.println(Search.wordtable);
		String[] columnNames = { "Word", "WordType" };
		Collection<AbstractWord> unprotectedWords = gameState
				.getUnprotectedArea().getAbstractWordCollection();
		Collection<AbstractWord> protectedWords = gameState
				.getProtectedArea().getAbstractWordCollection();
		cellData = new String[unprotectedWords.size() + protectedWords.size()][2];
		int i = 0;
		for (AbstractWord word : unprotectedWords) {
			// cellData[i] = new String[2];
			cellData[i][0] = word.getValue();
			// System.out.println(word.getValue() + "," + word.getType());
			cellData[i][1] = ((Word) word).getType().toString();
			i++;
		}

		table = new JTable(cellData, columnNames);

		table.setBorder(new LineBorder(Color.BLACK));
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(44, 172, 346, 67);
		table.setEnabled(false);

		jScrollPane = new JScrollPane();
		jScrollPane.setBounds(20, 75, 232, 268);
		contentPane.add(jScrollPane);
		jScrollPane.setViewportView(table);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				search.updateWordTable();
				if (comboBox.getSelectedIndex() == -1) {
					input = "";
				} else {
					input = comboBox.getSelectedItem().toString();
				}
				Collection<AbstractWord> result = search.search(
						textField.getText(), input);
				for (int rowNum = 0; rowNum < cellData.length; rowNum++) {
					cellData[rowNum][0] = null;
					cellData[rowNum][1] = null;
				}
				int i = 0;
				for (AbstractWord word : result) {
					cellData[i][0] = word.getValue();
					cellData[i][1] = ((Word) word).getType().toString();
					i++;
				}
				table.updateUI();
			}
		});

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search.updateWordTable();
					if (comboBox.getSelectedIndex() == -1) {
						input = "";
					} else {
						input = comboBox.getSelectedItem().toString();
					}
					Collection<AbstractWord> result = search.search(
							textField.getText(), input);

					for (int rowNum = 0; rowNum < cellData.length; rowNum++) {
						cellData[rowNum][0] = null;
						cellData[rowNum][1] = null;
					}
					int i = 0;
					for (AbstractWord word : result) {
						cellData[i][0] = word.getValue();
						cellData[i][1] = ((Word) word).getType().toString();
						i++;
					}
					table.updateUI();
				}
			}
		});
	}

	public void refresh() {
		revalidate();
		repaint();
		table.updateUI();
	}
}
