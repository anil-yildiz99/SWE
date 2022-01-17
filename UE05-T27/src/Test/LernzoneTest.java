package Test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LernzoneTest {

	private Lernzone lernzone;
	private Reservierung res;
	private Belegung bel;
	private Stornierung stor;

	//private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	//private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();



	@BeforeAll
	public void setUpStreams() {
		//System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	@BeforeAll
	public void initActions() throws StudentException {
		res = new Reservierung(
				LocalDate.of(2021, 12, 1),
				LocalTime.of(23, 18),
				"R08154711",
				LocalDate.of(2021, 12, 3),
				LocalTime.of(9, 15),
				LocalTime.of(10, 0),
				3,
				new Student("K12345678"));
		bel = new Belegung(res.getReservierungsDatum(), LocalTime.of(9, 21), res, LocalTime.of(9, 21), LocalTime.of(9, 48));
		stor = new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23),res);

	}

	@AfterAll
	public void restoreStreams() {
		//System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@BeforeEach
	void initTest() {
		lernzone = new Lernzone("S3-EG-Z01");

	}

	@AfterEach
	void tearDown() {
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
	void printProtokoll() {
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

		lp.add(stor);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n" +
				"\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n" +
				"\t\tBelegung am 03.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n" +
				"\t\tStornierung der Reservierung R08154711 am 05.12.2021 um 18:23\n" +
				"\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());
	}

	@Test
	void printProtokollImZeitraum() {
		Zeitraum zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
		assertEquals("Lernzone: S3-EG-Z01\n", lernzone.printProtokollImZeitraum(zeitraum));
		Lernplatz lp = new Lernplatz(1,5);
		lernzone.add(lp);
		assertEquals("Lernzone: S3-EG-Z01\n\tLernplatz 1 fuer 5 Personen\n", lernzone.printProtokollImZeitraum(zeitraum));

		lp.add(res);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n"+
				"\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n", lernzone.printProtokollImZeitraum(zeitraum));
		lp.add(stor);
		assertEquals("Lernzone: S3-EG-Z01\n" +
				"\tLernplatz 1 fuer 5 Personen\n" +
				"\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n"+
				"\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n", lernzone.printProtokollImZeitraum(zeitraum));
	}

	@Test
	void add() {
		assertEquals(0, lernzone.getProtokoll().size());
		lernzone.add(new Lernzone("S4-EG-Z01"));
		assertEquals(0, lernzone.getProtokoll().size());
		assertEquals("ungueltige Verschachtelung: Lernzone kann kein Teil von Lernzone sein" + System.lineSeparator(), errContent.toString());
		errContent.reset();

		lernzone.add(res);
		assertEquals(0, lernzone.getProtokoll().size());
		assertEquals("ungueltige Verschachtelung: Reservierung kann kein Teil von Lernzone sein" + System.lineSeparator(), errContent.toString());
		errContent.reset();

		lernzone.add(bel);
		assertEquals(0, lernzone.getProtokoll().size());
		assertEquals("ungueltige Verschachtelung: Belegung kann kein Teil von Lernzone sein" + System.lineSeparator(), errContent.toString());
		errContent.reset();

		lernzone.add(stor);
		assertEquals(0, lernzone.getProtokoll().size());
		assertEquals("ungueltige Verschachtelung: Stornierung kann kein Teil von Lernzone sein" + System.lineSeparator(), errContent.toString());
		errContent.reset();

		lernzone.add(new Lernplatz(1,5));
		assertEquals(1, lernzone.getProtokoll().size());
	}

}
