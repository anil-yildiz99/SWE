import model.*;
import model.exceptions.AktionException;
import model.exceptions.StudentException;
import model.exceptions.ZeitraumException;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * In dieser Klasse befindet sich die main-Methode. Dabei werden die Testdaten erstellt
 * und das Protokoll ausgegeben. Zusaetzlich wird auch noch eine Exception ausgeloest.
 */
public class PlatzFuerTeams {

    public static void main(String[] args) throws StudentException, ZeitraumException, AktionException {
        ProtokollWrapper wrapper = new ProtokollWrapper();

        Zeitraum zeitraum = null;
		try {
			zeitraum = new Zeitraum(LocalDate.of(2021, 11, 30), LocalDate.of(2021, 12, 4));
		} catch (ZeitraumException e1) {
			e1.printStackTrace();
		}
        Lernzone lz1 = new Lernzone("S3-EG-Z01");
        Lernplatz lp1 = new Lernplatz(1,4);


        Reservierung rs1 = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 18),
                "R08154711", LocalDate.of(2021, 12, 3),
                LocalTime.of(9, 15), LocalTime.of(10, 0), 3, new Student("K12345678"));
        Reservierung rs2 = new Reservierung(LocalDate.of(2021, 12, 1), LocalTime.of(23, 40),
                "R87456321", LocalDate.of(2021, 12, 6),
                LocalTime.of(9, 15), LocalTime.of(10, 0), 1, new Student("K12345679"));
        Belegung belegung1 = new Belegung(rs1.getReservierungsDatum(), LocalTime.of(9, 21), rs1, LocalTime.of(9, 21), LocalTime.of(9, 48));
        Stornierung stornierung1 = new Stornierung(LocalDate.of(2021, 12, 5), LocalTime.of(18, 23),rs2);

        //Fehlertests:
        try {
            lz1.add(lz1);
        } catch (model.exceptions.InvalidCompositeException e) {
            System.err.println(e.getMessage());
        }
        try {
            lp1.add(lp1);
        } catch (model.exceptions.InvalidCompositeException e) {
            System.err.println(e.getMessage());
        }
        try {
            lz1.add(belegung1);
        } catch (model.exceptions.InvalidCompositeException e) {
            System.err.println(e.getMessage());
        }

        try {
            wrapper.add(lz1);
            lz1.add(lp1);
            lp1.add(rs1);
            lp1.add(rs2);
            lp1.add(belegung1);
            lp1.add(stornierung1);
        } catch (model.exceptions.InvalidCompositeException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(wrapper.printProtokoll());
        System.out.println();
        System.out.println(wrapper.printProtokollImZeitraum(zeitraum));


    }

}
