package mathieu.pglp_5_2.dao;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.Personnel;

public class DaoPersonnelJDBCTest {

    @Test (expected = NullPointerException.class)
    public void testCreate() {
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        DaoPersonnelJDBC dp = new DaoPersonnelJDBC(null);
        dp.create(p);
    }

    @Test
    public void testFind() {
        ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        DaoPersonnelJDBC dp = new DaoPersonnelJDBC(null);
        dp.create(p);
        assertEquals(dp.find(p.getId()), null);
    }
    
    @Test
    public void testUpdate() {
        
    }
    
    @Test
    public void testDelete() {
        
    }
}
