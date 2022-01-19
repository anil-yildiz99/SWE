package model;

import java.time.LocalDate;

import model.exceptions.ZeitraumException;

/**
 * Dies ist eine Hilfsklasse fuer die Ausgabe des Protokolls in einem bestimmten Zeitraum,
 * die von der Methode "printProtokollImZeitraum(Zeitraum zeitraum)" verwendet wird.
 */
public class Zeitraum {

	private LocalDate startDatum;
	private LocalDate endDatum;

	public Zeitraum(LocalDate startDatum, LocalDate endDatum) throws ZeitraumException {
		if ((startDatum == null) || (endDatum == null) || (startDatum.isAfter(endDatum)))
			throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
		
		this.startDatum = startDatum;
		this.endDatum = endDatum;
	}

	public void setStartDatum(LocalDate startDatum) throws ZeitraumException {
		if ((startDatum == null) || startDatum.isAfter(endDatum))
			throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
		
		this.startDatum = startDatum;
	}

	public void setEndDatum(LocalDate endDatum) throws ZeitraumException {
		if ((endDatum == null) || endDatum.isBefore(startDatum))
			throw new ZeitraumException("Der eingegebene Zeitraum ist ungueltig!");
		
		this.endDatum = endDatum;
	}

	public LocalDate getStartDatum() {
		return startDatum;
	}

	public LocalDate getEndDatum() {
		return endDatum;
	}

}
