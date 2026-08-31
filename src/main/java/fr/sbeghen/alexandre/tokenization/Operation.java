package fr.sbeghen.alexandre.tokenization;

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

    /**
     * Permet d'obtenir la valeur de l'enum associé au
     * caractère d'une opération.
     *
     * @param opChar Caractère de l'opération.
     * @return La valeur de l'enum.
     *
     * @throws IllegalArgumentException Si un caractère invalide est entré.
     */
    public static Operation fromChar(char opChar) {
        for (Operation op: values())
            if (op.character == opChar)
                return op;
        throw new IllegalArgumentException(String.format("Invalid operator : '%c'", opChar));
    }
}
