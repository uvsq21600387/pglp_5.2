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
     */
    public DaoPersonnelJDBC(Connection connect) {
        super(connect);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public Personnel create(final Personnel object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
            "INSERT INTO personnel (id,nom,prenom,dateNaissance,numeroTelephone)"
            + " VALUES(?, ?, ?, ?, ?)");
            prepare.setInt(1, object.getId());
            prepare.setString(2, object.getNom());
            Date date = new Date(object.getDateNaissance().getYear() - 1900,
                    object.getDateNaissance().getMonthValue() - 1,
                    object.getDateNaissance().getDayOfMonth());
            prepare.setDate(3, date);
            prepare.setArray(4, (Array) object.getNumeroTelephone());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }
    
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Personnel find(final int id) {
        Personnel p = null;
        LocalDate dateNaissance;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM personnel WHERE id = ?");
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            if (result.first()) {
                dateNaissance = LocalDate.of(
                        result.getDate("dateNaissance").getYear() + 1900,
                        result.getDate("dateNaissance").getMonth() + 1,
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
        if(this.find(object.getId()) != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE personnel SET nom = ?, prenom = ?, dateNaissance = ?,"
                        + " numeroTelephone = ? WHERE id = ?");
                prepare.setString(1, object.getNom());
                prepare.setString(2, object.getPrenom());
                @SuppressWarnings("deprecation")
                Date date = new Date(object.getDateNaissance().getYear() - 1900,
                        object.getDateNaissance().getMonthValue() - 1,
                        object.getDateNaissance().getDayOfMonth());
                prepare.setDate(3, date);
                prepare.setArray(4, (Array) object.getNumeroTelephone());
                prepare.setInt(5, object.getId());
                int result = prepare.executeUpdate();
                assert result == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }     
        }
        return object;
    }

    @Override
    public void delete(final Personnel object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM personnel WHERE id = ?");
            prepare.setInt(1, object.getId());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
