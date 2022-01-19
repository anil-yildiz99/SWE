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
	protected String text1;
	protected String text2;
	
	protected Aktion aktion1;
	protected Aktion aktion2;
	
	/**
	 * Folgendes wird hier ausgefuehrt: Zuerst wird die Methode "printProtokoll()" ohne einem
	 * DateTimeFormatter getestet (also aktion1 und text1) und danach mit einem DateTimeFormatter
	 * (also aktion2 und text2).
	 */
	@Test
	void testPrintProtokoll() {
		assertEquals(text1, aktion1.printProtokoll());
		assertEquals(text2, aktion2.printProtokoll());
	}
}
