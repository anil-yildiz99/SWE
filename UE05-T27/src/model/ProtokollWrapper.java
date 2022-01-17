package model;

/**
 * Hierbei handelt es sich um eine konkrete Implementierung der Composite Klasse.
 * Diese konkrete Implementierung gibt den Protokollkopf und -fuß aus.
 */
public class ProtokollWrapper extends ProtokollComposite {

    @Override
    public String printProtokoll() {
        //System.out.println("Gesamtprotokoll");
        //super.printProtokoll();
        //System.out.println("Protokollende");

        String str = "Gesamtprotokoll\n";
        str = str + super.printProtokoll();
        str = str + "Protokollende";
        return str;
    }

    @Override
    public String printProtokollImZeitraum(Zeitraum zeitraum) {
        //System.out.println("Protokoll im Zeitraum von " + zeitraum.getStartDatum() + " bis " + zeitraum.getEndDatum());
        //super.printProtokollImZeitraum(zeitraum);
        //System.out.println("Protokollende");

        String str = "Protokoll im Zeitraum von " + zeitraum.getStartDatum() + " bis " + zeitraum.getEndDatum() +"\n";
        str = str + super.printProtokollImZeitraum(zeitraum);
        str = str + "Protokollende";
        return str;
    }
}
