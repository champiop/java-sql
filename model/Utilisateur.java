package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utilisateur {

    private String email;
    private String nom;
    private String prenom;
    private String adresse;

    public Utilisateur(String email, String nom, String prenom, String adresse) {
        this.init(email, nom, prenom, adresse);
    }

    private void init(String email, String nom, String prenom, String adresse) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void insert(Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Utilisateurs (email, nom, prenom, adresse) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, this.email);
            pstmt.setString(2, this.nom);
            pstmt.setString(3, this.prenom);
            pstmt.setString(4, this.adresse);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur pendant l'insertion d'un nouvel utilisateur --> ROLLBACK");

            try {
                connection.rollback();
            } catch (SQLException f) {
                System.out.println("Erreur pendant le rollback");
            }
        } finally {
            System.out.println("Insertion d'un nouvel utilisateur --> COMMIT");

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erreur pendant le commit");
            }
        }
    }
}
