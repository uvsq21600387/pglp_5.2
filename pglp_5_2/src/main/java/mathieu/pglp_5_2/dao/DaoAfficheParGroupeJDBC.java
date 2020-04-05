package mathieu.pglp_5_2.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

import mathieu.pglp_5_2.personnel.AfficheParGroupe;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

/**
 * Dao pour la classe AfficheParGroupe.
 */
public class DaoAfficheParGroupeJDBC
extends AbstractDao<AfficheParGroupe> {
    /**
     * constructeur de la classe.
     * @param connect se connecter
     */
    public DaoAfficheParGroupeJDBC(final Connection connect) {
        super(connect);
    }
    /**
     * crée un élément dans le dao.
     * @param object element à ajouter
     */
    @Override
    public AfficheParGroupe create(final AfficheParGroupe object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO afficheParGroupe (id,composites)"
                    + " VALUES(?, ?)");
            prepare.setInt(1, object.getId());
            prepare.setArray(2, (Array) object.getList());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }
    /**
     * cherche un element dans le dao.
     * @param id identifiant de l'objet a chercher
     */
    @Override
    public AfficheParGroupe find(final int id) {
        AfficheParGroupe apg = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM afficheParGroupe WHERE id = ?");
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            if (result.first()) {
                apg = new AfficheParGroupe();
                apg.setId(id);
                @SuppressWarnings("unchecked")
                ArrayDeque<InterfacePersonnels> array =
                (ArrayDeque<InterfacePersonnels>) result.getArray("composites");
                for (InterfacePersonnels ip : array) {
                    apg.add(ip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apg;
    }
    /**
     * modifier un CompositePersonnels.
     * @param object données pour modifier
     */
    @Override
    public AfficheParGroupe update(final AfficheParGroupe object) {
        if (this.find(object.getId()) != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE afficheParGroupe"
                        + "SET composite = ? WHERE id = ?");
                prepare.setArray(1, (Array) object.getList());
                prepare.setInt(2, object.getId());
                int result = prepare.executeUpdate();
                assert result == 1;
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
    public void delete(final AfficheParGroupe object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM afficheParGroupe WHERE id = ?");
            prepare.setInt(1, object.getId());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
