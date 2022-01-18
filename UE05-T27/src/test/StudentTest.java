package test;

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
    void testGetMatrikelnummer() {
        assertEquals("k12345678", student.getMatrikelnummer());

        try {
            student = new Student("k87654321");
        } catch (StudentException e) {
            e.printStackTrace();
        }
        assertEquals("k87654321", student.getMatrikelnummer());
    }

}
