package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Diese Klasse ist eine weitere Implementierung der abstrakten Klasse "Aktion".
 * Durch die Klasse wird die Stornierung eines bereits reservierten Lernplatzes
 * ermoeglicht. Dazu muss durch die Variable "reservierung" auf die Reservierung
 * zugegriffen werden, wodurch dann auch angegeben werden kann, welche Reservierung
 * nun storniert werden soll.
 */
public class Stornierung extends Aktion {
    private final Reservierung reservierung;

    public Stornierung(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, Reservierung reservierung) {
        super(aktionsDatum, aktionsZeitpunkt);
        this.reservierung = reservierung;
    }

    public Reservierung getReservierung() {
        return reservierung;
    }

    @Override
    public void printProtokoll() {
        System.out.println("\t\tStornierung der Reservierung " + getReservierung().getReservierungsNr() + " am " + getDatumsFormatierer().format(getAktionsDatum()) + " um " + getAktionsZeitpunkt());
    }
}
