package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.exceptions.ZeitraumException;

/**
 * Bei dieser Klasse handelt es sich um eine konkrete Implementierung der abstrakten
 * Klasse "Aktion", genauer gesagt, um die Reservierung eines Lernplatzes. Zu jeder
 * Reservierung wird die Reservierungsnummer, das Datum der Reservierung (also fuer
 * welches Datum reserviert wird), die Uhrzeit "von" und "bis" (also fuer welchen
 * Zeitraum wird ein Lernplatz reserviert), die Anzahl der Personen und der Student,
 * welcher die Reservierung taetigt, gespeichert.
 */
public class Reservierung extends Aktion {
    private String reservierungsNr;
    private LocalDate reservierungsDatum;
    private LocalTime von;
    private LocalTime bis;
    private int personenAnzahl;
    private final Student student;

    public Reservierung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, String reservierungsNr, LocalDate reservierungsDatum, LocalTime von, LocalTime bis, int personenAnzahl, Student student) throws ZeitraumException {
        super(aktionsDatum, aktionsZeitpunkt);
        if ((reservierungsDatum == null))
        	throw new ZeitraumException("Es wurde ein ungueltiges Reservierungsdatum eingegeben!");
        if (reservierungsDatum.isBefore(aktionsDatum))
        	throw new ZeitraumException("Das Reservierungsdatum darf nicht vor der Durchfuehrung dieser Aktion sein!");
        if ((von == null) || (bis == null) || von.isAfter(bis))
        	throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
        
        this.reservierungsNr = reservierungsNr;
        this.reservierungsDatum = reservierungsDatum;
        this.von = von;
        this.bis = bis;
        this.personenAnzahl = personenAnzahl;
        this.student = student;
    }
    
    public Reservierung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, DateTimeFormatter formatierer, String reservierungsNr, LocalDate reservierungsDatum, LocalTime von, LocalTime bis, int personenAnzahl, Student student) throws ZeitraumException {
        super(aktionsDatum, aktionsZeitpunkt, formatierer);
        if ((reservierungsDatum == null))
        	throw new ZeitraumException("Es wurde ein ungueltiges Reservierungsdatum eingegeben!");
        if (reservierungsDatum.isBefore(aktionsDatum))
        	throw new ZeitraumException("Das Reservierungsdatum darf nicht vor der Durchfuehrung dieser Aktion sein!");
        if ((von == null) || (bis == null) || von.isAfter(bis))
        	throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
        
        this.reservierungsNr = reservierungsNr;
        this.reservierungsDatum = reservierungsDatum;
        this.von = von;
        this.bis = bis;
        this.personenAnzahl = personenAnzahl;
        this.student = student;
    }

    public String getReservierungsNr() {
        return reservierungsNr;
    }

    public LocalDate getReservierungsDatum() {
        return reservierungsDatum;
    }

    public LocalTime getVon() {
        return von;
    }

    public void setVon(LocalTime von) throws ZeitraumException {
    	if ((von == null) || von.isAfter(bis))
    		throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
        this.von = von;
    }

    public LocalTime getBis() {
        return bis;
    }

    public void setBis(LocalTime bis) throws ZeitraumException {
    	if ((bis == null) || bis.isBefore(von))
    		throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
        this.bis = bis;
    }

    public int getPersonenAnzahl() {
        return personenAnzahl;
    }

    public void setPersonenAnzahl(int personenAnzahl) {
    	if (personenAnzahl != 0)
    		this.personenAnzahl = personenAnzahl;
    }

    public Student getStudent() {
        return student;
    }

    /**
     * In der Ausgabe einer Reservierung wird ueberprueft, fuer wie viele Personen ein bestimmter
     * Lernplatz reserviert wird. Je nach dem wird die Ausgabe des Protokolls angepasst.
     * @return
     */
    @Override
    public String printProtokoll() {
        String str = "";
        if(personenAnzahl==1) {
            str = "\t\tReservierung " + reservierungsNr + " fuer " + getDatumsFormatierer().format(getReservierungsDatum()) + " von " + getVon() + " bis " + getBis() + " fuer " + personenAnzahl + " Person\n" +
                    "\t\t\tdurch " + getStudent().getMatrikelnummer() + " am " + getDatumsFormatierer().format(getAktionsDatum()) + " um " + getAktionsZeitpunkt() +"\n";
        } else{
            str = "\t\tReservierung " + reservierungsNr + " fuer " + getDatumsFormatierer().format(getReservierungsDatum()) + " von " + getVon() + " bis " + getBis() + " fuer " + personenAnzahl + " Personen\n" +
                    "\t\t\tdurch " + getStudent().getMatrikelnummer() + " am " + getDatumsFormatierer().format(getAktionsDatum()) + " um " + getAktionsZeitpunkt() +"\n";
        }
        return str;
    }
}
