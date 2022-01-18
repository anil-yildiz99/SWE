package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Aktion;


/**
 * Diese Testklasse dient dazu, um die Ausgabe der Methode "printProtokoll()" von den konkreten 
 * Aktionen (Reservierung, Belegung, Sotrnierung) mit der Variable "text" zu vergleichen. 
 * @author gruppe27
 *
 */
abstract class LeafPrintProtkollTest {
	protected String text;
	protected Aktion aktion;
	
	@Test
	void testPrintProtokoll() {
		assertEquals(text, aktion.printProtokoll());
	}
}
