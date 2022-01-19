package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.exceptions.ZeitraumException;

/**
 * Bei dieser Klasse handelt es sich um den "Leaf" des Composite Patterns.
 * Weiters handelt es sich hier um eine abstrakte Klasse, die von den
 * konkreten Klassen Reservierung, Belegung und Stornierung implementiert
 * wird. Grundsaetzlich beinhaltet diese Klasse die drei unten definierten
 * Variablen, da zu jeder Aktion der Zeitpunkt dieser Aktion festgehalten
 * wird (also wann [Datum und Zeitpunkt] wurde eine Reservierung
 * durchgefuehrt usw.)
 */
public abstract class Aktion extends Component {
    private LocalDate aktionsDatum;
    private LocalTime aktionsZeitpunkt;
    private DateTimeFormatter datumsFormatierer;

    public Aktion(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt) throws ZeitraumException {
    	if ((aktionsDatum == null) || (aktionsZeitpunkt == null))
			throw new ZeitraumException("Das Datum bzw. die Uhrzeit der durchgefuehrten Aktion ist ungueltig!");
    	
        this.aktionsDatum = aktionsDatum;
        this.aktionsZeitpunkt = aktionsZeitpunkt;
        datumsFormatierer = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    // alternativer Konstruktor, im Falle einer gewuenschten Abweichung vom Standard-Datumsformat
    public Aktion(LocalDate aktionsDatum, LocalTime aktionsZeitpunkt, DateTimeFormatter formatierer) throws ZeitraumException {
    	this(aktionsDatum, aktionsZeitpunkt);
    	
        if (formatierer != null) datumsFormatierer = formatierer;
    }

    public LocalDate getAktionsDatum() {
        return aktionsDatum;
    }

    public void setAktionsDatum(LocalDate aktionsDatum) throws ZeitraumException {
    	if (aktionsDatum == null)
    		throw new ZeitraumException("Das Datum der durchgefuehrten Aktion ist ungueltig!");
    	
        this.aktionsDatum = aktionsDatum;
    }

    public LocalTime getAktionsZeitpunkt() {
        return aktionsZeitpunkt;
    }

    public void setAktionsZeitpunkt(LocalTime aktionsZeitpunkt) throws ZeitraumException {
    	if (aktionsZeitpunkt == null)
    		throw new ZeitraumException("Die Uhrzeit der durchgefuehrten Aktion ist ungueltig!");
    	
        this.aktionsZeitpunkt = aktionsZeitpunkt;
    }

    public DateTimeFormatter getDatumsFormatierer() {
        return datumsFormatierer;
    }

    public void setDatumsFormatierer(DateTimeFormatter datumsFormatierer) {
    	if (datumsFormatierer != null)
    		this.datumsFormatierer = datumsFormatierer;
    }

    /**
     * Nachfolgend wird in der Methode ueberprueft, ob sich das Protokoll (also die jeweilige getaetigte
     * Aktion in einem Lernplatz) im uebergebenen Zeitraum befindet. Ist dies der Fall, dann wird
     * die Methode "printProtokoll()" der jeweiligen Aktion aufgerufen. Somit kann die Methode in
     * dieser abstrakten Klasse implementiert werden, da diese nur die individuellen
     * "printProtokoll()"-Methoden aufruft und sich auch bei den konkreten Klassen nicht aendert.
     * @param zeitraum
     * @return
     */
    @Override
    public String printProtokollImZeitraum(Zeitraum zeitraum) {
        if(aktionsDatum.compareTo(zeitraum.getStartDatum()) >=0 && aktionsDatum.compareTo(zeitraum.getEndDatum()) <= 0) return printProtokoll();
        return "";
    }
}
