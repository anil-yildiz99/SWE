package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Zeitraum;
import model.exceptions.ZeitraumException;

class ZeitraumTest {
	private Zeitraum zeitraum;
	
	@BeforeEach
	void initTest() throws ZeitraumException {
		zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
	}
	
	@Test
	void testZeitraum() {
		assertThrows(ZeitraumException.class, () -> new Zeitraum(null, LocalDate.of(2021, 12, 4)));
		assertThrows(ZeitraumException.class, () -> new Zeitraum(LocalDate.of(2021, 11, 30), null));
		assertThrows(ZeitraumException.class, () -> new Zeitraum(LocalDate.of(2021, 12, 4), LocalDate.of(2021, 11, 30)));
	}

	/**
	 * Es wird zusaetzlich der Setter "setStartDatum" getestet. Somit wird überprueft, ob bei einem
	 * ungueltigen Parameter (null; startDatum erfolgt nach endDatum) eine Exception geworfen wird.
	 */
	@Test
	void testSetStartDatum() {
		assertThrows(ZeitraumException.class, () -> zeitraum.setStartDatum(null));
		assertThrows(ZeitraumException.class, () -> zeitraum.setStartDatum(LocalDate.of(2021, 12, 10)));
	}
	
	/**
	 * Analog zur vorherigen Testmethode wird hier der Setter "setEndDatum" getestet.
	 */
	@Test
	void testSetEndDatum() {
		assertThrows(ZeitraumException.class, () -> zeitraum.setEndDatum(null));
		assertThrows(ZeitraumException.class, () -> zeitraum.setEndDatum(LocalDate.of(2021, 11, 10)));
	}
}
