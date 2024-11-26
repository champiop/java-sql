package state;

import java.awt.GridBagConstraints;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenuState extends State {

    private final JButton createSDVButton;
    private final JButton joinSDVButton;

    public MainMenuState(JFrame window, Stack<State> states) {
        super(window, states);

        this.createSDVButton = new JButton("Mettre en place une salle de vente");
        this.createSDVButton.addActionListener(e -> {
            pushState(states, new SelectTypeState(this.window, this.states));
        });

        this.joinSDVButton = new JButton("Rejoindre une salle de vente");
        this.joinSDVButton.addActionListener(e -> {
            // Nouveau State
        });

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.container.add(this.createSDVButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        this.container.add(this.joinSDVButton, c);
    }
}
