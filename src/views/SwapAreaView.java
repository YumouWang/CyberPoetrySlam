package views;

import controllers.swap.BrokerConnectionController;
import controllers.swap.ConnectionException;
import controllers.swap.SwapController;
import models.GameState;
import models.Position;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A view for the swap area
 * @author Nathan
 * @version 11/30/2014
 */
public class SwapAreaView extends AbstractView {

    private JPanel swapPanel;

    private JButton btnSwap;
    private JButton btnReconnect;

    public SwapAreaView(Position position, int width, int height, final MainView mainView, final GameState gameState) {
        super(position, width, height);
        swapPanel = new JPanel();
        swapPanel.setBorder(new LineBorder(Color.BLACK));
        swapPanel.setBounds(716, 0, width, height);

        btnSwap = new JButton("SWAP");
        swapPanel.add(btnSwap);

        btnReconnect = new JButton("Reconnect");
        swapPanel.add(btnReconnect);

        btnSwap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwapController swapController = new SwapController(mainView, gameState);
                swapController.swap();
            }
        });
        btnReconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BrokerConnectionController.getConnection(mainView, gameState);
                    btnSwap.setEnabled(true);
                } catch(ConnectionException e1) {
                    // Reconnect failed. Not bad, but should have some user feedback
                }
            }
        });
    }

    /**
     * Disables the swap area (usually because we were unable to connect to the broker)
     */
    public void disable() {
        btnSwap.setEnabled(false);
        System.out.println("Swap area disabled");
    }

    public JPanel getPanel() {
        return swapPanel;
    }
}
