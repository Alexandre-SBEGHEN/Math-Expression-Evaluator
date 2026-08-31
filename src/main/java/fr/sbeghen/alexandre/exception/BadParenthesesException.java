package fr.sbeghen.alexandre.exception;

/**
 * Sous-exception de ExpressionException.
 * <p>
 * Exception qui a lieu lorsqu'une expression mathématique
 * présente un mauvais parenthésage, la rendant donc invalide.
 *
 * @see ExpressionException
 */
public class BadParenthesesException extends ExpressionException {
    public BadParenthesesException(String message) {
        super(message);
    }
}
