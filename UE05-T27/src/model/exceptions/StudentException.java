package model.exceptions;

public class StudentException extends Exception {
	public StudentException() {
		super("Die Matrikelnummer faengt mit einem \"k\" an, gefolgt von 8 Zeichen");
	}
}
