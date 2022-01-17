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
import model.Student;
import model.exceptions.StudentException;

class ReservierungTest extends AktionTest {
	private Reservierung reservierung;
	
	
	private ByteArrayOutputStream b=new ByteArrayOutputStream();
	private PrintStream prev=System.out;
    
	
	@BeforeEach
	void initTest() {
		
		PrintStream p=new PrintStream(b);
		System.setOut(p);
		
		text = "\t\tReservierung R08154711 fuer 06.12.2021 von 09:15 bis 10:00 fuer 1 Person\n" +
                "\t\t\tdurch K12345679 am 01.12.2021 um 23:18\n";
		
		try {
			reservierung = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
			        "R08154711", LocalDate.of(2021, 12, 6),
			        LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
		} catch (StudentException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPrintProtokoll() {
		reservierung.printProtokoll();
		assertEquals(text, b.toString());
	}

}
