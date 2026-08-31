package fr.sbeghen.alexandre.exception;

/**
 * Exception générale en lien avec
 * les expressions mathématiques.
 */
public class ExpressionException extends Exception {
    public ExpressionException(String message) { super(message); }
    public ExpressionException(String message, Throwable cause) { super(message, cause); }
}
