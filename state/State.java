package state;

import java.awt.GridBagLayout;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class State {

    protected final JFrame window;
    protected final JPanel container;
    protected final Stack<State> states;

    public State(JFrame window, Stack<State> states) {
        this.window = window;
        this.states = states;
        this.container = new JPanel();
        this.container.setLayout(new GridBagLayout());
        this.container.setPreferredSize(this.window.getSize());
    }

    private void paint() {
        this.window.add(this.container);
    }

    private void dispose() {
        this.window.remove(this.container);
    }

    private void display() {
        this.window.revalidate();
        this.window.repaint();
        this.window.pack();
    }

    public static void pushState(Stack<State> states, State newState) {
        if (!states.empty()) {
            states.peek().dispose();
        }

        states.push(newState);
        newState.paint();
        newState.display();
    }

    public static void popState(Stack<State> states) {
        if (!states.empty()) {
            State topState = states.pop();
            topState.dispose();
        }

        if (!states.empty()) {
            State topState = states.peek();
            topState.paint();
            topState.display();
        }
    }
}
