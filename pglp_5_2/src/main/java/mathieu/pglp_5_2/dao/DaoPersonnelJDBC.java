package mathieu.pglp_5_2.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import mathieu.pglp_5_2.personnel.Personnel;

/**
 * DAO pour la classe Personnel.
 */
public class DaoPersonnelJDBC extends AbstractDao<Personnel> {
    /**
     * constructeur de la classe.
     * @param connect se connecter
     */
    public DaoPersonnelJDBC(final Connection connect) {
        super(connect);
    }
    /**
     * crée un élément dans le dao.
     * @param object element à ajouter
     */
    @SuppressWarnings("deprecation")
    @Override
    public Personnel create(final Personnel object) {
        final int un = 1, deux = 2, trois = 3, quatre = 4, annee = 1900;
        try {
            PreparedStatement prepare = connect.prepareStatement(
            "INSERT INTO personnel"
            + " (id,nom,prenom,dateNaissance,numeroTelephone)"
            + " VALUES(?, ?, ?, ?, ?)");
            prepare.setInt(un, object.getId());
            prepare.setString(deux, object.getNom());
            Date date = new Date(object.getDateNaissance().getYear() - annee,
                    object.getDateNaissance().getMonthValue() - un,
                    object.getDateNaissance().getDayOfMonth());
            prepare.setDate(trois, date);
            prepare.setArray(quatre, (Array) object.getNumeroTelephone());
            int result = prepare.executeUpdate();
            assert result == un;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }
    /**
     * cherche un element dans le dao.
     * @param id identifiant de l'objet a chercher
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Personnel find(final int id) {
        final int un = 1, annee = 1900;
        Personnel p = null;
        LocalDate dateNaissance;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM personnel WHERE id = ?");
            prepare.setInt(un, id);
            ResultSet result = prepare.executeQuery();
            if (result.first()) {
                dateNaissance = LocalDate.of(
                        result.getDate("dateNaissance").getYear() + annee,
                        result.getDate("dateNaissance").getMonth() + un,
                        result.getDate("dateNaissance").getDay());
                result.getString("nom");
                p = new Personnel.Builder(
                        result.getString("nom"),
                        result.getString("nom"),
                        dateNaissance,
                        (ArrayList<String>) result.getArray("numeroTelephone")
                        ).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
    /**
     * modifier un personnel.
     * @param object données pour modifier
     */
    @Override
    public Personnel update(final Personnel object) {
        final int un = 1, deux = 2, trois = 3,
                quatre = 4, cinq = 5, annee = 1900;
        if (this.find(object.getId()) != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                "UPDATE personnel SET nom = ?, prenom = ?, dateNaissance = ?,"
                + " numeroTelephone = ? WHERE id = ?");
                prepare.setString(un, object.getNom());
                prepare.setString(deux, object.getPrenom());
                @SuppressWarnings("deprecation")
                Date date = new Date(
                        object.getDateNaissance().getYear() - annee,
                        object.getDateNaissance().getMonthValue() - 1,
                        object.getDateNaissance().getDayOfMonth());
                prepare.setDate(trois, date);
                prepare.setArray(quatre, (Array) object.getNumeroTelephone());
                prepare.setInt(cinq, object.getId());
                int result = prepare.executeUpdate();
                assert result == un;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    /**
     * supprime du dao.
     * @param object l'objet a supprimer
     */
    @Override
    public void delete(final Personnel object) {
        final int un = 1;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM personnel WHERE id = ?");
            prepare.setInt(un, object.getId());
            int result = prepare.executeUpdate();
            assert result == un;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
