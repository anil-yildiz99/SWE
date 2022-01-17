import model.*;
import model.exceptions.StudentException;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * In dieser Klasse befindet sich die main-Methode. Dabei werden die Testdaten erstellt
 * und das Protokoll ausgegeben. Zusaetzlich wird auch noch eine Exception ausgeloest.
 */
public class PlatzFuerTeams {

    public static void main(String[] args) throws StudentException {
        ProtokollWrapper wrapper = new ProtokollWrapper();

        Zeitraum zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
        Lernzone lz1 = new Lernzone("S3-EG-Z01");
        Lernplatz lp1 = new Lernplatz(1,4);
              
        
        Reservierung rs1 = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
                "R08154711", LocalDate.of(2021, 12, 3),
                LocalTime.of(9, 15), LocalTime.of(10, 0), 3, new Student("K12345678"));
        Reservierung rs2 = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
                "R08154711", LocalDate.of(2021, 12, 6),
                LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
        Belegung belegung1 = new Belegung(rs1.getReservierungsDatum(), LocalTime.of(9, 21), rs1, LocalTime.of(9, 21), LocalTime.of(9, 48));
        Stornierung stornierung1 = new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23),rs2);
        				               		
        //Fehlertests:
        lz1.add(lz1);
        lp1.add(lp1);
        lz1.add(belegung1);

        wrapper.add(lz1);
        lz1.add(lp1);
        lp1.add(rs1);
        lp1.add(rs2);
        lp1.add(belegung1);
        lp1.add(stornierung1);

        wrapper.printProtokoll();
        System.out.println();
        wrapper.printProtokollImZeitraum(zeitraum);

    }

}
