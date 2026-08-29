package fr.sbeghen.alexandre.token;

/**
 * Représente les différents types qu'un token peut prendre.
 *
 * @see Token
 */
public enum TokenType {
    LEFT("("),
    RIGHT(")"),
    OPERATION("+-*/"),
    NUMBER("0123456789.");

    public final String characters;
    TokenType(String characters) { this.characters = characters; }
}
