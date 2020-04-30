package mathieu.pglp_5_2.dao;

import java.sql.Connection;

import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.Personnel;

/**
 * pattern factory.
 */
public class DaoFactoryJDBC extends AbstractDaoFactory {
    /**
     * Connection à la bdd.
     */
    private static Connection connect;
    /**
     * Les classes utilitaires ne doivent pas
     * avoir de constructeur par défaut ou public.
     * @param connection se connecter
     */
    public DaoFactoryJDBC(final Connection connection) {
        connect = connection;
    }
    /**
     * fabrique Dao pour Personnel.
     * @return un Dao pour la classe Personnel
     */
    public static AbstractDao<Personnel> getDaoPersonnel() {
        return new DaoPersonnelJDBC(connect);
    }
    /**
     * fabrique Dao pour CompositePersonnel.
     * @return un Dao pour la classe CompositePersonnels
     */
    public static AbstractDao<CompositePersonnels>
    getDaoCompositePersonnels() {
        return new DaoCompositePersonnelsJDBC(connect);
    }
}
