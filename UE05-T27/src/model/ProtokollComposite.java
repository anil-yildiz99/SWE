package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Hierbei handelt es sich um das Composite des Composite Patterns. Hier
 * sind Unterobjekte (in der Variable "protokoll") enthalten, welche
 * Blaetter sind oder auch selbst wieder zusammengesetzt sein koennen.
 */
public abstract class ProtokollComposite extends Component {
    List<Component> protokoll = new ArrayList<>();

    @Override
    public void printProtokoll() {
        for(Component c : protokoll){
            c.printProtokoll();
        }
    }

    @Override
    public void printProtokollImZeitraum(Zeitraum zeitraum) {
        for(Component c : protokoll){
            c.printProtokollImZeitraum(zeitraum);
        }
    }

    /**
     * Die folgenden beiden Methoden dienen dazu, um der Liste "protokoll" Werte
     * (also Composites wie z.B. Lernzonen usw. bzw. Leafs wie z.B. Stornierungen
     * usw.) hinzufuegen bzw. entfernen zu koennen.
     */
    public boolean add(Component comp){
        return this.protokoll.add(comp);
    }

    public boolean delete(Component comp) {
        return this.protokoll.remove(comp);
    }
}
