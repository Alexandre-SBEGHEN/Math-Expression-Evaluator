package fr.sbeghen.alexandre.token;

/**
 * Représente les différents types qu'un token peut prendre.
 *
 * @see Token
 */
public enum TokenType {
    /** Parenthèse ouvrante '(' */
    LEFT,

    /** Parenthèse fermante ')' */
    RIGHT,

    /** Opération (+-* etc) */
    OPERATION,

    /** Nombre réel */
    NUMBER
}
