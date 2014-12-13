package views;

import controllers.swap.BrokerConnectionController;
import controllers.swap.ConnectionException;
import controllers.swap.SwapController;
import models.GameState;
import models.Position;
import models.WordType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A view for the swap area
 * @author Nathan
 * @version 11/30/2014
 */
public class SwapAreaView extends AbstractView {

    private JPanel swapPanel;

    private JButton btnSwap;
    private JButton btnReconnect;

    private JPanel statusIndicator;
    private JTextPane status;

    private ArrayList<ArrayList<JComponent>> inputElements;

    private final MainView mainView;
    private final GameState gameState;

    public SwapAreaView(Position position, int width, int height, final MainView mainView, final GameState gameState) {
        super(position, width, height);
        this.mainView = mainView;
        this.gameState = gameState;

        swapPanel = new JPanel();
        swapPanel.setLayout(new BorderLayout());
        swapPanel.setBorder(new LineBorder(Color.BLACK));
        swapPanel.setBounds(position.getX(), position.getY(), width, height);

        // Split the swap area into an input area and a control area
        JPanel inputPanel = new JPanel();
        JPanel controlPanel = new JPanel();
        swapPanel.add(inputPanel, BorderLayout.CENTER);
        swapPanel.add(controlPanel, BorderLayout.PAGE_END);

        // Encapsulate the code for creating each panel
        createInputPanel(inputPanel);
        createControlPanel(controlPanel);
    }

    private void createControlPanel(JPanel controlPanel) {
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,5,0,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        statusIndicator = new JPanel();
        c.gridx = 0; c.gridy = 0;
        statusIndicator.setPreferredSize(new Dimension(25, 25));
        statusIndicator.setBackground(Color.YELLOW);
        controlPanel.add(statusIndicator,c);

        btnSwap = new JButton("Swap");
        c.gridx = 1; c.gridy = 0;
        controlPanel.add(btnSwap, c);

        btnReconnect = new JButton("Reconnect");
        c.gridx = 2; c.gridy = 0;
        controlPanel.add(btnReconnect, c);
        btnReconnect.setEnabled(false);

        status = new JTextPane();
        // Center the text
        StyledDocument doc = status.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        // Put it below the other elements
        c.gridx = 0; c.gridy = 1;
        c.gridwidth = 3;
        status.setText("Connecting to broker...");
        status.setOpaque(false);
        status.setPreferredSize(new Dimension(10, 25));
        controlPanel.add(status, c);

        btnSwap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		mainView.getUndoMoves().clear();
            		mainView.getRedoMoves().clear();
            		mainView.getUndoButton().setEnabled(false);
            		mainView.getRedoButton().setEnabled(false);
                SwapController swapController = new SwapController(mainView, gameState);
                swapController.requestSwap();
            }
        });
        btnReconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BrokerConnectionController.getConnection(mainView, gameState);
                    enable();
                } catch (ConnectionException e1) {
                    // Reconnect failed. Not bad, but should have some user feedback
                }
            }
        });
    }

    private void createInputPanel(JPanel inputPanel) {
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,5,0,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        inputElements = new ArrayList<ArrayList<JComponent>>();
        for(int i = 0; i < 10; i+=2) {
            ArrayList<JComponent> elements = new ArrayList<JComponent>();
            inputElements.add(elements);
            c.gridy = i;

            JCheckBox check = new JCheckBox();
            c.gridx = 0;
            check.setSelected(true);
            inputPanel.add(check, c);
            elements.add(check);

            JTextPane give = new JTextPane();
            c.gridx = 1;
            give.setText("give:");
            give.setOpaque(false);
            inputPanel.add(give, c);

            JTextField giveWord = new JTextField();
            c.gridx = 2;
            giveWord.setPreferredSize(new Dimension(80, 25));
            giveWord.setMinimumSize(new Dimension(80, 25));
            inputPanel.add(giveWord, c);
            elements.add(giveWord);

            JComboBox<WordType> giveTypes = new JComboBox<WordType>(WordType.values());
            Font oldFont = giveTypes.getFont();
            Font comboBoxFont = new Font(oldFont.getFontName(), oldFont.getStyle(), oldFont.getSize() - 2);
            giveTypes.setFont(comboBoxFont);
            c.gridx = 3;
            inputPanel.add(giveTypes, c);
            elements.add(giveTypes);

            c.gridy = i + 1;

            JTextPane get = new JTextPane();
            c.gridx = 1;
            get.setText("get:");
            get.setOpaque(false);
            inputPanel.add(get, c);

            JTextField getWord = new JTextField();
            c.gridx = 2;
            getWord.setPreferredSize(new Dimension(80, 25));
            getWord.setMinimumSize(new Dimension(80, 25));
            inputPanel.add(getWord, c);
            elements.add(getWord);

            JComboBox<WordType> getTypes = new JComboBox<WordType>(WordType.values());
            getTypes.setFont(comboBoxFont);
            c.gridx = 3;
            inputPanel.add(getTypes, c);
            elements.add(getTypes);
        }
    }

    /**
     * Disables the swap area (usually because we were unable to connect to the broker)
     */
    public void disable() {
        btnSwap.setEnabled(false);
        btnReconnect.setEnabled(true);
        statusIndicator.setBackground(Color.RED);
        status.setText("Unable to connect to broker");
    }

    /**
     * Enables the swap area (usually because we are now able to connect to the broker)
     */
    public void enable() {
        btnSwap.setEnabled(true);
        btnReconnect.setEnabled(false);
        statusIndicator.setBackground(Color.GREEN);
        status.setText("Connected to broker, ready to swap");
    }

    /**
     * Updates the view to reflect a pending swap
     */
    public void swapPending() {
        btnSwap.setEnabled(false);
        statusIndicator.setBackground(Color.YELLOW);
        status.setText("Swap Pending");
    }

    /**
     * Updates the view to reflect a successful swap
     */
    public void swapSuccessful() {
        btnSwap.setEnabled(true);
        statusIndicator.setBackground(Color.GREEN);
        status.setText("Swap Successful");
    }

    /**
     * Updates the view to reflect a failed swap
     */
    public void swapFailed() {
        btnSwap.setEnabled(true);
        statusIndicator.setBackground(Color.GREEN);
        status.setText("Swap Rejected");
    }

    /**
     * Updates the view to reflect that the swap a user requested is not valid
     */
    public void swapInvalid() {
        btnSwap.setEnabled(true);
        statusIndicator.setBackground(Color.GREEN);
        status.setText("You can't trade words you don't have!");
    }

    /**
     * Gets the panel that this view is displaying
     * @return The panel
     */
    public JPanel getPanel() {
        return swapPanel;
    }

    public ArrayList<ArrayList<JComponent>> getInputElements() { return inputElements; }
}
