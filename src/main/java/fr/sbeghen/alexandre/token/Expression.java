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
        return null;
    }

    @Override
    public String toString() {
        return expression;
    }
}
