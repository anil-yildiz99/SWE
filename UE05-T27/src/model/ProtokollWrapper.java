package model;

/**
 * Hierbei handelt es sich um eine konkrete Implementierung der Composite Klasse.
 * Diese konkrete Implementierung gibt den Protokollkopf und -fuß aus.
 */
public class ProtokollWrapper extends ProtokollComposite {

    @Override
    public void printProtokoll() {
        System.out.println("Gesamtprotokoll");
        super.printProtokoll();
        System.out.println("Protokollende");
    }

    @Override
    public void printProtokollImZeitraum(Zeitraum zeitraum) {
        System.out.println("Protokoll im Zeitraum von " + zeitraum.getStartDatum() + " bis " + zeitraum.getEndDatum());
        super.printProtokollImZeitraum(zeitraum);
        System.out.println("Protokollende");
    }
}
