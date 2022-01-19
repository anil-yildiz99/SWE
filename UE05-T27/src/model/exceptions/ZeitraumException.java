package model.exceptions;

public class ZeitraumException extends Exception {
	public ZeitraumException(String message) {
		super(message + " Ueberpruefen Sie bitte Ihre Eingaben.");
	}
}
