package fr.sbeghen.alexandre.token;

/**
 * Représente les opérations arithmétiques supportées par l'évaluateur.
 */
public enum Operation {
    PLUS('+'),
    MINUS('-'),
    TIMES('*'),
    DIV('/');

    public final char character;
    Operation(char character) { this.character = character; }

    public static Operation fromChar(char c) {
        for (Operation op: values()) {
            if (op.character == c) {
                return op;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid operator : '%c'", c));
    }
}
