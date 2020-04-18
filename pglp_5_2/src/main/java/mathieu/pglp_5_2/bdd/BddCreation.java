package mathieu.pglp_5_2.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BddCreation {
    /**
     * créer la base de donnée
     * @throws SQLException 
     */
    public static void resetDataBase() throws Exception {
        Connection connect = DriverManager.getConnection("jdbc:derby:compositePattern;create=false");
        BddCreation.delTables(connect);
        BddCreation.initTablePersonnel(connect);
        BddCreation.initTableNumeroTelephone(connect);
        initTableCompositePersonnels(connect);
        initTableRelationCC(connect);
        initTableRelationCP(connect);
    }
    
    public static void createDataBase() throws SQLException {
        DriverManager.getConnection("jdbc:derby:compositePattern;create=true");
    }
    
    private static void delTables(Connection connect) {
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
    
    private static void initTablePersonnel(Connection connect) throws SQLException {
        String table = "create table personnel ("
                + "id int primary key,"
                + "nom varchar(30),"
                + "prenom varchar(30),"
                + "dateNaissance date"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    
    private static void initTableNumeroTelephone(Connection connect) throws SQLException {
        String table = "create table numeroTelephone ("
                + "idPersonnel int,"
                + "numero varchar(30),"
                + "primary key (idPersonnel,numero),"
                + "foreign key (idPersonnel) references personnel (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    
    private static void initTableCompositePersonnels(Connection connect) throws SQLException {
        String table = "create table compositePersonnels ("
                + "id int primary key"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    
    private static void initTableRelationCC(Connection connect) throws SQLException {
        String table = "create table composantComposite ("
                + "idComposite int,"
                + "idComposant int,"
                + "primary key (idComposite, idComposant),"
                + "foreign key (idComposite) references compositePersonnels (id),"
                + "foreign key (idComposant) references compositePersonnels (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
    
    private static void initTableRelationCP(Connection connect) throws SQLException {
        String table = "create table composantPersonnel ("
                + "idComposite int,"
                + "idPersonnel int,"
                + "primary key (idComposite, idPersonnel),"
                + "foreign key (idComposite) references compositePersonnels (id),"
                + "foreign key (idPersonnel) references personnel (id)"
                + ")";
        Statement stat = connect.createStatement();
        stat.execute(table);
    }
}
