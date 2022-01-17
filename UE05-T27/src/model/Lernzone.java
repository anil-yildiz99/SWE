package model;

import model.exceptions.InvalidCompositeException;

/**
 * Bei dieser Klasse handelt es sich um eine konkrete Implementierung der Klasse
 * "ProtokollComposite", welche die Lernzonen (in denen sich die Lernplaetze
 * befinden) behandelt. Eine Lernzone hat einen Lernzonennamen.
 */
public class Lernzone extends ProtokollComposite {
    private String name;

    public Lernzone(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void printProtokoll() {
        System.out.println("Lernzone: " + name);
        super.printProtokoll();
    }

    @Override
    public void printProtokollImZeitraum(Zeitraum zeitraum) {
        System.out.println("Lernzone: " + name);
        super.printProtokollImZeitraum(zeitraum);
    }

    /**
     * Hier wird einer Lernzone die entsprechenden Lernplaetze zugewiesen. Diese Lernplaetze werden in die
     * Liste "protokoll" hinzugefuegt. Dabei wird geprueft, ob auch wirklich Lernplaetze uebergeben werden.
     * Falls dies nicht der Fall ist, wird eine Exception geworfen.
     * @param comp
     * @return
     */
    @Override
    public boolean add(Component comp) {
        try {
            if(!(comp instanceof Lernplatz)) throw new InvalidCompositeException(this, comp);
            return super.add(comp);
        } catch(InvalidCompositeException ex){
            System.err.println(ex.getMessage());
        }
        return false;
    }
}
