package views;

import controllers.SearchController;
import models.AbstractWord;
import models.GameState;
import models.Word;
import models.WordType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Class for explore area
 *
 * @author Yumou
 * @version 10/3/2014
 */
public class ExploreArea extends JFrame implements Serializable {

    /**
     *Serialized ID for an exploreArea
     */
    private static final long serialVersionUID = -3913212348104753802L;
    /**
     * JPanel for the ExploreArea
     */
    public JPanel contentPane;
    /**
     * GameState and MainView of the game
     */
    GameState gameState;
    MainView mainView;
    SearchController search;
    /**
     * An array to store IDs of each row
     */
    long[] rowId;
    /**
     * Fields for ExploreArea
     */
    private JTextField textField;
    private JComboBox<String> comboBox;
    private String input;
    private JTable table;
    private JScrollPane jScrollPane;
    private JButton btnNewButton;
    private Object[][] cellData;

/**
 * Create the frame.
 * @param gameState
 * @param mainView
 */
    public ExploreArea(GameState gameState, final MainView mainView) {
        this.gameState = gameState;
        this.search = new SearchController(mainView, gameState);
        this.mainView = mainView;

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

        comboBox = new JComboBox<String>();
        comboBox.setBounds(20, 44, 232, 21);
        contentPane.add(comboBox);
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(
                new String[]{null, WordType.ADJECTIVE.name(),
                        WordType.ADVERB.name(), WordType.CONJUNCTION.name(),
                        WordType.INTERJECTION.name(), WordType.NOUN.name(),
                        WordType.POSTFIX.name(), WordType.PREFIX.name(),
                        WordType.PREPOSITION.name(), WordType.PRONOUN.name(),
                        WordType.VERB.name(), WordType.DETERMINER.name(),
                        WordType.SUFFIX.name(), WordType.PRONOUNS.name(),
                        WordType.NUMBER.name()}));

        table = getTable();

        table.setBorder(new LineBorder(Color.BLACK));
        table.setBackground(Color.LIGHT_GRAY);
        table.setBounds(44, 172, 346, 67);
        table.setCellSelectionEnabled(true);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(95);

        jScrollPane = new JScrollPane();
        jScrollPane.setBounds(20, 75, 233, 268);
        contentPane.add(jScrollPane);
        jScrollPane.setViewportView(table);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    updateTable();
                    table = (JTable) e.getSource();
                    int row = table.getSelectedRow();
                    if (row > -1) {
                        Collection<AbstractWordView> words = mainView
                                .getUnprotectedAreaWords();
                        for (AbstractWordView word : words) {

                            if (word.getWord().getId() == rowId[row]) {
                                word.setBackground(Color.orange);
                            } else {
                                word.setBackground(Color.LIGHT_GRAY);
                            }

                        }
                    } else {
                        
                    }
                }
            }
        });

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    updateTable();
                }
            }
        });
    }
/**
 * Update the word table of explore area
 */
    public void updateTable() {
        search.updateWordTable();
        if (comboBox.getSelectedIndex() == -1) {
            input = "";
        } else {
            input = comboBox.getSelectedItem().toString();
        }
        Collection<AbstractWord> result = search.search(textField.getText(),
                input);

        for (int rowNum = 0; rowNum < cellData.length; rowNum++) {
            table.setValueAt(null, rowNum, 0);
            table.setValueAt(null, rowNum, 1);
            table.setValueAt(null, rowNum, 2);
        }
        int i = 0;
        for (AbstractWord word : result) {
            table.setValueAt(i + 1, i, 0);
            table.setValueAt(word.getValue(), i, 1);
            table.setValueAt(((Word) word).getType().toString(), i, 2);
            rowId[i] = word.getId();
            i++;
        }
        table.updateUI();
    }
/**
 * Refresh the unprotected area
 */
    public void refresh() {
        revalidate();
        repaint();
        table.updateUI();
    }
/**
 * Helper method to get the word table
 * @return JTable
 */
    private JTable getTable() {
        rowId = new long[1000];
        String[] columnNames = {"No.", "Word", "WordType"};
        Collection<AbstractWord> unprotectedWords = gameState
                .getUnprotectedArea().getAbstractWordCollection();
        Collection<AbstractWord> protectedWords = gameState.getProtectedArea()
                .getAbstractWordCollection();
        cellData = new String[unprotectedWords.size() + protectedWords.size()
                + 10][3];
        int i = 0;
        for (AbstractWord word : unprotectedWords) {
            cellData[i][0] = String.valueOf(i + 1);
            cellData[i][1] = word.getValue();
            cellData[i][2] = ((Word) word).getType().toString();
            rowId[i] = word.getId();
            i++;
        }
        DefaultTableModel model = new DefaultTableModel(cellData, columnNames) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        return new JTable(model) {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return getPreferredSize();
            }
        };
    }
}
