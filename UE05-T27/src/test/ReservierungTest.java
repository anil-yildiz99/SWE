package test;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Reservierung;
import model.Student;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

class ReservierungTest extends LeafPrintProtkollTest {

	@BeforeEach
	void initTest() throws ZeitraumException, StudentException {
		text = "\t\tReservierung R08154711 fuer 06.12.2021 von 09:15 bis 10:00 fuer 1 Person\n" +
				"\t\t\tdurch K12345679 am 01.12.2021 um 23:18\n";

		aktion = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
				"R08154711", LocalDate.of(2021, 12, 6),
				LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
	}
	
	@Test
	void testReservierung() {
		// Die folgenden zwei Asserts testen das Reservierungsdatum
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18),
		        "R08154711", null,
		        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679")));
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18),
		        "R08154711", LocalDate.of(2021, 11, 10),
		        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679")));
		
		// Die folgenden zwei Asserts testen den Zeitraum einer Reservierung
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18),
		        "R08154711", LocalDate.of(2021, 12, 6),
		        null, LocalTime.of(10, 0), 1, new Student("K12345679")));
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18),
		        "R08154711", LocalDate.of(2021, 12, 6),
		        LocalTime.of(9, 15), null, 1, new Student("K12345679")));
		
		// Als naechstes wird ueberprueft, ob eine ZeitraumException geworfen wird, wenn der Zeitpunkt "von" nach dem 
		// Zeitpunkt "bis" initialisiert wird
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18),
		        "R08154711", LocalDate.of(2021, 12, 6),
		        LocalTime.of(12, 30), LocalTime.of(10, 0), 1, new Student("K12345679")));
		
		// Dieselben asserts muessen auch fuer den zweiten Konstruktor erfolgen
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18), DateTimeFormatter.ofPattern("yyyy.MM.dd"),
		        "R08154711", null,
		        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679")));
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18), DateTimeFormatter.ofPattern("yyyy.MM.dd"),
		        "R08154711", LocalDate.of(2021, 11, 10),
		        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679")));
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18), DateTimeFormatter.ofPattern("yyyy.MM.dd"),
		        "R08154711", LocalDate.of(2021, 12, 6),
		        null, LocalTime.of(10, 0), 1, new Student("K12345679")));
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18), DateTimeFormatter.ofPattern("yyyy.MM.dd"),
		        "R08154711", LocalDate.of(2021, 12, 6),
		        LocalTime.of(9, 15), null, 1, new Student("K12345679")));
		assertThrows(ZeitraumException.class, () -> new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 18), DateTimeFormatter.ofPattern("yyyy.MM.dd"),
		        "R08154711", LocalDate.of(2021, 12, 6),
		        LocalTime.of(12, 30), LocalTime.of(10, 0), 1, new Student("K12345679")));
	}
	
	@Test
	void testSetVon() {
		assertThrows(ZeitraumException.class, () -> ((Reservierung) aktion).setVon(null));
		assertThrows(ZeitraumException.class, () -> ((Reservierung) aktion).setVon(LocalTime.of(12, 30)));
	}
	
	@Test
	void testSetBis() {
		assertThrows(ZeitraumException.class, () -> ((Reservierung) aktion).setBis(null));
		assertThrows(ZeitraumException.class, () -> ((Reservierung) aktion).setBis(LocalTime.of(8, 10)));
	}
	
	/**
	 * Hier wird grundsaetzlich der Setter "setPersonenAnzahl" getestet, jedoch
	 * ist ein Zugriff auf dessen Getter unabdingbar. 
	 */
	@Test
	void testPersonenAnzahl() {
		assertEquals(1, ((Reservierung) aktion).getPersonenAnzahl());
		((Reservierung) aktion).setPersonenAnzahl(0);
		assertEquals(1, ((Reservierung) aktion).getPersonenAnzahl());
		((Reservierung) aktion).setPersonenAnzahl(2);
		assertEquals(2, ((Reservierung) aktion).getPersonenAnzahl());
	}

}
