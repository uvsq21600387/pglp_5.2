package mathieu.pglp_5_2.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

/**
 * Dao pour la classe CompositePersonnels.
 */
public class DaoCompositePersonnelsJDBC
extends AbstractDao<CompositePersonnels> {
    /**
     * constructeur de la classe.
     */
    public DaoCompositePersonnelsJDBC(Connection connect) {
        super(connect);
    }
    
    @Override
    public CompositePersonnels create(final CompositePersonnels object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO compositePersonnels (id,composites)"
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
    
    @Override
    public CompositePersonnels find(final int id) {
        CompositePersonnels cp = null;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM compositePersonnels WHERE id = ?");
            prepare.setInt(1, id);
            ResultSet result = prepare.executeQuery();
            if (result.first()) {
                cp = new CompositePersonnels();
                cp.setId(id);
                @SuppressWarnings("unchecked")
                ArrayList<InterfacePersonnels> array =
                (ArrayList<InterfacePersonnels>) result.getArray("composites");
                for(InterfacePersonnels ip : array) {
                    cp.add(ip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cp;
    }
    /**
     * modifier un CompositePersonnels.
     * @param object données pour modifier
     */
    @Override
    public CompositePersonnels update(final CompositePersonnels object) {
        if(this.find(object.getId()) != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                        "UPDATE compositePersonnels SET composite = ? WHERE id = ?");
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

    @Override
    public void delete(final CompositePersonnels object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM compositePersonnels WHERE id = ?");
            prepare.setInt(1, object.getId());
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
