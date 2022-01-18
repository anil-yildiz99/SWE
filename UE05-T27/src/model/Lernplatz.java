package model;

import model.exceptions.InvalidCompositeException;

/**
 * Dies ist eine Implementierung der Composite Klasse. Hierbei handelt
 * es sich um die Lernplaetze, die ein Student reservieren, belegen und
 * stornieren kann. Ein Lernplatz hat eine Nummer und eine maximale
 * Kapazitaet
 */
public class Lernplatz extends ProtokollComposite {
    private int nummer;
    private int kapazitaet;

    public Lernplatz(int nummer, int kapazitaet) {
        this.nummer = nummer;
        this.kapazitaet = kapazitaet;
    }

    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    /**
     * Bei der Protokollausgabe wird die Kapazitaet des Lernplatzes ueberprueft.
     * Je nach dem wird die Ausgabe des Protokolls angepasst.
     * @return
     */
    @Override
    public String printProtokoll() {
        String str = "";
        if(kapazitaet ==1){
            //System.out.println("\tLernplatz " + nummer + " fuer " + kapazitaet + " Person");
            str = "\tLernplatz " + nummer + " fuer " + kapazitaet + " Person\n";
        }else{
            //System.out.println("\tLernplatz " + nummer + " fuer " + kapazitaet + " Personen");
            str = "\tLernplatz " + nummer + " fuer " + kapazitaet + " Personen\n";
        }
        str = str + super.printProtokoll();
        return str;
    }

    @Override
    public String printProtokollImZeitraum(Zeitraum zeitraum) {
        String str = "";
        if(kapazitaet ==1){
            //System.out.println("\tLernplatz " + nummer + " fuer " + kapazitaet + " Person");
            str = "\tLernplatz " + nummer + " fuer " + kapazitaet + " Person\n";
        }else{
            //System.out.println("\tLernplatz " + nummer + " fuer " + kapazitaet + " Personen");
            str = "\tLernplatz " + nummer + " fuer " + kapazitaet + " Personen\n";
        }
        str = str + super.printProtokollImZeitraum(zeitraum);
        return str;
    }

    /**
     * Hier wird einem Lernplatz die entsprechenden Aktionen (Reservierung, Belegung, Stornierung)
     * zugewiesen. Diese Aktionen werden in die Liste "protokoll" hinzugefuegt. Dabei wird geprueft,
     * ob auch wirklich Aktionen uebergeben werden. Falls dies nicht der Fall ist, wird eine
     * Exception geworfen.
     * @param comp
     * @return
     */
    @Override
    public boolean add(Component comp) throws InvalidCompositeException {
        if(!(comp instanceof Aktion)) throw new InvalidCompositeException(this, comp);
        return super.add(comp);
    }
}
