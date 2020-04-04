package mathieu.pglp_5_2.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.AfficheParGroupe;
import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.Personnel;

public class DaoFactoryTest {

    @Test
    public void testDaoPersonnel() {
        Dao<Personnel> dao = DaoFactory.getDaoPersonnel(null);
        assertTrue(dao.getAll().isEmpty());
    }

    @Test
    public void testDaoCompositePersonnels() {
        Dao<CompositePersonnels> dao = DaoFactory.getDaoCompositePersonnels(null);
        assertTrue(dao.getAll().isEmpty());
    }
    
    @Test
    public void testDaoAfficheParGroupe() {
        Dao<AfficheParGroupe> dao = DaoFactory.getDaoAfficheParGroupe(null);
        assertTrue(dao.getAll().isEmpty());
    }
    
    @Test
    public void testDaoPersonnelDeserialize() {
        DaoPersonnel dp = (DaoPersonnel) DaoFactory.getDaoPersonnel(null);
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        dp.add(p);
        dp.serialize("dp.ser");
        DaoPersonnel dp2 = DaoPersonnel.deserialize("dp.ser");
        File f = new File("dp.ser");
        f.delete();
        assertTrue(dp2.get(p.getId()).getNom().equals("man") && dp2.get(p.getId()).getPrenom().equals("Iron"));
    }

    @Test
    public void testDaoCompositePersonnelsDeserialize() {
        DaoCompositePersonnels dcp = (DaoCompositePersonnels) DaoFactory.getDaoCompositePersonnels(null);
        CompositePersonnels cp = new CompositePersonnels();
        dcp.add(cp);
        dcp.serialize("dcp.ser");
        DaoCompositePersonnels dcp2 = (DaoCompositePersonnels) DaoFactory.getDaoCompositePersonnels("dcp.ser");
        File f = new File("dcp.ser");
        f.delete();
        assertTrue(dcp.getAll().toString().equals(dcp2.getAll().toString()));
    }
    
    @Test
    public void testDaoAfficheParGroupeDeserialize() {
        DaoAfficheParGroupe dapg = (DaoAfficheParGroupe) DaoFactory.getDaoAfficheParGroupe(null);
        AfficheParGroupe apg = new AfficheParGroupe();
        CompositePersonnels c = new CompositePersonnels();
        apg.parcoursLargeur(c);
        dapg.add(apg);
        
        dapg.serialize("dapg.ser");
        DaoAfficheParGroupe dapg2 = (DaoAfficheParGroupe) DaoFactory.getDaoAfficheParGroupe("dapg.ser");
        File f = new File("dapg.ser");
        f.delete();
        assertTrue(dapg.getAll().toString().equals(dapg2.getAll().toString()));
    }
    
    @Test
    public void testDaoPersonnelEchec() {
        assertNull(DaoFactory.getDaoPersonnel("file"));
    }
    
    @Test
    public void testDaoCompositePersonnelsEchec() {
        assertNull(DaoFactory.getDaoCompositePersonnels("file"));
    }
    
    @Test
    public void testDaoAfficheParGroupeEchec() {
        assertNull(DaoFactory.getDaoAfficheParGroupe("file"));
    }
}
