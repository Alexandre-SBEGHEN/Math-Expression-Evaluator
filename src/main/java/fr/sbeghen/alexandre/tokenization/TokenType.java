package fr.sbeghen.alexandre.tokenization;

/**
 * Représente les différents types qu'un tokenization peut prendre.
 *
 * @see Token
 */
public enum TokenType {
    LEFT("("),
    RIGHT(")"),
    OPERATOR("+-*/"),
    NUMBER("0123456789.");

    public final String characters;
    TokenType(String characters) { this.characters = characters; }
}
