package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SDV {

    private int idSDV;

    public SDV() {
        this.init(0);
    }

    public SDV(int idSDV) {
        this.init(idSDV);
    }

    private void init(int idSDV) {
        this.idSDV = idSDV;
    }

    public int getIdSDV() {
        return this.idSDV;
    }

    public void insert(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(idSDV) as maxId FROM SDV");

            int id = 1;
            if (res.next()) {
                id = res.getInt("maxId") + 1;
            }

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO SDV (idSDV) VALUES (?)");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            this.idSDV = id;

        } catch (SQLException e) {
            System.out.println("Erreur pendant l'insertion d'une nouvelle salle de vente --> ROLLBACK");

            try {
                connection.rollback();
            } catch (SQLException f) {
                System.out.println("Erreur pendant le rollback");
            }
        } finally {
            System.out.println("Insertion d'une nouvelle salle de vente --> COMMIT");

            try {
                connection.commit();
            } catch (SQLException e) {
                System.out.println("Erreur pendant le commit");
            }
        }
    }
}
