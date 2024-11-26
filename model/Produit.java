package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Produit {

    private int idProduit;
    private String nomProduit;
    private float prixRevient;
    private int qteProposee;
    private String nomCat;
    private String email;

    public Produit(String nomProduit, float prixRevient, int qteProposee, String nomCat, String email) {
        this.init(0, nomProduit, prixRevient, qteProposee, nomCat, email);
    }

    public Produit(int idProduit, String nomProduit, float prixRevient, int qteProposee, String nomCat, String email) {
        this.init(idProduit, nomProduit, prixRevient, qteProposee, nomCat, email);
    }

    private void init(int idProduit, String nomProduit, float prixRevient, int qteProposee, String nomCat, String email) {
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.prixRevient = prixRevient;
        this.qteProposee = qteProposee;
        this.nomCat = nomCat;
        this.email = email;
    }

    public int getIdProduit() {
        return this.idProduit;
    }

    public String getNomProduit() {
        return this.nomProduit;
    }

    public float getPrixRevient() {
        return this.prixRevient;
    }

    public int getQteProposee() {
        return this.qteProposee;
    }

    public String getNomCat() {
        return this.nomCat;
    }

    public String getEmail() {
        return this.email;
    }

    public void insert(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(idProduit) as maxId FROM Produits");

            int id = 1;
            if (res.next()) {
                id = res.getInt("maxId") + 1;
            }

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Produits (idProduit, nomProduit, prixRevient, qteProposee, nomCat, email) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, id);
            pstmt.setString(2, this.nomProduit);
            pstmt.setFloat(3, this.prixRevient);
            pstmt.setInt(4, this.qteProposee);
            pstmt.setString(5, this.nomCat);
            pstmt.setString(6, this.email);
            pstmt.executeUpdate();

            this.idProduit = id;

        } catch (SQLException e) {
            System.out.println("Erreur pendant l'insertion d'un nouveau produit --> ROLLBACK");

            try {
                connection.rollback();
            } catch (SQLException f) {
                System.out.println("Erreur pendant le rollback");
            }
        } finally {
            System.out.println("Insertion d'un nouveau produit --> COMMIT");

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erreur pendant le commit");
            }
        }
    }
}
