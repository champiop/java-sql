package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Offre {

    private int idOffre;
    private float prixOffre;
    private Date dateOffre;
    private int qteOffre;

    public Offre(float prixOffre, Date dateOffre, int qteOffre) {
        this.init(0, prixOffre, dateOffre, qteOffre);
    }

    public Offre(int idOffre, float prixOffre, Date dateOffre, int qteOffre) {
        this.init(idOffre, prixOffre, dateOffre, qteOffre);
    }

    private void init(int idOffre, float prixOffre, Date dateOffre, int qteOffre) {
        this.idOffre = idOffre;
        this.prixOffre = prixOffre;
        this.dateOffre = dateOffre;
        this.qteOffre = qteOffre;

    }

    public int getIdOffre() {
        return this.idOffre;
    }

    public float getPrixOffre() {
        return this.prixOffre;
    }

    public Date getDateOffre() {
        return this.dateOffre;
    }

    public int getQteOffre() {
        return this.qteOffre;
    }

    public void insert(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(idOffre) as maxId FROM Offres");

            int id = 1;
            if (res.next()) {
                id = res.getInt("maxId") + 1;
            }

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Offres (idOffre, prixOffre, dateOffre, qteOffre) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, id);
            pstmt.setFloat(2, this.prixOffre);
            pstmt.setDate(3, this.dateOffre);
            pstmt.setInt(4, this.qteOffre);
            pstmt.executeUpdate();

            this.idOffre = id;

        } catch (SQLException e) {
            System.out.println("Erreur pendant l'insertion d'une nouvelle offre --> ROLLBACK");

            try {
                connection.rollback();
            } catch (SQLException f) {
                System.out.println("Erreur pendant le rollback");
            }
        } finally {
            System.out.println("Insertion d'une nouvelle offre --> COMMIT");

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erreur pendant le commit");
            }
        }
    }
}
