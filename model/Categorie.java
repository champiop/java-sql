package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Categorie {

    private String nomCat;
    private String descr;

    public Categorie(String nomCat, String descr) {
        this.init(nomCat, descr);
    }

    private void init(String nomCat, String descr) {
        this.nomCat = nomCat;
        this.descr = descr;
    }

    public String getNomCat() {
        return this.nomCat;
    }

    public String getDescr() {
        return this.descr;
    }

    public void insert(Connection connection) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Categories (nomCat, descr) VALUES (?, ?)");
            pstmt.setString(1, this.nomCat);
            pstmt.setString(2, this.descr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur pendant l'insertion d'une nouvelle catégorie --> ROLLBACK");

            try {
                connection.rollback();
            } catch (SQLException f) {
                System.out.println("Erreur pendant le rollback");
            }
        } finally {
            System.out.println("Insertion d'une nouvelle catégorie --> COMMIT");

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erreur pendant le commit");
            }
        }
    }
}
