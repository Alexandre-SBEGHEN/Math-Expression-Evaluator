package fr.sbeghen.alexandre.token;

/**
 * Enregistre un token avec son type (nombre / opération) ainsi que sa valeur.
 *
 * @param type Type du token.
 * @param value Valeur du token. Peut être un enum Operation casté en double.
 */
public record Token(TokenType type, double value) {}
