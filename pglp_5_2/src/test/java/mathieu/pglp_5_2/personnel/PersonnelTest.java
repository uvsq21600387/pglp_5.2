package mathieu.pglp_5_2.personnel;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import mathieu.pglp_5_2.personnel.Personnel;

/**
 * tests unitaires sur la classe Personnel.
 */
public class PersonnelTest {
	@Test
	/**
	 * teste le constructeur.
	 */
	public void test() {
		ArrayList<String> numero = new ArrayList<String>();
    	numero.add("06.18.12.15.95");
    	numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        assertTrue(p.getNom().equals("man") && p.getPrenom() == "Iron" &&
                p.getDateNaissance().equals(LocalDate.of(1955, 05, 02)) &&
                p.getNumeroTelephone().containsAll(numero));
	}

	@Test
	public void TestSerialize() {
	    ArrayList<String> numero = new ArrayList<String>();
        numero.add("06.18.12.15.95");
        numero.add("01.25.46.85.16");
        Personnel p = new Personnel.Builder("man", "Iron", LocalDate.of(1955, 05, 02), numero).build();
        
        p.serialize("person.ser");
        Personnel p2 = Personnel.deserialize("person.ser");
        File f = new File("person.ser");
        f.delete();
        assertTrue(p.toString().equals(p2.toString()));
	}
	
	@Test
    public void testEchecDeserialize() {
	    Personnel c = Personnel.deserialize("ccc");
        assertNull(c);
    }
}
