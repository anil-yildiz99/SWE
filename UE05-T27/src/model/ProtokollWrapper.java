package model;

import model.exceptions.InvalidCompositeException;

/**
 * Hierbei handelt es sich um eine konkrete Implementierung der Composite Klasse.
 * Diese konkrete Implementierung gibt den Protokollkopf und -fuß aus.
 */
public class ProtokollWrapper extends ProtokollComposite {

    @Override
    public String printProtokoll() {
        String str = "Gesamtprotokoll\n";
        str = str + super.printProtokoll();
        str = str + "Protokollende";
        return str;
    }

    @Override
    public String printProtokollImZeitraum(Zeitraum zeitraum) {
        String str = "Protokoll im Zeitraum von " + zeitraum.getStartDatum() + " bis " + zeitraum.getEndDatum() +"\n";
        str = str + super.printProtokollImZeitraum(zeitraum);
        str = str + "Protokollende";
        return str;
    }

    @Override
    public boolean add(Component comp) throws InvalidCompositeException {
        if(!(comp instanceof Lernzone)) throw new InvalidCompositeException(this, comp);
        return super.add(comp);
    }
}
