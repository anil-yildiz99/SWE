package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Reservierung;
import model.Stornierung;
import model.Student;
import model.exceptions.StudentException;

class StornierungTest extends AktionTest {
	private Stornierung stornierung;
	
	
	private ByteArrayOutputStream b=new ByteArrayOutputStream();
	private PrintStream prev=System.out;
    
	
	@BeforeEach
	void initTest() {
		
		PrintStream p=new PrintStream(b);
		System.setOut(p);
		
		text = "\t\tStornierung der Reservierung R08154711 am 05.12.2021 um 18:23\n";
		
		try {
			Reservierung reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
			        "R08154711", LocalDate.of(2021, 12, 6),
			        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
			stornierung = new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23), reservierung);
		} catch (StudentException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPrintProtokoll() {
		stornierung.printProtokoll();
		assertEquals(text, b.toString());
	}

}
