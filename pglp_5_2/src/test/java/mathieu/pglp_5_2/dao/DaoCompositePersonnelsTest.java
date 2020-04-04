package mathieu.pglp_5_2.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

public class DaoCompositePersonnelsTest {

    @Test
    public void testAddGet() {
        DaoCompositePersonnels dcp = new DaoCompositePersonnels();
        CompositePersonnels cp = new CompositePersonnels();
        dcp.add(cp);
        assertTrue(dcp.getAll().size() == 1 && dcp.get(cp.getId()) == cp);
    }

    @Test
    public void testRemove() {
        DaoCompositePersonnels dcp = new DaoCompositePersonnels();
        CompositePersonnels cp = new CompositePersonnels();
        dcp.add(cp);
        dcp.remove(cp);
        assertTrue(dcp.getAll().isEmpty());
    }
    
    @Test
    public void testgetNull() {
        DaoCompositePersonnels dcp = new DaoCompositePersonnels();
        assertNull(dcp.get(0));
    }
    
    @Test
    public void testUpdate() {
        DaoCompositePersonnels dcp = new DaoCompositePersonnels();
        CompositePersonnels cp = new CompositePersonnels();
        CompositePersonnels cp2 = new CompositePersonnels();
        CompositePersonnels cp3 = new CompositePersonnels();
        cp.add(cp2);
        cp.add(cp3);
        dcp.add(cp);
        Map<String, Object> params = new HashMap<String, Object> ();
        params.put("personnels", new ArrayList<InterfacePersonnels> ());
        dcp.update(cp, params);
        String s = "Id : " + cp.getId() + "\n";
        assertTrue(dcp.get(cp.getId()).toString().equals(s));
    }
    
    @Test
    public void testSerialize() {
        DaoCompositePersonnels dcp = new DaoCompositePersonnels();
        CompositePersonnels cp = new CompositePersonnels();
        dcp.add(cp);
        
        dcp.serialize("dcp.ser");
        DaoCompositePersonnels dcp2 = DaoCompositePersonnels.deserialize("dcp.ser");
        File f = new File("dcp.ser");
        f.delete();
        assertTrue(dcp.getAll().toString().equals(dcp2.getAll().toString()));
    }
    
    @Test
    public void testEchecDeserialize() {
        DaoCompositePersonnels c = DaoCompositePersonnels.deserialize("ccc");
        assertNull(c);
    }
}
