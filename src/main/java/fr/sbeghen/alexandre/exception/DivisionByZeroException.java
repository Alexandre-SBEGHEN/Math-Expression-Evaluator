package fr.sbeghen.alexandre.exception;

/**
 * Exception qui a lieu lors du parsing d'un AST, lorsqu'une division
 * est tentée, mais que le diviseur est nul.
 */
public class DivisionByZeroException extends ArithmeticException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}
