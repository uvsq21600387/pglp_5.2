package mathieu.pglp_5_2.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.Personnel;

public class DaoPersonnelTest {

    @Test
    public void testAddGet() {
        DaoPersonnel dp = new DaoPersonnel();
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        dp.add(p);
        assertTrue(dp.getAll().size() == 1 && dp.getAll().get(0) == p);
    }

    @Test
    public void testRemove() {
        DaoPersonnel dp = new DaoPersonnel();
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        dp.add(p);
        dp.remove(p);
        assertTrue(dp.getAll().isEmpty());
    }
    
    @Test
    public void testgetNull() {
        DaoPersonnel dp = new DaoPersonnel();
        assertNull(dp.get(0));
    }
    
    @Test
    public void testUpdate() {
        DaoPersonnel dp = new DaoPersonnel();
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        dp.add(p);
        Map<String, Object> params = new HashMap<String, Object> ();
        params.put("nom", "Son");
        params.put("prenom", "Goku");
        dp.update(p, params);
        Personnel p2 = new Personnel.Builder("Son", "Goku", LocalDate.of(1955, 05, 02), numero).build();
        assertTrue(dp.getAll().get(0).toString().equalsIgnoreCase(p2.toString()));
    }
    
    @Test
    public void testSerialize() {
        DaoPersonnel dp = new DaoPersonnel();
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        dp.add(p);
        
        dp.serialize("dp.ser");
        DaoPersonnel dp2 = DaoPersonnel.deserialize("dp.ser");
        File f = new File("dp.ser");
        f.delete();
        assertTrue(dp.getAll().toString().equals(dp2.getAll().toString()));
    }
    
    @Test
    public void testEchecDeserialize() {
        DaoPersonnel c = DaoPersonnel.deserialize("ccc");
        assertNull(c);
    }

}
