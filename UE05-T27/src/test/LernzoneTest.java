package test;

import static org.junit.jupiter.api.Assertions.*;

import model.*;
import model.exceptions.AktionException;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

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
    void initActions() throws StudentException, ZeitraumException, AktionException {
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
        stor = new Stornierung(LocalDate.of(2021, 12, 30), LocalTime.of(18, 23), res2);

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

    /**
     * Dieser Test testet die Methode printProtokoll() der Klasse Lernzone. Dabei werden der Lernzone 2 Lernplätze hinzugefügt.
     * Anschließend werden einem dieser Lernplätzen 2 Reservierungen, eine Belegung und eine Stornierung hinzugefügt.
     * Nach jeder Hinzufügung einer Komponente wird anschließend die printProtokoll()-Methode aufgerufen und überprüft,
     * ob der zurückgegebene String, der erwarteten Ausgabe entspricht.
     * @throws InvalidCompositeException
     */
    @Test
    void printProtokoll() throws InvalidCompositeException {
        assertEquals("Lernzone: S3-EG-Z01\n", lernzone.printProtokoll());
        Lernplatz lp = new Lernplatz(1, 5);
        lernzone.add(lp);
        assertEquals("Lernzone: S3-EG-Z01\n\tLernplatz 1 fuer 5 Personen\n", lernzone.printProtokoll());

        lernzone.add(new Lernplatz(2, 1));
        assertEquals("Lernzone: S3-EG-Z01\n" +
                "\tLernplatz 1 fuer 5 Personen\n" +
                "\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());

        lp.add(res);
        assertEquals("Lernzone: S3-EG-Z01\n" +
                "\tLernplatz 1 fuer 5 Personen\n" +
                "\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n" +
                "\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n" +
                "\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());

        lp.add(bel);
        assertEquals("Lernzone: S3-EG-Z01\n" +
                "\tLernplatz 1 fuer 5 Personen\n" +
                "\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n" +
                "\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n" +
                "\t\tBelegung am 03.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n" +
                "\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());

        lp.add(res2);
        lp.add(stor);
        assertEquals("Lernzone: S3-EG-Z01\n" +
                "\tLernplatz 1 fuer 5 Personen\n" +
                "\t\tReservierung R08154711 fuer 03.12.2021 von 09:15 bis 10:00 fuer 3 Personen\n" +
                "\t\t\tdurch K12345678 am 01.12.2021 um 23:18\n" +
                "\t\tBelegung am 03.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n" +
                "\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n" +
                "\t\t\tdurch K12345678 am 22.12.2021 um 01:01\n" +
                "\t\tStornierung der Reservierung R87654321 am 30.12.2021 um 18:23\n" +
                "\tLernplatz 2 fuer 1 Person\n", lernzone.printProtokoll());
    }
    /**
     * Dieser Test testet die Methode printProtokollImZeitraum(Zeitraum zeitraum) der Klasse Lernzone. Dabei wird der Lernzone ein Lernplatz hinzugefügt.
     * Anschließend werden diesem Lernplatz eine Reservierung und eine Stornierung hinzugefügt.
     * Nach jeder Hinzufügung einer Komponente wird anschließend die printProtokollImZeitraum(Zeitraum zeitraum)-Methode aufgerufen und überprüft,
     * ob der zurückgegebene String, der erwarteten Ausgabe entspricht.
     * @throws InvalidCompositeException
     */
    @Test
    void printProtokollImZeitraum() throws InvalidCompositeException, ZeitraumException {
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
    /**
     * Dieser Test testet die add(Component comp) Methode der Klasse Lernzone, dabei wird überprüft, ob das hinzufügen
     * einer gültigen Komponente erfolgreich ist. Führt das Hinzufügen der Komponete zu keiner Exception, wird überprüft,
     * ob diese in der Komponenten-Collection der Klasse hinzugefügt wurde, sprich ob die size() Methode einen erwarteten Wert
     * zurückliefert.
     */
    @Test
    void add() {
        assertTrue(lernzone.getProtokoll().isEmpty());
        try {
            assertTrue(lernzone.add(new Lernplatz(1, 5)));
        } catch (model.exceptions.InvalidCompositeException e) {
            fail(e.getMessage());
        }
        assertEquals(1, lernzone.getProtokoll().size());
    }
    /**
     * Dieser Test testet ob das Hinzufügen einer Lernzone in eine Lernzone eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionLernzoneInLernzone() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                lernzone.add(new Lernzone("S7-EG-Z01")));
        assertEquals("ungueltige Verschachtelung: Lernzone kann kein Teil von Lernzone sein", exception.getMessage());
        assertTrue(lernzone.getProtokoll().isEmpty());
    }
    /**
     * Dieser Test testet ob das Hinzufügen einer Reservierung in eine Lernzone eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionReservierungInLernzone() {
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

    /**
     * Dieser Test testet ob das Hinzufügen einer Belegung in eine Lernzone eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionBelegungInLernzone() {
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

    /**
     * Dieser Test testet ob das Hinzufügen einer Stornierung in eine Lernzone eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionStornierungInLernzone() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                lernzone.add(new Stornierung(
                        LocalDate.of(2021, 12, 5),
                        LocalTime.of(18, 23),
                        res)));
        assertEquals("ungueltige Verschachtelung: Stornierung kann kein Teil von Lernzone sein", exception.getMessage());
        assertTrue(lernzone.getProtokoll().isEmpty());
    }
    /**
     * Dieser Test testet ob das Hinzufügen eines Wrappers in eine Lernzone eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionWrapperInLernzone() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                lernzone.add(new ProtokollWrapper()));
        assertEquals("ungueltige Verschachtelung: ProtokollWrapper kann kein Teil von Lernzone sein", exception.getMessage());
        assertTrue(lernzone.getProtokoll().isEmpty());
    }
}
