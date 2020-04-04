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
        CRUD,
        JDBC; 
    }
    /**
     * connection à la bdd pour JDBC.
     */
    private static Connection connect = null;
    /**
     * récupérer une fabrique.
     * @param t type de fabrique souhaité
     * @return la fabrique, null sinon
     */
    public static Object getFactory(final DaoType t) {
        switch(t) {
        case CRUD :
            return new DaoFactory();
        case JDBC :
            if(connect != null) {
                return new DaoFactoryJDBC(connect);
            }
        default :
            return null;
        }
    }
    /**
     * se connecter a la bdd.
     * @param url la bdd
     * @param login le login de l'user
     * @param password le mot de passe de l'user
     * @throws SQLException problème de connection
     */
    public static void connectToBdd(String url, String login, String password) throws SQLException {
        connect = DriverManager.getConnection(url, login, password);
    }
}
