package mathieu.pglp_5_2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
     * récupérer une fabrique.
     * @param t type de fabrique souhaité
     * @return la fabrique, null sinon
     */
    public static Object getFactory(final DaoType t) {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection("jdbc:derby:compositePattern;create=false");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch (t) {
        case CRUD :
            return new DaoFactory();
        case JDBC :
            return new DaoFactoryJDBC(connect);
        default :
            return null;
        }
    }
}
