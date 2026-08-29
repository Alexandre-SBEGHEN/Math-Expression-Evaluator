package fr.sbeghen.alexandre.exceptions;

/**
 * Exception qui a lieu lorsqu'un caractère invalide
 * est présent dans une expression mathématique, la
 * rendant donc elle-même invalide.
 */
public class IllegalCharacterException extends RuntimeException {
    public IllegalCharacterException(String message) {
        super(message);
    }
}
