package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Student;
import model.exceptions.StudentException;

class StudentTest {
	
	private Student student;
	
	@BeforeEach
	void initTest() throws StudentException {
		student = new Student("k12345678");
	}
	
	@Test
	void testStudent() {
		assertThrows(StudentException.class, () -> new Student("f12345678"));
		assertThrows(StudentException.class, () -> new Student("k123456789"));
		assertThrows(StudentException.class, () -> new Student("k12345a78"));
	}

	@Test
	void testGetMatrikelnummer() throws StudentException {
		assertEquals("k12345678", student.getMatrikelnummer());
		
		student = new Student("k87654321");
		assertEquals("k87654321", student.getMatrikelnummer());
	}

}
