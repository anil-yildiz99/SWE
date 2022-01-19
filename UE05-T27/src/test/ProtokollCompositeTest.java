package test;

import model.*;
import model.exceptions.InvalidCompositeException;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ProtokollCompositeTest {

    @Test
    void deleteTest() throws StudentException, InvalidCompositeException, ZeitraumException {
        Lernzone lz = new Lernzone("Learning Center-EG-Z01");
        Lernplatz lp = new Lernplatz(6, 8);
        Reservierung res = new Reservierung(
                LocalDate.of(2022, 1, 1),
                LocalTime.of(6, 6),
                "R87654321",
                LocalDate.of(2022, 1, 3),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                7,
                new Student("K87654321"));
        Belegung bel = new Belegung(res.getReservierungsDatum(), LocalTime.of(9, 21), res, LocalTime.of(10, 00), LocalTime.of(11, 00));

        Reservierung res2 = new Reservierung(
                LocalDate.of(2022, 1, 1),
                LocalTime.of(7, 7),
                "R87654321",
                LocalDate.of(2022, 1, 5),
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                7,
                new Student("K12387546"));
        Stornierung stor = new Stornierung(LocalDate.of(2022, 1, 3), LocalTime.of(2, 2), res2);
        ProtokollWrapper wrapper = new ProtokollWrapper();
        wrapper.add(lz);
        lz.add(lp);
        lp.add(res);
        lp.add(bel);
        lp.add(res2);
        lp.add(stor);
        assertEquals(lp.getProtokoll().size(), 4);
        lp.delete(stor);                                  //Löschen einer Stornierung
        assertEquals(lp.getProtokoll().size(), 3);
        lp.delete(res2);                                  //Löschen einer Reservierung
        assertEquals(lp.getProtokoll().size(), 2);
        lp.delete(bel);                                   //Löschen einer Belegung
        assertEquals(lp.getProtokoll().size(), 1);

        lz.delete(lp);                                    //Löschen eines Lernplatzes mit eigener Kindkomponente
        assertTrue(lz.getProtokoll().isEmpty());

        wrapper.delete(lz);                               //Löschen einer Lernzone
        assertTrue(lz.getProtokoll().isEmpty());
    }
}