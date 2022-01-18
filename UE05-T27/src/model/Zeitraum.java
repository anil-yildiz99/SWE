package model;

import java.time.LocalDate;

/**
 * Dies ist eine Hilfsklasse fuer die Ausgabe des Protokolls in einem bestimmten Zeitraum,
 * die von der Methode "printProtokollImZeitraum(Zeitraum zeitraum)" verwendet wird.
 */
public class Zeitraum {

	private LocalDate startDatum;
	private LocalDate endDatum;

	public Zeitraum(LocalDate startDatum, LocalDate endDatum) {
		this.startDatum = startDatum;
		this.endDatum = endDatum;
	}

	public void setStartDatum(LocalDate startDatum) {
		this.startDatum = startDatum;
	}

	public void setEndDatum(LocalDate endDatum) {
		this.endDatum = endDatum;
	}

	public LocalDate getStartDatum() {
		return startDatum;
	}

	public LocalDate getEndDatum() {
		return endDatum;
	}

}
