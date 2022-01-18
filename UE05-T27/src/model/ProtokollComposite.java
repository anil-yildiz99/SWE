package model;

import model.exceptions.InvalidCompositeException;

import java.util.ArrayList;
import java.util.List;

/**
 * Hierbei handelt es sich um das Composite des Composite Patterns. Hier
 * sind Unterobjekte (in der Variable "protokoll") enthalten, welche
 * Blaetter sind oder auch selbst wieder zusammengesetzt sein koennen.
 */
public abstract class ProtokollComposite extends Component {
    private List<Component> protokoll = new ArrayList<>();

    @Override
    public String printProtokoll() {
        String str = "";
        for(Component c : protokoll){
            str = str + c.printProtokoll();
        }
        return str;
    }

    @Override
    public String printProtokollImZeitraum(Zeitraum zeitraum) {
        String str = "";
        for(Component c : protokoll){
            str = str + c.printProtokollImZeitraum(zeitraum);
        }
        return str;
    }

    /**
     * Die folgenden beiden Methoden dienen dazu, um der Liste "protokoll" Werte
     * (also Composites wie z.B. Lernzonen usw. bzw. Leafs wie z.B. Stornierungen
     * usw.) hinzufuegen bzw. entfernen zu koennen.
     */
    public boolean add(Component comp) throws InvalidCompositeException {
        return this.protokoll.add(comp);
    }

    public boolean delete(Component comp) {
        return this.protokoll.remove(comp);
    }

    public List<Component> getProtokoll() {
        return protokoll;
    }
}
