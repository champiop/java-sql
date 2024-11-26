package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vente {

    private int idVente;
    private float prixDepart;
    private boolean revocable;
    private float decrementation;
    private Date dateFin;
    private boolean multiOffre;
    private int idProduit;
    private int idSDV;

    public Vente(float prixDepart, boolean revocable, float decrementation, Date dateFin, boolean multiOffre, int idProduit, int idSDV) {
        this.init(0, prixDepart, revocable, decrementation, dateFin, multiOffre, idProduit, idSDV);
    }

    public Vente(int idVente, float prixDepart, boolean revocable, float decrementation, Date dateFin, boolean multiOffre, int idProduit, int idSDV) {
        this.init(idVente, prixDepart, revocable, decrementation, dateFin, multiOffre, idProduit, idSDV);
    }

    private void init(int idVente, float prixDepart, boolean revocable, float decrementation, Date dateFin, boolean multiOffre, int idProduit, int idSDV) {
        this.idVente = idVente;
        this.prixDepart = prixDepart;
        this.revocable = revocable;
        this.decrementation = decrementation;
        this.dateFin = dateFin;
        this.multiOffre = multiOffre;
        this.idProduit = idProduit;
        this.idSDV = idSDV;
    }

    public int getIdVente() {
        return this.idVente;
    }

    public float getPrixDepart() {
        return this.prixDepart;
    }

    public boolean isRevocable() {
        return this.revocable;
    }

    public float getDecrementation() {
        return this.decrementation;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public boolean isMultiOffre() {
        return this.multiOffre;
    }

    public int getIdProduit() {
        return this.idProduit;
    }

    public int getIdSDV() {
        return this.idSDV;
    }

    public void insert(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(idVente) as maxId FROM Ventes");

            int id = 1;
            if (res.next()) {
                id = res.getInt("maxId") + 1;
            }

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Ventes (idVente, prixDepart, revocable, idProduit, idSDV) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, id);
            pstmt.setFloat(2, this.prixDepart);
            pstmt.setInt(3, this.revocable ? 1 : 0);
            pstmt.setInt(4, this.idProduit);
            pstmt.setInt(5, this.idSDV);
            pstmt.executeUpdate();

            if (this.decrementation > 0) {
                pstmt = connection.prepareStatement("INSERT INTO VentesDescendantes (idVente, decrementation) VALUES (?, ?)");
                pstmt.setInt(1, id);
                pstmt.setFloat(2, this.decrementation);
                pstmt.executeUpdate();
            } else {
                pstmt = connection.prepareStatement("INSERT INTO VentesMontantes (idVente) VALUES (?)");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            if (this.dateFin != null) {
                pstmt = connection.prepareStatement("INSERT INTO VentesDureeLimitee (idVente, dateFin) VALUES (?, ?)");
                pstmt.setInt(1, id);
                pstmt.setDate(2, this.dateFin);
                pstmt.executeUpdate();
            } else {
                pstmt = connection.prepareStatement("INSERT INTO VentesDureeLibre (idVente) VALUES (?)");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            if (this.multiOffre) {
                pstmt = connection.prepareStatement("INSERT INTO VentesOffresMultiples (idVente) VALUES (?)");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            } else {
                pstmt = connection.prepareStatement("INSERT INTO VentesOffresUniques (idVente) VALUES (?)");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            this.idVente = id;

        } catch (SQLException e) {
            System.out.println("Erreur pendant l'insertion d'une vente --> ROLLBACK");

            try {
                connection.rollback();
            } catch (SQLException f) {
                System.out.println("Erreur pendant le rollback");
            }
        } finally {
            System.out.println("Insertion d'une nouvelle vente --> COMMIT");

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erreur pendant le commit");
            }
        }
    }
}
