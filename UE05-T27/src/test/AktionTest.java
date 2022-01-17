package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Aktion;
import model.Belegung;
import model.Reservierung;
import model.Stornierung;
import model.Student;
import model.Zeitraum;
import model.exceptions.StudentException;

class AktionTest {
	private String text;
	private Aktion aktion;
	private Zeitraum zeitraum;
	
	@BeforeEach
	void initTest() {
		zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
	}

	/**
	 * Hier wird die Ausgabe von der "printProtokollImZeitraum(Zeitraum zeitraum)" Methode mit dem in der Variable "text" 
	 * zugewiesenem Wert verglichen. Dabei testet diese Methode alle drei Aktionen (Reservierung, Belegung, Stornierung).
	 */
	@Test
	void testPrintProtokollImZeitraum() {
		// Zunächst wird die "printProtokollImZeitraum" Methode bei einer Reservierung getestet
		text = "\t\tReservierung R08154711 fuer 06.12.2021 von 09:15 bis 10:00 fuer 1 Person\n" +
                "\t\t\tdurch K12345679 am 01.12.2021 um 23:18" + System.lineSeparator();
		try {
			aktion = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
			        "R08154711", LocalDate.of(2021, 12, 6),
			        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
		} catch (StudentException e) {
			e.printStackTrace();
		}
		assertEquals(text, aktion.printProtokollImZeitraum(zeitraum));
		zeitraum = new Zeitraum(LocalDate.of(2021, 11, 10), LocalDate.of(2021, 11, 30));
		assertNotEquals(text, aktion.printProtokollImZeitraum(zeitraum));
		
		zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
		
		
		// Als nächstes wird die "printProtokollImZeitraum" Methode bei einer Belegung getestet
		text = "\t\tBelegung am 06.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711" + System.lineSeparator();
		try {
			Reservierung reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
			        "R08154711", LocalDate.of(2021, 12, 6),
			        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
			aktion = new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 21), LocalTime.of(9, 48));
		} catch (StudentException e) {
			e.printStackTrace();
		}
		assertNotEquals(text, aktion.printProtokollImZeitraum(zeitraum));
		zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 6));
		assertEquals(text, aktion.printProtokollImZeitraum(zeitraum));
		
		
		// Als letztes wird die "printProtokollImZeitraum" Methode bei einer Stornierung getestet
		text = "\t\tStornierung der Reservierung R08154711 am 05.12.2021 um 18:23" + System.lineSeparator();
		try {
			Reservierung reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
			        "R08154711", LocalDate.of(2021, 12, 6),
			        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
			aktion = new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23), reservierung);
		} catch (StudentException e) {
			e.printStackTrace();
		}
		assertEquals(text, aktion.printProtokollImZeitraum(zeitraum));
		zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
		assertNotEquals(text, aktion.printProtokollImZeitraum(zeitraum));
		
	}

}
