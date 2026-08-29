package fr.sbeghen.alexandre.token;

import java.util.ArrayList;
import fr.sbeghen.alexandre.exceptions.BadParenthesesException;
import fr.sbeghen.alexandre.exceptions.IllegalCharacterException;

/**
 * Représente une expression mathématique sous la forme de chaîne de caractères.
 * <p>
 * Cette classe encapsule l'expression brute avant qu'elle soit tokénisée évaluée.
 */
public class Expression {
    private final String expression;

    public Expression(String expression) {
        this.expression = expression;
    }

    /**
     * Crée une liste de tokens à partir de l'expression mathématique.
     *
     * @return La liste des tokens.
     *
     * @throws fr.sbeghen.alexandre.exceptions.BadParenthesesException Si l'expression
     * présente un mauvais parenthésage.
     * @throws fr.sbeghen.alexandre.exceptions.IllegalCharacterException Si l'expression
     * contient un ou plusieurs caractères invalides.
     */
    public ArrayList<Token> tokenize() throws BadParenthesesException, IllegalCharacterException {
        ArrayList<Token> tokens = new ArrayList<>();

        int parentheses = 0;

        // Parcours des caractères
        for (char c: expression.toCharArray()) {
            // Ignorer si c'est un caractère d'espacement
            boolean isSpace = Character.isWhitespace(c);
            if (isSpace)
                continue;

            // Type de caractère
            boolean isLeft = TokenType.LEFT.characters.indexOf(c) != -1;
            boolean isRight = TokenType.RIGHT.characters.indexOf(c) != -1;
            boolean isOperation = TokenType.OPERATION.characters.indexOf(c) != -1;
            boolean isNumber = TokenType.NUMBER.characters.indexOf(c) != -1;

            if (isLeft) {
                ++parentheses;
            } else if (isRight) {
                if (--parentheses < 0)
                    throw new BadParenthesesException("Closing a parenthesis before opening it");
            } else if (isNumber) {

            } else {
                if (isOperation) {

                } else {
                    throw new IllegalCharacterException(String.format("Illegal character : '%c'", c));
                }
            }
        }

        // Vérifier que le parenthésage est nul
        if (parentheses != 0)
            throw new BadParenthesesException("Parentheses not closed");

        return tokens;
    }

    @Override
    public String toString() {
        return expression;
    }
}
