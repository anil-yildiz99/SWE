package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.exceptions.ZeitraumException;
import model.exceptions.AktionException;

/**
 * Hier handelt es sich um eine konkrete Implementierung der abstrakten Klasse
 * "Aktion". Genauer gesagt, behandelt diese Klasse die Belegungen eines Lernplatzes.
 * Dazu gehoert das Datum (also Datum und Uhrzeit) der Taetigung der Belegung, die
 * zugehoerige Reservierungsinstanz (also welche konkrete Reservierung belegt wird)
 * und der Zeitraum der Belegung (also die Uhrzeit "von" und "bis", in der dieser
 * bestimmte Lernplatz tatsaechlich belegt wurde.
 */
public class Belegung extends Aktion {
    private Reservierung reservierung;
    private LocalTime von;
    private LocalTime bis;

    public Belegung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, Reservierung reservierung, LocalTime von, LocalTime bis) throws ZeitraumException, AktionException {
        super(aktionsDatum, aktionsZeitpunkt);
        if (reservierung == null)
        	throw new AktionException("Zu einer Belegung eines Lernplatzes muss angegeben werden, welche Reservierung belegt wird!");
        if ((von == null) || (bis == null) || von.isAfter(bis))
        	throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
        if (von.isBefore(reservierung.getVon()))
        	throw new ZeitraumException("Der Zeitpunkt einer Belegung darf nicht vor dem Reservierungszeitpunkt erfolgen!");
        if (bis.isAfter(reservierung.getBis()))
        	throw new ZeitraumException("Der Endzeitpunkt einer Belegung darf den Endzeitpunkt einer Reservierung nicht ueberschreiten!");
        
        this.reservierung = reservierung;
        this.von = von;
        this.bis = bis;
    }
    
    public Belegung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, DateTimeFormatter formatierer, Reservierung reservierung, LocalTime von, LocalTime bis) throws ZeitraumException, AktionException {
        super(aktionsDatum, aktionsZeitpunkt, formatierer);
        if (reservierung == null)
        	throw new AktionException("Zu einer Belegung eines Lernplatzes muss angegeben werden, welche Reservierung belegt wird!");
        if ((von == null) || (bis == null) || von.isAfter(bis))
        	throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
        if (von.isBefore(reservierung.getVon()))
        	throw new ZeitraumException("Der Zeitpunkt einer Belegung darf nicht vor dem Reservierungszeitpunkt erfolgen!");
        if (bis.isAfter(reservierung.getBis()))
        	throw new ZeitraumException("Der Endzeitpunkt einer Belegung darf den Endzeitpunkt einer Reservierung nicht ueberschreiten!");
        
        this.reservierung = reservierung;
        this.von = von;
        this.bis = bis;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    public void setReservierung(Reservierung reservierung) throws AktionException {
    	if (reservierung == null)
        	throw new AktionException("Zu einer Belegung eines Lernplatzes muss angegeben werden, welche Reservierung belegt wird!");
    	this.reservierung = reservierung;
    }

    public LocalTime getVon() {
        return von;
    }

    public void setVon(LocalTime von) throws ZeitraumException {
    	if ((von == null) || von.isAfter(bis))
    		throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
    	if (von.isBefore(reservierung.getVon()))
        	throw new ZeitraumException("Der Zeitpunkt einer Belegung darf nicht vor dem Reservierungszeitpunkt erfolgen!");
        this.von = von;
    }

    public LocalTime getBis() {
        return bis;
    }

    public void setBis(LocalTime bis) throws ZeitraumException {
    	if ((bis == null) || bis.isBefore(von))
    		throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
    	 if (bis.isAfter(reservierung.getBis()))
         	throw new ZeitraumException("Der Endzeitpunkt einer Belegung darf den Endzeitpunkt einer Reservierung nicht ueberschreiten!");
        this.bis = bis;
    }

    @Override
    public String printProtokoll() {
        return "\t\tBelegung am " + this.getDatumsFormatierer().format(reservierung.getReservierungsDatum()) +
                " von " + getVon() + " bis " + getBis() + " mit Reservierung " + reservierung.getReservierungsNr() +"\n";
    }
}
