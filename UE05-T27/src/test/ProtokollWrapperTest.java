package test;

import model.*;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
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
    void initAktionen() throws StudentException {
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
        bel = new Belegung(res.getReservierungsDatum(), LocalTime.of(9, 21), res, LocalTime.of(9, 21), LocalTime.of(9, 48));
        stor = new Stornierung(LocalDate.of(2021, 12, 30), LocalTime.of(18, 23),res2);
    }

    @Test
    void printProtokoll() throws InvalidCompositeException {
        Lernzone lz = new Lernzone("Teichwerk-EG-Z04");
        wrapper.add(lz);
        Lernplatz lp = new Lernplatz(1,5);
        Lernplatz lp2 = new Lernplatz(2,1);
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
                "\t\tBelegung am 07.01.2022 von 09:21 bis 09:48 mit Reservierung R86574123\n" +
                "\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n"+
                "\t\t\tdurch K12345678 am 23.12.2021 um 07:07\n"+
                "\t\tStornierung der Reservierung R87654321 am 30.12.2021 um 18:23\n" +
                "\tLernplatz 2 fuer 1 Person\n"+
                "Protokollende", wrapper.printProtokoll());
    }

    @Test
    void printProtokollImZeitraum() throws InvalidCompositeException {
        Zeitraum zeitraum = new Zeitraum(LocalDate.of(2021, 9, 30), LocalDate.of(2021, 12, 31));
        Lernzone lz = new Lernzone("Kepler Hall-EG-Z09");
        wrapper.add(lz);
        Lernplatz lp = new Lernplatz(4,8);
        Lernplatz lp2 = new Lernplatz(9,1);
        lz.add(lp);
        lz.add(lp2);
        lp.add(res);
        lp.add(bel);
        lp2.add(res2);
        lp2.add(stor);

        assertEquals("Protokoll im Zeitraum von 2021-09-30 bis 2021-12-31\n" +
                "Lernzone: Kepler Hall-EG-Z09\n" +
                "\tLernplatz 4 fuer 8 Personen\n" +
                "\tLernplatz 9 fuer 1 Person\n"+
                "\t\tReservierung R87654321 fuer 29.12.2021 von 22:15 bis 23:55 fuer 7 Personen\n"+
                "\t\t\tdurch K12345678 am 23.12.2021 um 07:07\n"+
                "\t\tStornierung der Reservierung R87654321 am 30.12.2021 um 18:23\n" +
                "Protokollende", wrapper.printProtokollImZeitraum(zeitraum));
    }

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
    @Test
    void invalidCompositionWrapperInWrapper(){
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new ProtokollWrapper()));
        assertEquals("ungueltige Verschachtelung: ProtokollWrapper kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
    @Test
    void invalidCompositionLernplatzInWrapper(){
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new Lernplatz(5,7)));
        assertEquals("ungueltige Verschachtelung: Lernplatz kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
    @Test
    void invalidCompositionReservierungInWrapper(){
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
    @Test
    void invalidCompositionBelegungInLernzone(){
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
    @Test
    void invalidCompositionStornierungInLernzone(){
        Exception exception = assertThrows(InvalidCompositeException.class, () ->
                wrapper.add(new Stornierung(
                        LocalDate.of(2022, 1, 8),
                        LocalTime.of(18, 23),
                        res)));
        assertEquals("ungueltige Verschachtelung: Stornierung kann kein Teil von ProtokollWrapper sein", exception.getMessage());
        assertTrue(wrapper.getProtokoll().isEmpty());
    }
}