package test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LernzoneTest {

	private Lernzone lernzone;
	private Reservierung res;
	private Reservierung res2;
	private Belegung bel;
	private Stornierung stor;

	@BeforeAll
	void initActions() throws StudentException {
		res = new Reservierung(
				LocalDate.of(2021, 12, 1),
				LocalTime.of(23, 18),
				"R08154711",
				LocalDate.of(2021, 12, 3),
				LocalTime.of(9, 15),
				LocalTime.of(10, 0),
				3,
				new Student("K12345678"));
		res2 = new Reservierung(
				LocalDate.of(2021, 12, 22),
				LocalTime.of(1, 1),
				"R87654321",
				LocalDate.of(2021, 12, 29),
				LocalTime.of(22, 15),
				LocalTime.of(23, 55),
				7,
				new Student("K12345678"));
		bel = new Belegung(res.getReservierungsDatum(), LocalTime.of(9, 21), res, LocalTime.of(9, 21), LocalTime.of(9, 48));
		stor = new Stornierung(LocalDate.of(2021, 12, 30), LocalTime.of(18, 23),res2);

	}

	@BeforeEach
	void initTest() {
		lernzone = new Lernzone("S3-EG-Z01");
	}

	@Test
	void setName() {
		lernzone.setName("S4-EG-Z01");
		assertNotEquals("S3-EG-Z01", lernzone.getName());
		assertEquals("S4-EG-Z01", lernzone.getName());
	}

	@Test
	void getName() {
		assertEquals("S3-EG-Z01", lernzone.getName());
		assertNotEquals("S4-EG-Z01", lernzone.getName());
	}

	@Test
	void printProtokoll() throws InvalidCompositeException {
		assertEquals("Lernzone: S3-EG-Z01\n", lernzone.printProtokoll());
		Lernplatz lp = new Lernplatz(1,5);
		lernzone.add(lp);
		assertEquals("Lernzone: S3-EG-Z01\n\tLernplatz 1 fuer 5 Personen\n", lernzone.printProtokoll());

		lernzone.add(new Lernplatz(2,1));
		assertEquals("Lernzone: S3-EG-Z01\n" +
								"\tLernplatz 1 fuer 5 Personen\n" +
								"\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());

		lp.add(res);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
					"\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n"+
						"\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n"+
				"\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());

		lp.add(bel);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n"+
				"\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n"+
				"\t\tBelegung am 03.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n"+
				"\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());

		lp.add(res2);
		lp.add(stor);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n" +
				"\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n" +
				"\t\tBelegung am 03.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n" +
				"\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n"+
				"\t\t\tdurch K12345678 am 22.12.2021 um 01:01\n"+
				"\t\tStornierung der Reservierung R87654321 am 30.12.2021 um 18:23\n" +
				"\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());
	}

	@Test
	void printProtokollImZeitraum() throws InvalidCompositeException {
		Zeitraum zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 29));
		assertEquals("Lernzone: S3-EG-Z01\n", lernzone.printProtokollImZeitraum(zeitraum));
		Lernplatz lp = new Lernplatz(1, 5);
		lernzone.add(lp);
		assertEquals("Lernzone: S3-EG-Z01\n\tLernplatz 1 fuer 5 Personen\n", lernzone.printProtokollImZeitraum(zeitraum));

		lp.add(res2);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n" +
				"\t\t\tdurch K12345678 am 22.12.2021 um 01:01\n", lernzone.printProtokollImZeitraum(zeitraum));

		lp.add(stor);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n" +
				"\t\t\tdurch K12345678 am 22.12.2021 um 01:01\n", lernzone.printProtokollImZeitraum(zeitraum));
	}

	@Test
	void add() {
		assertTrue(lernzone.getProtokoll().isEmpty());
		try {
			assertTrue(lernzone.add(new Lernplatz(1,5)));
		} catch (model.exceptions.InvalidCompositeException e) {
			fail(e.getMessage());
		}
		assertEquals(1, lernzone.getProtokoll().size());
	}

	@Test
	void invalidCompositionLernzoneInLernzone(){
		Exception exception = assertThrows(InvalidCompositeException.class, () ->
				lernzone.add(new Lernzone("S7-EG-Z01")));
		assertEquals("ungueltige Verschachtelung: Lernzone kann kein Teil von Lernzone sein", exception.getMessage());
		assertTrue(lernzone.getProtokoll().isEmpty());
	}
	@Test
	void invalidCompositionReservierungInLernzone(){
		Exception exception = assertThrows(InvalidCompositeException.class, () ->
				lernzone.add(new Reservierung(
									LocalDate.of(2021, 12, 1),
									LocalTime.of(23, 18),
									"R08154711",
									LocalDate.of(2021, 12, 3),
									LocalTime.of(9, 15),
									LocalTime.of(10, 0),
									3,
									new Student("K12345678"))));
		assertEquals("ungueltige Verschachtelung: Reservierung kann kein Teil von Lernzone sein", exception.getMessage());
		assertTrue(lernzone.getProtokoll().isEmpty());
	}
	@Test
	void invalidCompositionBelegungInLernzone(){
		Exception exception = assertThrows(InvalidCompositeException.class, () ->
				lernzone.add(new Belegung(
							res.getReservierungsDatum(),
							LocalTime.of(9, 21),
							res,
							LocalTime.of(9, 21),
							LocalTime.of(9, 48))));
		assertEquals("ungueltige Verschachtelung: Belegung kann kein Teil von Lernzone sein", exception.getMessage());
		assertTrue(lernzone.getProtokoll().isEmpty());
	}
	@Test
	void invalidCompositionStornierungInLernzone(){
		Exception exception = assertThrows(InvalidCompositeException.class, () ->
				lernzone.add(new Stornierung(
						LocalDate.of(2021, 12, 5),
						LocalTime.of(18, 23),
						res)));
		assertEquals("ungueltige Verschachtelung: Stornierung kann kein Teil von Lernzone sein", exception.getMessage());
		assertTrue(lernzone.getProtokoll().isEmpty());
	}
}
