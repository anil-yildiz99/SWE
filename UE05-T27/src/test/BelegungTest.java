package test;



import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Belegung;
import model.Reservierung;
import model.Student;
import model.exceptions.AktionException;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

class BelegungTest extends LeafPrintProtkollTest {
	private Reservierung reservierung;
	
	@BeforeEach
	void initTest() throws AktionException, ZeitraumException, StudentException {
		text1 = "\t\tBelegung am 06.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n";
		
		reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
				"R08154711", LocalDate.of(2021, 12, 6),
				LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
		aktion1 = new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 21), LocalTime.of(9, 48));
		
		text2 = "\t\tBelegung am 2021-12-06 von 09:21 bis 09:48 mit Reservierung R08154711\n";
		aktion2 = new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy-MM-dd"), reservierung, LocalTime.of(9, 21), LocalTime.of(9, 48));
	}
	
	@Test
	void testBelegung() {
		// Zunaechst wird getestet, ob bei einem nicht setzen einer Reservierung (also null) eine Exception geworfen wird
		assertThrows(AktionException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), null, LocalTime.of(9, 21), LocalTime.of(9, 48)));
		
		// Die folgenden zwei Asserts testen den Zeitraum einer Belegung
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, null, LocalTime.of(9, 48)));
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 21), null));
		
		// Als naechstes wird ueberprueft, ob eine ZeitraumException geworfen wird, wenn der Zeitpunkt "von" der Belegung
		// nach dem Zeitpunkt "bis" initialisiert wird.
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 50), LocalTime.of(9, 45)));
		
		// Als naechstes wird der Belegungszeitpunkt "von" vor dem Reservierungszeitpunkt "von" gesetzt
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 0), LocalTime.of(9, 45)));
		
		// Als naechstes wird der Belegungszeitpunkt "bis" nach dem Reservierungszeitpunkt "bis" gesetzt
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 21), LocalTime.of(10, 15)));
		
		// Dieselben asserts muessen auch fuer den zweiten Konstruktor erfolgen
		assertThrows(AktionException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy.MM.dd"), null, LocalTime.of(9, 21), LocalTime.of(9, 48)));
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy.MM.dd"), reservierung, null, LocalTime.of(9, 48)));
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy.MM.dd"), reservierung, LocalTime.of(9, 21), null));
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy.MM.dd"), reservierung, LocalTime.of(9, 50), LocalTime.of(9, 45)));
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy.MM.dd"), reservierung, LocalTime.of(9, 0), LocalTime.of(9, 45)));
		assertThrows(ZeitraumException.class, () -> new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), DateTimeFormatter.ofPattern("yyyy.MM.dd"), reservierung, LocalTime.of(9, 21), LocalTime.of(10, 15)));
	}
	
	@Test
	void testSetReservierung() {
		assertDoesNotThrow(() -> ((Belegung) aktion1).setReservierung(new Reservierung(LocalDate.of(2021, 11, 30), LocalTime.of(23, 10),
				"R08154711", LocalDate.of(2021, 12, 3),
				LocalTime.of(10, 15), LocalTime.of(14, 30), 2, new Student("K12345679"))));
		assertThrows(AktionException.class, () -> ((Belegung) aktion1).setReservierung(null));
	}
	
	/**
	 * Fuer eine 100%ige Coverage ist das Testen dieses Setters notwendig
	 */
	@Test
	void testGetReservierung() {
		assertNotNull(((Belegung) aktion1).getReservierung());
	}
	
	@Test
	void testSetVon() {
		// Zunaechst wird die ordnungsgemaesse Zuordnung eines Werts in die Variable "von" getestet
		assertDoesNotThrow(() -> ((Belegung) aktion1).setVon(LocalTime.of(9, 30)));
		assertThrows(ZeitraumException.class, () -> ((Belegung) aktion1).setVon(null));
		
		// Im folgenden assert wird der Belegungszeitpunkt "von" nach dem Belegungszeitpunkt "bis" gesetzt
		assertThrows(ZeitraumException.class, () -> ((Belegung) aktion1).setVon(LocalTime.of(9, 55)));
		
		// Als naechstes wird der Belegungszeitpunkt "von" vor dem Reservierungszeitpunkt "von" gesetzt
		assertThrows(ZeitraumException.class, () -> ((Belegung) aktion1).setVon(LocalTime.of(8, 30)));
	}
	
	@Test
	void testSetBis() {
		// Zunaechst wird die ordnungsgemaesse Zuordnung eines Werts in die Variable "von" getestet
		assertDoesNotThrow(() -> ((Belegung) aktion1).setBis(LocalTime.of(9, 50)));
		assertThrows(ZeitraumException.class, () -> ((Belegung) aktion1).setBis(null));
		
		// Im folgenden assert wird der Belegungszeitpunkt "bis" vor dem Belegungszeitpunkt "von" gesetzt
		assertThrows(ZeitraumException.class, () -> ((Belegung) aktion1).setBis(LocalTime.of(9, 18)));
		
		// Als naechstes wird der Belegungszeitpunkt "bis" nach dem Reservierungszeitpunkt "bis" gesetzt
		assertThrows(ZeitraumException.class, () -> ((Belegung) aktion1).setBis(LocalTime.of(10, 30)));
	}

}
