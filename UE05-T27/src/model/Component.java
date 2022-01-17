package model;

/**
 * Dies ist die Component Klasse des Composite Patterns. Hier wird die Schnittstelle
 * bereitgestellt (also in diesem Fall die beiden print Methoden), welche die konkreten
 * Klassen (also die Leaf und Composite Klassen) implementieren
 */
public abstract class Component {
    public abstract String printProtokoll();
    public abstract String printProtokollImZeitraum(Zeitraum zeitraum);
}
