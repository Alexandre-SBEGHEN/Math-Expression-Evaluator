package fr.sbeghen.alexandre.exception;

/**
 * Sous-exception de ExpressionException.
 * <p>
 * Exception qui a lieu lorsqu'un caractère invalide
 * est présent dans une expression mathématique, la
 * rendant donc elle-même invalide.
 *
 * @see ExpressionException
 */
public class IllegalCharacterException extends ExpressionException {
    public IllegalCharacterException(String message) {
        super(message);
    }
    public IllegalCharacterException(String message, Throwable cause) {
        super(message, cause);
    }
}
