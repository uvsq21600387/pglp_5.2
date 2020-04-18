package mathieu.pglp_5_2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mathieu.pglp_5_2.dao.AbstractDaoFactory.DaoType;
import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;
import mathieu.pglp_5_2.personnel.Personnel;

/**
 * Dao pour la classe CompositePersonnels.
 */
public class DaoCompositePersonnelsJDBC
extends AbstractDao<CompositePersonnels> {
    /**
     * constructeur de la classe.
     * @param connect se connecter
     */
    public DaoCompositePersonnelsJDBC(final Connection connect) {
        super(connect);
    }
    /**
     * crée la relation de composition entre 2 CompositePersonnels.
     * @param idComposite le composé
     * @param idComposant le composant
     * @throws SQLException échec de la création
     */
    private void createComposantComposite(final int idComposite, final int idComposant) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "INSERT INTO composantComposite (idComposite,idComposant)"
                + " VALUES(?, ?)");
        prepare.setInt(1, idComposite);
        prepare.setInt(2, idComposant);
        int result = prepare.executeUpdate();
        assert result == 1;
    }
    /**
     * crée la relation de composition entre un CompositePersonnels et un Personnel.
     * @param idComposite le composé
     * @param idComposant le composant
     * @throws SQLException échec de la création
     */
    public void createComposantPersonnel(final int idComposite, final int idPersonnel) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "INSERT INTO composantPersonnel (idComposite,idPersonnel)"
                + " VALUES(?, ?)");
        prepare.setInt(1, idComposite);
        prepare.setInt(2, idPersonnel);
        int result = prepare.executeUpdate();
        assert result == 1;
    }
    /**
     * cherche les composants du composite de type CompositePersonnels.
     * @param idComposite le composite en question
     * @return une liste de ses composants 
     * @throws SQLException échec de la recherche
     */
    private ArrayList<InterfacePersonnels> findComposantComposite(final int idComposite) throws SQLException {
        ArrayList<InterfacePersonnels> array =
                new ArrayList<InterfacePersonnels> ();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT idComposant FROM composantComposite WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            CompositePersonnels finder = this.find(result.getInt("idComposant"));
            if(finder != null) array.add(finder);
        }
        return array;
    }
    /**
     * cherche les composants du composite de type Personnel.
     * @param idComposite le composite en question
     * @return une liste de ses composants 
     * @throws SQLException échec de la recherche
     */
    private ArrayList<InterfacePersonnels> findComposantPersonnel(final int idComposite) throws SQLException {
        ArrayList<InterfacePersonnels> array =
                new ArrayList<InterfacePersonnels> ();
        DaoFactoryJDBC factorytmp = (DaoFactoryJDBC) AbstractDaoFactory.getFactory(DaoType.JDBC);
        DaoPersonnelJDBC daoPersonnels = (DaoPersonnelJDBC) factorytmp.getDaoPersonnel();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT idPersonnel FROM composantPersonnel WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            Personnel finder = daoPersonnels.find(result.getInt("idPersonnel"));
            if(finder != null) array.add(finder);
        }
        return array;
    }
    /**
     * cherche les composants du composite.
     * @param idComposite le composite en question
     * @return une liste de ses composants
     * @throws SQLException échec de la recherche
     */
    private ArrayList<InterfacePersonnels> findComposant(final int idComposite) throws SQLException {
        ArrayList<InterfacePersonnels> arrayC =
                findComposantComposite(idComposite);
        ArrayList<InterfacePersonnels> arrayP =
                findComposantPersonnel(idComposite);
        for (InterfacePersonnels ip : arrayP) {
            arrayC.add(ip);
        }
        return arrayC;
    }
    /**
     * supprime la relation de composition d'un CompositePersonnel.
     * @param idComposite le composite composé d'éléments
     * @throws SQLException échec de suppression
     */
    private void deleteComposantComposite(final int idComposite) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantComposite WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        prepare.executeUpdate();
    }
    /**
     * supprime la relation de composition d'un Personnel.
     * @param idComposite le composite composé d'éléments
     * @throws SQLException échec de suppression
     */
    private void deleteComposantPersonnel(final int idComposite) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantPersonnel WHERE idComposite = ?");
        prepare.setInt(1, idComposite);
        prepare.executeUpdate();
    }
    /**
     * supprime la relation de composition.
     * @param idComposite le composite composé d'éléments
     * @throws SQLException échec de suppression
     */
    private void deleteComposant(final int idComposite) throws SQLException {
        deleteComposantPersonnel(idComposite);
        deleteComposantComposite(idComposite);
    }
    /**
     * crée un élément dans la bdd.
     * @param object element à ajouter
     */
    @Override
    public CompositePersonnels create(final CompositePersonnels object) {
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "INSERT INTO compositePersonnels (id)"
                    + " VALUES(?)");
            prepare.setInt(1, object.getId());
            for (InterfacePersonnels ip : object.getList()) {
                if (ip.getClass() == CompositePersonnels.class) {
                    createComposantComposite(object.getId(), ip.getId());
                } else {
                    createComposantPersonnel(object.getId(), ip.getId());
                }
            }
            int result = prepare.executeUpdate();
            assert result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            this.delete(object);
        }
        return object;
    }
    /**
     * cherche un element dans la bdd.
     * @param id identifiant de l'objet a chercher
     */
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
                ArrayList<InterfacePersonnels> array =
                        this.findComposant(id);
                for (InterfacePersonnels ip : array) {
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
        CompositePersonnels before = this.find(object.getId());
        if (before != null) {
            try {
                this.deleteComposant(object.getId());
                for (InterfacePersonnels ip : object.getList()) {
                    if (ip.getClass() == CompositePersonnels.class) {
                        createComposantComposite(object.getId(), ip.getId());
                    } else {
                        createComposantPersonnel(object.getId(), ip.getId());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.delete(object);
                this.create(before);
                return before;
            }
        }
        return object;
    }
    /**
     * supprime de la bdd.
     * @param object l'objet a supprimer
     */
    @Override
    public void delete(final CompositePersonnels object) {
        try {
            this.deleteComposant(object.getId());
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
