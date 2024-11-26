package state;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

abstract public class BackNextTemplateState extends State {

    protected final JPanel content;

    protected final JButton backButton;
    protected final JButton nextButton;

    public BackNextTemplateState(JFrame window, Stack<State> states) {
        super(window, states);

        this.content = new JPanel(new GridBagLayout());

        this.backButton = new JButton("Retour");

        this.nextButton = new JButton("Suivant");

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        this.container.add(content, c);

        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.PAGE_END;

        c.gridx = 0;
        c.gridy = 1;
        this.container.add(this.backButton, c);

        c.gridx = 1;
        c.gridy = 1;
        this.container.add(this.nextButton, c);
    }
}
