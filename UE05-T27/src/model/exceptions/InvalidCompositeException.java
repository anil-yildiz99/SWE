package model.exceptions;

import model.Component;

/**
 * Diese Exceptionklasse dient zum Behandeln von ungueltigen Composites.
 */
public class InvalidCompositeException extends Exception{
    public InvalidCompositeException(Component parentComp, Component childComp) {
        super("ungueltige Verschachtelung: " + childComp.getClass().getSimpleName() + " kann kein Teil von " + parentComp.getClass().getSimpleName() + " sein");
    }
}
