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
        BddCreation.initTableNumeroTelephone(connect);
        BddCreation.initTablePersonnel(connect);
        initTableCompositePersonnels(connect);
        initTableRelationCC(connect);
        initTableRelationCP(connect);
    }
    
    public void createDataBase() throws SQLException {
        DriverManager.getConnection("jdbc:derby:compositePattern;create=true");
    }
    
    private static void delTables(Connection connect) throws SQLException {
        Statement stat = connect.createStatement();
        stat.execute("drop table if exists numeroTelephone, personnel, composantComposite, composantPersonnel, compositePersonnels");
    }
    
    private static void initTablePersonnel(Connection connect) {
        
    }
    
    private static void initTableNumeroTelephone(Connection connect) {
        
    }
    
    private static void initTableCompositePersonnels(Connection connect) {
        
    }
    
    private static void initTableRelationCC(Connection connect) {
        
    }
    
    private static void initTableRelationCP(Connection connect) {
        
    }
}
