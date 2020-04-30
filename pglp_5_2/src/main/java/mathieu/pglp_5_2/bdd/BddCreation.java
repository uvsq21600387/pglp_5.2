package mathieu.pglp_5_2.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe pour créer la base de donnée.
 */
public abstract class BddCreation {
    /**
     * créer la base de donnée.
     * @throws Exception erreur de création
     */
    public static void resetDataBase() throws Exception {
        Connection connect = DriverManager.getConnection(
                "jdbc:derby:compositePattern;create=false");
        BddCreation.delTables(connect);
        BddCreation.initTablePersonnel(connect);
        BddCreation.initTableNumeroTelephone(connect);
        initTableCompositePersonnels(connect);
        initTableRelationCC(connect);
        initTableRelationCP(connect);
    }
    /**
     * créer la bdd.
     * @throws SQLException erreur de création
     */
    public static void createDataBase() throws SQLException {
        DriverManager.getConnection(
                "jdbc:derby:compositePattern;create=true");
    }
    /**
     * supprime les tables.
     * @param connect connection a la bdd
     */
    private static void delTables(final Connection connect) {
        Statement stat = null;
        try {
            stat = connect.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            stat.execute("drop table numeroTelephone");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table composantComposite");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table composantPersonnel");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table compositePersonnels");
        } catch (SQLException e) {
        }
        try {
            stat.execute("drop table personnel");
        } catch (SQLException e) {
        }
    }
    /**
     * créer la table personnel.
     * @param connect connexion a la bdd
     * @throws SQLException erreur sql
     */
    private static void initTablePersonnel(final Connection connect)
            throws SQLException {
        String table = "create table personnel ("
                + "id int primary key,"
                + "nom varchar(30),"
                + "prenom varchar(30),"
                + "dateNaissance date"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /**
     * créer la table numeroTelephone.
     * @param connect connexion a la bdd
     * @throws SQLException erreur sql
     */
    private static void initTableNumeroTelephone(final Connection connect)
            throws SQLException {
        String table = "create table numeroTelephone ("
                + "idPersonnel int,"
                + "numero varchar(30) primary key,"
                + "foreign key (idPersonnel) references personnel (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /**
     * créer la table CompositePersonnels.
     * @param connect connexion a la bdd
     * @throws SQLException erreur sql
     */
    private static void initTableCompositePersonnels(final Connection connect)
            throws SQLException {
        String table = "create table compositePersonnels ("
                + "id int primary key"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /**
     * créer la table de composition entre composite et composite.
     * @param connect connexion a la bdd
     * @throws SQLException erreur sql
     */
    private static void initTableRelationCC(final Connection connect)
            throws SQLException {
        String table = "create table composantComposite ("
                + "idComposite int,"
                + "idComposant int,"
                + "primary key (idComposite, idComposant),"
                + "foreign key (idComposite) references "
                + "compositePersonnels (id),"
                + "foreign key (idComposant) references "
                + "compositePersonnels (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    /**
     * créer la table de composition entre composite et personnel.
     * @param connect connexion a la bdd
     * @throws SQLException erreur sql
     */
    private static void initTableRelationCP(final Connection connect)
            throws SQLException {
        String table = "create table composantPersonnel ("
                + "idComposite int,"
                + "idPersonnel int,"
                + "primary key (idComposite, idPersonnel),"
                + "foreign key (idComposite) references "
                + "compositePersonnels (id),"
                + "foreign key (idPersonnel) references personnel (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
}
