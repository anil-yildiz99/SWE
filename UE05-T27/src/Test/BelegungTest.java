package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Belegung;
import model.Reservierung;
import model.Student;
import model.exceptions.StudentException;

class BelegungTest extends AktionTest{
	private Belegung belegung; 


	private ByteArrayOutputStream b=new ByteArrayOutputStream();
	private PrintStream prev=System.out;
    
	
	@BeforeEach
	void initTest() {
		
		PrintStream p=new PrintStream(b);
		System.setOut(p);
		
		text = "\t\tBelegung am 06.12.2021 von 09:21 bis 09:48 mit Reservierung R08154711\n";
		
		try {
			Reservierung reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
			        "R08154711", LocalDate.of(2021, 12, 6),
			        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
			belegung = new Belegung(reservierung.getReservierungsDatum(), LocalTime.of(9, 21), reservierung, LocalTime.of(9, 21), LocalTime.of(9, 48));
		} catch (StudentException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPrintProtokoll() {
		belegung.printProtokoll();
		assertEquals(text, b.toString());
	}

}
