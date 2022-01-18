package test;

import model.*;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LernplatzTest {

    private Lernplatz lernplatz;
    private Reservierung res;
    private Reservierung res2;
    private Belegung bel;
    private Stornierung stor;

    @BeforeAll
    public void initActions() throws StudentException {
        res = new Reservierung(
                LocalDate.of(2021, 11, 1),
                LocalTime.of(15, 20),
                "R12345678",
                LocalDate.of(2021, 11, 1),
                LocalTime.of(16, 25),
                LocalTime.of(18, 30),
                3,
                new Student("K12345678"));
        res2 = new Reservierung(
                LocalDate.of(2021, 12, 24),
                LocalTime.of(2, 30),
                "R87654321",
                LocalDate.of(2021, 1, 3),
                LocalTime.of(11, 10),
                LocalTime.of(14, 21),
                6,
                new Student("K12345678"));
        bel = new Belegung(res.getReservierungsDatum(), LocalTime.of(16, 25), res, LocalTime.of(16, 25), LocalTime.of(17, 30));
        stor = new Stornierung(LocalDate.of(2021, 12, 25), LocalTime.of(12, 0), res2);
    }

    @BeforeEach
    void initTest() {
        lernplatz = new Lernplatz(5, 6);
    }

    @Test
    void getNummer() {
        assertEquals(5, 5);
    }

    @Test
    void setNummer() {
        lernplatz.setNummer(2);
        assertEquals(2, lernplatz.getNummer());
    }

    @Test
    void getKapazitaet() {
        assertEquals(6, 6);
    }

    @Test
    void setKapazitaet() {
        lernplatz.setKapazitaet(10);
        assertEquals(10, lernplatz.getKapazitaet());
    }

    @Test
    void printProtokoll() throws InvalidCompositeException {
        assertEquals("\tLernplatz 5 fuer 6 Personen\n", lernplatz.printProtokoll());
        assertEquals("\tLernplatz 1 fuer 1 Person\n", new Lernplatz(1, 1).printProtokoll());

        lernplatz.add(res);
        assertEquals("\tLernplatz 5 fuer 6 Personen\n" +
                "\t\tReservierung R12345678 fuer 01.11.2021 von 16:25 bis 18:30 fuer 3 Personen\n" +
                "\t\t\tdurch K12345678 am 01.11.2021 um 15:20\n", lernplatz.printProtokoll());


        lernplatz.add(bel);
        assertEquals("\tLernplatz 5 fuer 6 Personen\n" +
                "\t\tReservierung R12345678 fuer 01.11.2021 von 16:25 bis 18:30 fuer 3 Personen\n" +
                "\t\t\tdurch K12345678 am 01.11.2021 um 15:20\n" +
                "\t\tBelegung am 01.11.2021 von 16:25 bis 17:30 mit Reservierung R12345678\n", lernplatz.printProtokoll());

        lernplatz.add(res2);
        lernplatz.add(stor);
        assertEquals("\tLernplatz 5 fuer 6 Personen\n" +
                "\t\tReservierung R12345678 fuer 01.11.2021 von 16:25 bis 18:30 fuer 3 Personen\n" +
                "\t\t\tdurch K12345678 am 01.11.2021 um 15:20\n" +
                "\t\tBelegung am 01.11.2021 von 16:25 bis 17:30 mit Reservierung R12345678\n" +
                "\t\tReservierung R87654321 fuer 03.01.2021 von 11:10 bis 14:21 fuer 6 Personen\n" +
                "\t\t\tdurch K12345678 am 24.12.2021 um 02:30\n" +
                "\t\tStornierung der Reservierung R87654321 am 25.12.2021 um 12:00\n", lernplatz.printProtokoll());
    }

    @Test
    void printProtokollImZeitraum() {
        Zeitraum zeitraum = new Zeitraum(LocalDate.of(2021, 10, 1), LocalDate.of(2021, 12, 10));
        assertEquals("\tLernplatz 5 fuer 6 Personen\n", lernplatz.printProtokollImZeitraum(zeitraum));
        assertEquals("\tLernplatz 1 fuer 1 Person\n", new Lernplatz(1, 1).printProtokollImZeitraum(zeitraum));

    }

    @Test
    void invalidCompositionLernzoneInLernplatz() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                lernplatz.add(new Lernzone("Teichwerk-EG-Z01")));
        assertEquals("ungueltige Verschachtelung: Lernzone kann kein Teil von Lernplatz sein", exception.getMessage());
        assertTrue(lernplatz.getProtokoll().isEmpty());
    }

    @Test
    void invalidCompositionLernplatzInLernplatz() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                lernplatz.add(new Lernplatz(1, 15)));
        assertEquals("ungueltige Verschachtelung: Lernplatz kann kein Teil von Lernplatz sein", exception.getMessage());
        assertTrue(lernplatz.getProtokoll().isEmpty());
    }

    @Test
    void add() {
        try {
            assertTrue(lernplatz.add(res));
            assertEquals(1, lernplatz.getProtokoll().size());
            assertTrue(lernplatz.add(bel));
            assertEquals(2, lernplatz.getProtokoll().size());
            assertTrue(lernplatz.add(stor));
            assertEquals(3, lernplatz.getProtokoll().size());
        } catch (model.exceptions.InvalidCompositeException e) {
            fail(e.getMessage());
        }
    }
}