package mathieu.pglp_5_2.dao;

import java.sql.Connection;
/**
 * pattern de la fabrique.
 */
public abstract class AbstractDaoFactory {
    /**
     * type de fabrique possible.
     */
    public enum DaoType {
        /**
         * crud version.
         */
        CRUD,
        /**
         * jdbc version.
         */
        JDBC;
    }
    /**
     * connection à la bdd pour JDBC.
     */
    private static Connection connect;
    /**
     * récupérer une fabrique.
     * @param t type de fabrique souhaité
     * @return la fabrique, null sinon
     */
    public static Object getFactory(final DaoType t) {
        switch (t) {
        case CRUD :
            return new DaoFactory();
        case JDBC :
            if (connect != null) {
                return new DaoFactoryJDBC(connect);
            }
        default :
            return null;
        }
    }
}
