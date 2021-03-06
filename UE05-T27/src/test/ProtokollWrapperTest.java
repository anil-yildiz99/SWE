package test;

import model.*;
import model.exceptions.AktionException;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProtokollWrapperTest {

    private ProtokollWrapper wrapper;
    private Reservierung res;
    private Reservierung res2;
    private Stornierung stor;
    private Belegung bel;

    @BeforeEach
    void setUp() {
        wrapper = new ProtokollWrapper();
    }

    @BeforeAll
    void initAktionen() throws StudentException, ZeitraumException, AktionException {
        res = new Reservierung(
                LocalDate.of(2022, 1, 5),
                LocalTime.of(22, 11),
                "R86574123",
                LocalDate.of(2022, 1, 7),
                LocalTime.of(16, 13),
                LocalTime.of(18, 0),
                8,
                new Student("K74125863"));
        res2 = new Reservierung(
                LocalDate.of(2021, 12, 23),
                LocalTime.of(7, 7),
                "R87654321",
                LocalDate.of(2021, 12, 29),
                LocalTime.of(22, 15),
                LocalTime.of(23, 55),
                7,
                new Student("K12345678"));
        bel = new Belegung(res.getReservierungsDatum(), LocalTime.of(9, 21), res, LocalTime.of(16, 32), LocalTime.of(17, 44));
        stor = new Stornierung(LocalDate.of(2021, 12, 30), LocalTime.of(18, 23), res2);
    }

    /**
     * Dieser Test testet die Methode printProtokoll() der Klasse ProtokollWrapper.
     * Dabei wird dem Wrapper eine Lernzone mit zwei Lernplätzen hinzugefügt. Wobei einer dieser Lernplätze
     * 2 Reservierungen, eine Belegung und eine Stornierung hinzugefügt werden.
     *
     * Nach Hinzufügung der Komponenten wird die printProtokoll()-Methode des Wrappers aufgerufen und überprüft, ob
     * der zurückgegebene String der erwarteten Zeichenkette entspricht. Die korrekte Schritt-für-Schritt-Schachtelung der
     * einzelnen Komponenten wird bereits in Tests der jeweiligen Komponenten abgedeckt, wodurch in diesem Test nur ein Vergleich mit der
     * finalen Zeichenkette überprüft wird.
     * @throws InvalidCompositeException
     */
    @Test
    void printProtokoll() throws InvalidCompositeException {
        Lernzone lz = new Lernzone("Teichwerk-EG-Z04");
        wrapper.add(lz);
        Lernplatz lp = new Lernplatz(1, 5);
        Lernplatz lp2 = new Lernplatz(2, 1);
        lz.add(lp);
        lz.add(lp2);
        lp.add(res);
        lp.add(bel);
        lp.add(res2);
        lp.add(stor);

        assertEquals("Gesamtprotokoll\n" +
                "Lernzone: Teichwerk-EG-Z04\n" +
                "\tLernplatz 1 fuer 5 Personen\n" +
                "\t\tReservierung R86574123 fuer 07.01.2022 von 16:13 bis 18:00 fuer 8 Personen\n" +
                "\t\t\tdurch K74125863 am 05.01.2022 um 22:11\n" +
                "\t\tBelegung am 07.01.2022 von 16:32 bis 17:44 mit Reservierung R86574123\n" +
                "\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n" +
                "\t\t\tdurch K12345678 am 23.12.2021 um 07:07\n" +
                "\t\tStornierung der Reservierung R87654321 am 30.12.2021 um 18:23\n" +
                "\tLernplatz 2 fuer 1 Person\n" +
                "Protokollende", wrapper.printProtokoll());
    }
    /**
     * Dieser Test testet die Methode printProtokollImZeitraum(Zeitraum zeitraum) der Klasse ProtokollWrapper.
     * Dabei wird dem Wrapper eine Lernzone mit zwei Lernplätzen hinzugefügt. Wobei einem dieser Lernplätze
     * eine Reservierung und eine Belegung und dem anderen eine Reservierung mit einer Stornierung hinzugefügt werden.
     *
     * Nach Hinzufügung der Komponenten wird die printProtokollImZeitraum(Zeitraum zeitraum)-Methode des Wrappers aufgerufen und überprüft, ob
     * der zurückgegebene String der erwarteten Zeichenkette entspricht. Die korrekte Schritt-für-Schritt-Schachtelung der
     * einzelnen Komponenten wird bereits in Tests der jeweiligen Komponenten abgedeckt, wodurch in diesem Test nur ein Vergleich mit der
     * finalen Zeichenkette überprüft wird.
     * @throws InvalidCompositeException
     * @throws ZeitraumException
     */
    @Test
    void printProtokollImZeitraum() throws InvalidCompositeException, ZeitraumException {
        Zeitraum zeitraum = new Zeitraum(LocalDate.of(2021, 9, 30), LocalDate.of(2021, 12, 31));
        Lernzone lz = new Lernzone("Kepler Hall-EG-Z09");
        wrapper.add(lz);
        Lernplatz lp = new Lernplatz(4, 8);
        Lernplatz lp2 = new Lernplatz(9, 1);
        lz.add(lp);
        lz.add(lp2);
        lp.add(res);
        lp.add(bel);
        lp2.add(res2);
        lp2.add(stor);

        assertEquals("Protokoll im Zeitraum von 2021-09-30 bis 2021-12-31\n" +
                "Lernzone: Kepler Hall-EG-Z09\n" +
                "\tLernplatz 4 fuer 8 Personen\n" +
                "\tLernplatz 9 fuer 1 Person\n" +
                "\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n" +
                "\t\t\tdurch K12345678 am 23.12.2021 um 07:07\n" +
                "\t\tStornierung der Reservierung R87654321 am 30.12.2021 um 18:23\n" +
                "Protokollende", wrapper.printProtokollImZeitraum(zeitraum));
    }
    /**
     * Dieser Test testet die add(Component comp) Methode der Klasse ProtokollWrapper, dabei wird überprüft, ob das hinzufügen
     * einer gültigen Komponente erfolgreich ist. Führt das Hinzufügen der Komponete zu keiner Exception, wird überprüft,
     * ob diese in der Komponenten-Collection der Klasse hinzugefügt wurde, sprich ob die size() Methode einen erwarteten Wert
     * zurückliefert.
     */
    @Test
    void add() {
        assertTrue(wrapper.getProtokoll().isEmpty());
        try {
            assertTrue(wrapper.add(new Lernzone("Bibliothek-EG-Z03")));
        } catch (model.exceptions.InvalidCompositeException e) {
            fail(e.getMessage());
        }
        assertEquals(1, wrapper.getProtokoll().size());
    }
    /**
     * Dieser Test testet ob das Hinzufügen eines Wrappers in einen Wrapper eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionWrapperInWrapper() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new ProtokollWrapper()));
        assertEquals("ungueltige Verschachtelung: ProtokollWrapper kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
    /**
     * Dieser Test testet ob das Hinzufügen eines Lernplatzes in einen Wrapper eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionLernplatzInWrapper() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new Lernplatz(5, 7)));
        assertEquals("ungueltige Verschachtelung: Lernplatz kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
    /**
     * Dieser Test testet ob das Hinzufügen einer Reservierung in einen Wrapper eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionReservierungInWrapper() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new Reservierung(
                        LocalDate.of(2021, 1, 15),
                        LocalTime.of(12, 13),
                        "R45678123",
                        LocalDate.of(2021, 1, 18),
                        LocalTime.of(5, 4),
                        LocalTime.of(6, 3),
                        3,
                        new Student("K78541236"))));
        assertEquals("ungueltige Verschachtelung: Reservierung kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
    /**
     * Dieser Test testet ob das Hinzufügen einer Belegung in einen Wrapper eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionBelegungInWrapper() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new Belegung(
                        res.getReservierungsDatum(),
                        LocalTime.of(16, 13),
                        res,
                        LocalTime.of(16, 15),
                        LocalTime.of(18, 0))));
        assertEquals("ungueltige Verschachtelung: Belegung kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
    /**
     * Dieser Test testet ob das Hinzufügen einer Stornierung in einen Wrapper eine erwartete Exception mit entsprechender
     * Nachricht liefert.
     */
    @Test
    void invalidCompositionStornierungInWrapper() {
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new Stornierung(
                        LocalDate.of(2022, 1, 8),
                        LocalTime.of(18, 23),
                        res)));
        assertEquals("ungueltige Verschachtelung: Stornierung kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
}