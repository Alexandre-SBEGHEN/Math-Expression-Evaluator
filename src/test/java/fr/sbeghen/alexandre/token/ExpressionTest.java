package fr.sbeghen.alexandre.token;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import fr.sbeghen.alexandre.exceptions.BadParenthesesException;
import fr.sbeghen.alexandre.exceptions.IllegalCharacterException;
import java.util.ArrayList;

/**
 * Classe de test de Expression.
 * <p>
 * <strong>Note :</strong> ne prend pas en compte les méthodes triviales
 * telles que les guetters, setters, toString, etc. Seulement les
 * implémentations propres au programme.
 *
 * @see Expression
 */
public class ExpressionTest {

    /* ----- Tests liés aux exceptions ------------------------------------- */

    /**
     * Vérifie que l'exception BadParenthesesException est bien levée
     * dans le cas où un mauvais parenthésage est présent dans
     * l'expression mathématique.
     *
     * @param str La chaîne de l'expression dont on vérifie le parenthésage.
     *
     * @see BadParenthesesException
     */
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"(", ")", ")(", "(()", "())"})
    void tokenizeBadParentheses(String str) {
        Expression exp = new Expression(str);
        assertThrows(BadParenthesesException.class, exp::tokenize);
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée dans le cas où
     * l'expression mathématique présente un parenthésage correct.
     *
     * @param str La chaîne de l'expression dont on vérifie le parenthésage.
     */
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"", "()", "()()", "(())", "(()())"})
    void tokenizeGoodParentheses(String str) {
        Expression exp = new Expression(str);
        assertDoesNotThrow(exp::tokenize);
    }

    /**
     * Vérifie que l'exception IllegalCharacterException est bien levée
     * dans le cas où un ou plusieurs caractères invalides sont présents
     * dans l'expression mathématique.
     * <p>
     * <strong>Note :</strong> Les caractères d'espacement (Espace, Tab)
     * ne sont pas condidérés comme des caractères invalides et sont ignorés.
     *
     * @param str La chaîne de l'expression dont on vérifie les caractères.
     *
     * @see IllegalCharacterException
     */
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"3 ° 2", "5 * a", "6 * [4 - (1 + 2)]"})
    void tokenizeIllegalCharacters(String str) {
        Expression exp = new Expression(str);
        assertThrows(IllegalCharacterException.class, exp::tokenize);
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée dans le cas où
     * l'expression mathématique présente des caractères valides.
     * <p>
     * <strong>Note :</strong> Les caractères d'espacement (Espace, Tab)
     * ne sont pas condidérés comme des caractères invalides et sont ignorés.
     *
     * @param str La chaîne de l'expression dont on vérifie les caractères.
     */
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"6/2*(1+2)", "1   +     4", "(3)*(    3    )"})
    void tokenizeNoIllegalCharacters(String str) {
        Expression exp = new Expression(str);
        assertDoesNotThrow(exp::tokenize);
    }

    /* ----- Tests liés à la valeur de retour ------------------------------ */

    /**
     * Vérifie que l'expression '4 + 2 * 3' renvoie la bonne liste
     * de tokens.
     */
    @Disabled
    @Test
    void tokenizeSimpleOperation() {
        Expression exp = new Expression("4 + 2 * 3");

        ArrayList<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token(TokenType.NUMBER, 4.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.PLUS.ordinal()));
        expectedTokens.add(new Token(TokenType.NUMBER, 2.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.TIMES.ordinal()));
        expectedTokens.add(new Token(TokenType.NUMBER, 3.0));

        assertArrayEquals(expectedTokens.toArray(), exp.tokenize().toArray());
    }

    /**
     * Vérifie que l'expression '6 / 2 * (1 + 2)' renvoie la bonne liste
     * de tokens.
     */
    @Disabled
    @Test
    void tokenizeOperationWithParentheses() {
        Expression exp = new Expression("6 / 2 * (1 + 2)");

        ArrayList<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token(TokenType.NUMBER, 6.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.DIV.ordinal()));
        expectedTokens.add(new Token(TokenType.NUMBER, 2.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.TIMES.ordinal()));
        expectedTokens.add(new Token(TokenType.LEFT, 0.0));
        expectedTokens.add(new Token(TokenType.NUMBER, 1.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.PLUS.ordinal()));
        expectedTokens.add(new Token(TokenType.NUMBER, 2.0));
        expectedTokens.add(new Token(TokenType.RIGHT, 0.0));

        assertArrayEquals(expectedTokens.toArray(), exp.tokenize().toArray());
    }
}
