package fr.sbeghen.alexandre.tokenization;

import fr.sbeghen.alexandre.parsing.BindingPower;

/**
 * Représente les opérations arithmétiques supportées par l'évaluateur.
 */
public enum Operation {
    PLUS('+'),
    MINUS('-'), // Moins binaire (a - b)
    NEGATE('-'), // Moins unaire (-a)
    TIMES('*'),
    DIV('/');

    public final char character;
    Operation(char character) { this.character = character; }

    /**
     * Génère le <i>binding power</i> de l'opération.
     *
     * @return Record du binding power.
     *
     * @see BindingPower
     */
    public BindingPower getBindingPower() {
        return switch (this) {
            case PLUS -> new BindingPower(1, 2);
            case MINUS -> new BindingPower(1, 2);
            case NEGATE -> new BindingPower(0, 5); // 0 à gauche car opérateur préfixe
            case TIMES -> new BindingPower(3, 4);
            case DIV -> new BindingPower(3, 4);
        };
    }

    /**
     * Permet d'obtenir la valeur de l'enum associé au
     * caractère d'une opération.
     * <p>
     * <strong>Note :</strong> Etant donné que le caractère '-'
     * correspond à deux opérations à la fois (moins unaire / moins binaire),
     * cette méthode renverra par défaut le moins binaire
     * s'il est passé en paramètre.
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
