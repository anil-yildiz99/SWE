package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Reservierung;
import model.Stornierung;
import model.Student;
import model.exceptions.AktionException;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

class StornierungTest extends LeafPrintProtkollTest {
	private Reservierung reservierung;
	
    @BeforeEach
    void initTest() throws AktionException, ZeitraumException, StudentException {
        text = "\t\tStornierung der Reservierung R08154711 am 05.12.2021 um 18:23\n";

        reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
        		"R08154711", LocalDate.of(2021, 12, 6),
        		LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
        aktion = new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23), reservierung);
    }
    
    /**
     * Hier wird getestet, ob bei einem nicht setzen einer Reservierung (also null) eine Exception geworfen wird.
     * Dabei werden beide Konstruktoren getestet.
     */
    @Test
	void testStornierung() {
		assertThrows(AktionException.class, () -> new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23), null));
		assertThrows(AktionException.class, () -> new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23), DateTimeFormatter.ofPattern("yyyy.MM.dd"), null));
    }

}
