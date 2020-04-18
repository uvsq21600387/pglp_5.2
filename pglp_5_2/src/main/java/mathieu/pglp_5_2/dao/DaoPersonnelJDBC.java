package mathieu.pglp_5_2.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import mathieu.pglp_5_2.dao.AbstractDaoFactory.DaoType;
import mathieu.pglp_5_2.personnel.CompositePersonnels;
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
     * insérer les valeurs de numéro de téléphone du personnel.
     * @param object le numero de téléphone à insérer
     * @param idPersonnel l'identifiant du personnel associé
     * @return le numéro de téléphone
     * @throws SQLException si la création échoue
     */
    private String createNumeroTelephone(final String object, final int idPersonnel)
            throws SQLException {
        final int un = 1, deux = 2;
        PreparedStatement prepare = connect.prepareStatement(
        "INSERT INTO numeroTelephone"
        + " (idPersonnel,numero)"
        + " VALUES(?, ?)");
        prepare.setInt(un, idPersonnel);
        prepare.setString(deux, object);
        int result = prepare.executeUpdate();
        assert result == un;
        return object;
    }
    /**
     * rechercher tous les numéro de téléphone d'un personnel.
     * @param idPersonnel l'id du personnel
     * @return un tableau avec les numéros de téléphone
     * @throws SQLException si la recherche échoue
     */
    private ArrayList<String> findNumeroTelephone(final int idPersonnel)
            throws SQLException {
        final int un = 1;
        ArrayList<String> numeroTelephone = new ArrayList<String> ();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT numero FROM numeroTelephone WHERE idPersonnel = ?");
        prepare.setInt(un, idPersonnel);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            numeroTelephone.add(result.getString("numero"));
        }
        return numeroTelephone;
    }
    /**
     * retire de la bdd tous les numéros de téléphones d'un personnel.
     * @param idPersonnel l'id du personnel
     * @throws SQLException si la suppression échoue
     */
    private void deleteNumeros(final int idPersonnel) throws SQLException {
        final int un = 1;
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM numeroTelephone WHERE idPersonnel = ?");
        prepare.setInt(un, idPersonnel);
        prepare.executeUpdate();
    }
    private ArrayList<CompositePersonnels> findComposantPersonnel(final int idPersonnel)
            throws SQLException {
        ArrayList<CompositePersonnels> array =
                new ArrayList<CompositePersonnels> ();
        DaoFactoryJDBC factorytmp = (DaoFactoryJDBC)
                AbstractDaoFactory.getFactory(DaoType.JDBC);
        DaoCompositePersonnelsJDBC daoComposite = (DaoCompositePersonnelsJDBC)
                factorytmp.getDaoCompositePersonnels();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT idComposite FROM composantPersonnel WHERE idPersonnel = ?");
        prepare.setInt(1, idPersonnel);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            CompositePersonnels finder = daoComposite.find(result.getInt("idComposite"));
            if(finder != null) array.add(finder);
        }
        return array;
    }
    /**
     * supprime les relations de compositions de ce Personnel
     * @param idPersonnel le Personnel en question
     * @throws SQLException échec de la recherche
     */
    private void deleteComposantPersonnel(final int idPersonnel) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantPersonnel WHERE idPersonnel = ?");
        prepare.setInt(1, idPersonnel);
        prepare.executeUpdate();
    }
    /**
     * crée un élément dans la bdd.
     * @param object element à ajouter
     */
    @SuppressWarnings("deprecation")
    @Override
    public Personnel create(final Personnel object) {
        final int un = 1, deux = 2, trois = 3, quatre = 4, annee = 1900;
        try {
            PreparedStatement prepare = connect.prepareStatement(
            "INSERT INTO personnel"
            + " (id,nom,prenom,dateNaissance)"
            + " VALUES(?, ?, ?, ?)");
            prepare.setInt(un, object.getId());
            prepare.setString(deux, object.getNom());
            prepare.setString(trois, object.getPrenom());
            Date date = new Date(object.getDateNaissance().getYear() - annee,
                    object.getDateNaissance().getMonthValue() - un,
                    object.getDateNaissance().getDayOfMonth());
            prepare.setDate(quatre, date);
            int result = prepare.executeUpdate();
            assert result == un;
            for (String num : object.getNumeroTelephone()) {
                createNumeroTelephone(num, object.getId());
            }
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
    @SuppressWarnings({ "deprecation" })
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
                        this.findNumeroTelephone(id)
                        ).build();
                p.setId(id);
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
                quatre = 4, annee = 1900;
        final Personnel before = this.find(object.getId());
        ArrayList<CompositePersonnels> composantBefore = null;
        try {
            composantBefore = findComposantPersonnel(object.getId());
        } catch (SQLException e1) {
            e1.printStackTrace();
            return before;
        }
        if (before != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                "UPDATE personnel SET nom = ?, prenom = ?, dateNaissance = ?,"
                + " WHERE id = ?");
                prepare.setString(un, object.getNom());
                prepare.setString(deux, object.getPrenom());
                @SuppressWarnings("deprecation")
                Date date = new Date(
                        object.getDateNaissance().getYear() - annee,
                        object.getDateNaissance().getMonthValue() - 1,
                        object.getDateNaissance().getDayOfMonth());
                prepare.setDate(trois, date);
                prepare.setInt(quatre, object.getId());
                int result = prepare.executeUpdate();
                assert result == un;
                this.deleteNumeros(object.getId());
                for (String num : object.getNumeroTelephone()) {
                    this.createNumeroTelephone(num, object.getId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.delete(object);
                this.create(before);
                DaoFactoryJDBC factorytmp = (DaoFactoryJDBC)
                        AbstractDaoFactory.getFactory(DaoType.JDBC);
                DaoCompositePersonnelsJDBC daoComposite = (DaoCompositePersonnelsJDBC)
                        factorytmp.getDaoCompositePersonnels();
                for (CompositePersonnels cp : composantBefore) {
                    try {
                        daoComposite.createComposantPersonnel(cp.getId(), object.getId());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
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
    public void delete(final Personnel object) {
        final int un = 1;
        try {
            this.deleteComposantPersonnel(object.getId());
            this.deleteNumeros(object.getId());
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
