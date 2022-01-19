package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.exceptions.AktionException;
import model.exceptions.ZeitraumException;

/**
 * Diese Klasse ist eine weitere Implementierung der abstrakten Klasse "Aktion".
 * Durch die Klasse wird die Stornierung eines bereits reservierten Lernplatzes
 * ermoeglicht. Dazu muss durch die Variable "reservierung" auf die Reservierung
 * zugegriffen werden, wodurch dann auch angegeben werden kann, welche Reservierung
 * nun storniert werden soll.
 */
public class Stornierung extends Aktion {
    private final Reservierung reservierung;

    public Stornierung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, Reservierung reservierung) throws ZeitraumException, AktionException {
        super(aktionsDatum, aktionsZeitpunkt);
        if (reservierung == null)
        	throw new AktionException("Zu einer Belegung eines Lernplatzes muss angegeben werden, welche Reservierung belegt wird!");
        
        this.reservierung = reservierung;
    }
    
    public Stornierung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, DateTimeFormatter formatierer, Reservierung reservierung) throws ZeitraumException, AktionException {
        super(aktionsDatum, aktionsZeitpunkt, formatierer);
        if (reservierung == null)
        	throw new AktionException("Zu einer Belegung eines Lernplatzes muss angegeben werden, welche Reservierung belegt wird!");
        
        this.reservierung = reservierung;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    @Override
    public String printProtokoll() {
        return "\t\tStornierung der Reservierung " + getReservierung().getReservierungsNr() + " am " + getDatumsFormatierer().format(getAktionsDatum()) + " um " + getAktionsZeitpunkt() +"\n";
    }
}
