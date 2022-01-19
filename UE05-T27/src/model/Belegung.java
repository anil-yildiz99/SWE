package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.exceptions.ZeitraumException;

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

    public Belegung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, Reservierung reservierung, LocalTime von, LocalTime bis) throws ZeitraumException {
        super(aktionsDatum, aktionsZeitpunkt);
        this.reservierung = reservierung;
        this.von = von;
        this.bis = bis;
    }
    
    public Belegung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, DateTimeFormatter formatierer, Reservierung reservierung, LocalTime von, LocalTime bis) throws ZeitraumException {
        super(aktionsDatum, aktionsZeitpunkt, formatierer);
        this.reservierung = reservierung;
        this.von = von;
        this.bis = bis;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    public void setReservierung(Reservierung reservierung) {
        this.reservierung = reservierung;
    }

    public LocalTime getVon() {
        return von;
    }

    public void setVon(LocalTime von) {
        this.von = von;
    }

    public LocalTime getBis() {
        return bis;
    }

    public void setBis(LocalTime bis) {
        this.bis = bis;
    }

    @Override
    public String printProtokoll() {
        return "\t\tBelegung am " + this.getDatumsFormatierer().format(reservierung.getReservierungsDatum()) +
                " von " + getVon() + " bis " + getBis() + " mit Reservierung " + reservierung.getReservierungsNr() +"\n";
    }
}
