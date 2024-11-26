package state;

import java.awt.GridBagConstraints;
import java.util.Stack;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class SelectTypeState extends BackNextTemplateState {

    private final JComboBox<String> categorieComboBox;
    private final JCheckBox revocableCB;
    private final JCheckBox sensCB;
    private final JCheckBox dureeLimiteeCB;
    private final JCheckBox multiOffreCB;

    public SelectTypeState(JFrame window, Stack<State> states) {
        super(window, states);

        String[] categories = {"Jouets", "Vetements", "Meubles"};

        this.categorieComboBox = new JComboBox<>(categories);
        this.revocableCB = new JCheckBox("Révocable");
        this.sensCB = new JCheckBox("Montante");
        this.dureeLimiteeCB = new JCheckBox("Durée limitée");
        this.multiOffreCB = new JCheckBox("Offres multiples");

        this.backButton.addActionListener(e -> {
            popState(this.states);
        });

        this.nextButton.addActionListener(e -> {
            pushState(this.states, new CreateSDVState(this.window, this.states, (String) this.categorieComboBox.getSelectedItem(),
                    this.revocableCB.isSelected(), this.sensCB.isSelected(), this.dureeLimiteeCB.isSelected(), this.multiOffreCB.isSelected()));
        });

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        this.content.add(this.categorieComboBox, c);

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        this.content.add(this.revocableCB, c);

        c.gridx = 1;
        c.gridy = 1;
        this.content.add(this.sensCB, c);

        c.gridx = 0;
        c.gridy = 2;
        this.content.add(this.dureeLimiteeCB, c);

        c.gridx = 1;
        c.gridy = 2;
        this.content.add(this.multiOffreCB, c);
    }
}
