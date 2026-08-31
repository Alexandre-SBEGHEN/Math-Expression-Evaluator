package fr.sbeghen.alexandre.tokenization;

/**
 * Enregistre un tokenization avec son type (nombre / opération) ainsi que sa valeur.
 *
 * @param type Type du tokenization.
 * @param value Valeur du tokenization. Peut être un enum Operation casté en double.
 */
public record Token(TokenType type, double value) {}
