package test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(StudentException.class, () -> new Student(null));
    }
    
}
