package mathieu.pglp_5_2.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.AfficheParGroupe;
import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

public class DaoAfficheParGroupeTest {

    @Test
    public void testAddGet() {
        DaoAfficheParGroupe dapg = new DaoAfficheParGroupe();
        AfficheParGroupe apg = new AfficheParGroupe();
        dapg.add(apg);
        assertTrue(dapg.getAll().size() == 1 && dapg.get(apg.getId()) == apg);
    }

    @Test
    public void testRemove() {
        DaoAfficheParGroupe dapg = new DaoAfficheParGroupe();
        AfficheParGroupe apg = new AfficheParGroupe();
        dapg.add(apg);
        dapg.remove(apg);
        assertTrue(dapg.getAll().isEmpty());
    }
    
    @Test
    public void testgetNull() {
        DaoAfficheParGroupe dapg = new DaoAfficheParGroupe();
        assertNull(dapg.get(0));
    }
    
    @Test
    public void testUpdate() {
        DaoAfficheParGroupe dapg = new DaoAfficheParGroupe();
        AfficheParGroupe apg = new AfficheParGroupe();
        CompositePersonnels c = new CompositePersonnels();
        CompositePersonnels c2 = new CompositePersonnels();
        apg.parcoursLargeur(c);
        dapg.add(apg);
        ArrayList<InterfacePersonnels> lapg = new ArrayList<InterfacePersonnels>();
        lapg.add(c);
        lapg.add(c2);
        Map<String, Object> params = new HashMap<String, Object> ();
        params.put("file", lapg);
        dapg.update(apg, params);
        boolean expected = true;
        Iterator<InterfacePersonnels> it = apg.iterator();
        for(InterfacePersonnels contain : lapg) {
            if(it.hasNext()) expected = expected && contain.toString().equals(it.next().toString());
            else expected = false;
        }
        assertTrue(expected);
    }
    
    @Test
    public void testSerialize() {
        DaoAfficheParGroupe dapg = new DaoAfficheParGroupe();
        AfficheParGroupe apg = new AfficheParGroupe();
        CompositePersonnels c = new CompositePersonnels();
        apg.parcoursLargeur(c);
        dapg.add(apg);
        
        dapg.serialize("dapg.ser");
        DaoAfficheParGroupe dapg2 = DaoAfficheParGroupe.deserialize("dapg.ser");
        File f = new File("dapg.ser");
        f.delete();
        assertTrue(dapg.getAll().toString().equals(dapg2.getAll().toString()));
    }
    
    @Test
    public void testEchecDeserialize() {
        DaoAfficheParGroupe c = DaoAfficheParGroupe.deserialize("ccc");
        assertNull(c);
    }
}
