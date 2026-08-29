package fr.sbeghen.alexandre.exceptions;

/**
 * Exception qui a lieu lorsqu'un caractère non reconnu
 * est présent dans une expression mathématique, la
 * rendant donc invalide.
 */
public class IllegalCharacterException extends RuntimeException {
    public IllegalCharacterException(String message) {
        super(message);
    }
}
