package state;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateSDVState extends BackNextTemplateState {

    private String nomCat;
    private boolean revocable;
    private boolean sens;
    private boolean dureeLimitee;
    private boolean multiOffre;

    private List<String> selectedProduits;

    private final JComboBox<String> produitComboBox;
    private final JFormattedTextField prixDepartField;
    private final JFormattedTextField decrementationField;
    private final JFormattedTextField dateFinField;
    private final JButton addVenteButton;
    private final JButton createSDVButton;

    public CreateSDVState(JFrame window, Stack<State> states, String nomCat, boolean revocable, boolean sens, boolean dureeLimitee, boolean multiOffre) {
        super(window, states);

        this.nomCat = nomCat;
        this.revocable = revocable;
        this.sens = sens;
        this.dureeLimitee = dureeLimitee;
        this.multiOffre = multiOffre;

        this.selectedProduits = new LinkedList<>();

        String[] produits = {"Chose", "Objet", "Produit"};
        this.produitComboBox = new JComboBox<>(produits);

        NumberFormat formatPrix = NumberFormat.getNumberInstance();
        formatPrix.setMaximumFractionDigits(2);
        DateFormat formatDate = DateFormat.getDateInstance(DateFormat.SHORT);

        this.prixDepartField = new JFormattedTextField(formatPrix);
        this.prixDepartField.setColumns(8);
        this.decrementationField = new JFormattedTextField(formatPrix);
        this.decrementationField.setColumns(8);
        this.dateFinField = new JFormattedTextField(formatDate);
        this.dateFinField.setColumns(8);

        JLabel prixDepartLabel = new JLabel("Prix de départ: ");
        JLabel decrementationLabel = new JLabel("Décrémenation: ");
        JLabel dateFinLabel = new JLabel("Date de fin: ");

        prixDepartLabel.setLabelFor(this.prixDepartField);
        decrementationLabel.setLabelFor(this.decrementationField);
        dateFinLabel.setLabelFor(this.dateFinField);

        JPanel labelPanel = new JPanel(new GridLayout(0, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(0, 1));

        labelPanel.add(prixDepartLabel);
        fieldPanel.add(this.prixDepartField);

        if (!this.sens) {
            labelPanel.add(decrementationLabel);
            fieldPanel.add(decrementationField);
        }

        if (this.dureeLimitee) {
            labelPanel.add(dateFinLabel);
            fieldPanel.add(dateFinField);
        }

        JPanel fieldGroupPanel = new JPanel(new BorderLayout());
        fieldGroupPanel.add(labelPanel, BorderLayout.CENTER);
        fieldGroupPanel.add(fieldPanel, BorderLayout.LINE_END);

        this.addVenteButton = new JButton("Ajouter le produit à la salle de vente");
        this.addVenteButton.addActionListener(e -> {
            String prod = (String) this.produitComboBox.getSelectedItem();
            String prixDepart = this.prixDepartField.getText();
            String decrementation = this.decrementationField.getText();
            String dateFin = this.dateFinField.getText();
            if (prod != null && !prixDepart.equals("") && (sens || !decrementation.equals("")) && (!dureeLimitee || !dateFin.equals(""))) {
                this.produitComboBox.removeItem(prod);
                this.selectedProduits.add(prod);
                System.out.println("Un produit a été ajouté à la salle de vente: " + prod);
                System.out.println("Prix de départ: " + prixDepart);
                if (!sens) {
                    System.out.println("Décrémenation: " + decrementation);
                }
                if (dureeLimitee) {
                    System.out.println("Date de fin: " + dateFin);
                }
            }
        });

        this.createSDVButton = new JButton("Créer la salle de vente");
        this.createSDVButton.addActionListener(e -> {
            System.out.println("Salle de vente créer avec des ventes de catégorie " + this.nomCat + " et de type (" + this.revocable + ", " + this.sens + ", " + this.dureeLimitee + ", " + this.multiOffre + ")");
        });

        this.backButton.addActionListener(e -> {
            popState(this.states);
        });

        this.nextButton.setEnabled(false);

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        this.content.add(this.produitComboBox, c);

        c.gridx = 0;
        c.gridy = 1;
        this.content.add(fieldGroupPanel, c);

        c.gridx = 0;
        c.gridy = 2;
        this.content.add(this.addVenteButton, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(100, 0, 0, 0);
        this.content.add(this.createSDVButton, c);
    }
}
