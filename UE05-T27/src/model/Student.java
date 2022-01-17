package model;

import model.exceptions.StudentException;

/**
 * Diese Klasse ist nicht dem Composite Pattern zugeordnet. Es handelt sich lediglich
 * um eine Klasse, die Studentinstanzen beinhaltet.
 */
public class Student {
    private final String matrikelnummer;

    public Student(String matrikelnummer) throws StudentException {
    	if ((matrikelnummer.length() != 9) || ((matrikelnummer.charAt(0) != 107) && (matrikelnummer.charAt(0) != 75))) 
    		throw new StudentException();
    	for (int i=1; i < matrikelnummer.length(); i++) {
    		if ((matrikelnummer.charAt(i) < 48) || (matrikelnummer.charAt(i) > 57))
    			throw new StudentException();
    	}
    	
        this.matrikelnummer = matrikelnummer;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

}
