package fr.sbeghen.alexandre.exception;

/**
 * Exception qui a lieu lorsqu'une expression mathématique
 * présente un mauvais parenthésage, la rendant donc invalide.
 */
public class BadParenthesesException extends RuntimeException {
    public BadParenthesesException(String message) {
        super(message);
    }
}
