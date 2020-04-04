package mathieu.pglp_5_2.personnel;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.CompositePersonnels;
import mathieu.pglp_5_2.personnel.InterfacePersonnels;

/**
 * tests unitaires sur la classe CompositePersonnels.
 */
public class CompositePersonnelsTest {
	@Test
	/**
	 * teste le constructeur.
	 */
	public void testConstructeur() {
		CompositePersonnels cp = new CompositePersonnels();
		Iterator<InterfacePersonnels> ip = cp.iterator();
		assertFalse(ip.hasNext());
	}
	@Test
	/**
	 * teste l'ajout de personnel.
	 */
	public void testAjout() {
		CompositePersonnels cp = new CompositePersonnels();
		cp.add(new CompositePersonnels());
		Iterator<InterfacePersonnels> ip = cp.iterator();
		assertTrue(ip.hasNext());
	}
	@Test
	/**
	 * teste la suppression de personnel.
	 */
	public void testSuppression() {
		CompositePersonnels cp = new CompositePersonnels();
		CompositePersonnels cp2 = new CompositePersonnels();
		InterfacePersonnels it = cp2;
		cp.add(cp2);
		cp.remove(it);
		Iterator<InterfacePersonnels> ip = cp.iterator();
		assertFalse(ip.hasNext());
	}
	
	@Test
	public void testSerialization() {
	    CompositePersonnels cp = new CompositePersonnels();
        CompositePersonnels cp2 = new CompositePersonnels();
        cp.add(cp2);
        
        cp.serialize("cp.ser");
        CompositePersonnels cp3 = CompositePersonnels.deserialize("cp.ser");
        File f = new File("cp.ser");
        f.delete();
        assertTrue(cp.toString().equals(cp3.toString()));
	}

    @Test
    public void testEchecDeserialize() {
        CompositePersonnels c = CompositePersonnels.deserialize("ccc");
        assertNull(c);
    }
}
