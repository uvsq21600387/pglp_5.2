package mathieu.pglp_5_2;

import java.time.LocalDate;
import java.util.ArrayList;

import mathieu.pglp_5_2.bdd.BddCreation;
import mathieu.pglp_5_2.dao.AbstractDaoFactory;
import mathieu.pglp_5_2.dao.AbstractDaoFactory.DaoType;
import mathieu.pglp_5_2.personnel.Personnel;
import mathieu.pglp_5_2.dao.DaoFactoryJDBC;
import mathieu.pglp_5_2.dao.DaoPersonnelJDBC;

public class MainClass {

    public static void main(String[] args) throws Exception {
        BddCreation.createDataBase();
        BddCreation.resetDataBase();
        DaoFactoryJDBC factory = (DaoFactoryJDBC) AbstractDaoFactory.getFactory(DaoType.JDBC);
        DaoPersonnelJDBC daop = (DaoPersonnelJDBC) factory.getDaoPersonnel();

        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        daop.create(p);
    }
}
