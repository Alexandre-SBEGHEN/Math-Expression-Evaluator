package fr.sbeghen.alexandre.token;

/**
 * Représente les opérations arithmétiques supportées par l'évaluateur.
 */
public enum Operation {
    /** Addition (+) */
    PLUS,

    /** Soustraction (-) */
    MINUS,

    /** Multipication (*) */
    TIMES,

    /** Division (/). Lève une exception si le diviseur vaut zéro. */
    DIV
}
