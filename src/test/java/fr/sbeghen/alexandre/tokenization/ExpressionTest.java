package fr.sbeghen.alexandre.tokenization;

import fr.sbeghen.alexandre.exception.ExpressionException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import fr.sbeghen.alexandre.exception.BadParenthesesException;
import fr.sbeghen.alexandre.exception.IllegalCharacterException;

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

    /*
     * ----------------------- Tests de tokenize() ----------------------------
     */

    /* ----- Exceptions ----- */

    /**
     * Vérifie que l'exception BadParenthesesException est bien levée
     * dans le cas où un mauvais parenthésage est présent dans
     * l'expression mathématique.
     *
     * @param str La chaîne de l'expression dont on vérifie le parenthésage.
     *
     * @see BadParenthesesException
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"(", ")", ")(", "(()", "())"})
    void tokenizeBadParentheses(String str) {
        Expression exp = new Expression(str);
        assertThrows(BadParenthesesException.class, exp::tokenize, String.format("Failed with '%s'", str));
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée dans le cas où
     * l'expression mathématique présente un parenthésage correct.
     *
     * @param str La chaîne de l'expression dont on vérifie le parenthésage.
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"", "()", "()()", "(())", "(()())"})
    void tokenizeGoodParentheses(String str) {
        Expression exp = new Expression(str);
        assertDoesNotThrow(exp::tokenize, String.format("Failed with '%s'", str));
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
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"3 ° 2", "5 * a", "6 * [4 - (1 + 2)]"})
    void tokenizeIllegalCharacters(String str) {
        Expression exp = new Expression(str);
        assertThrows(IllegalCharacterException.class, exp::tokenize, String.format("Failed with '%s'", str));
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
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"6/2*(1+2)", "1   +     4", "(3)*(    3    )"})
    void tokenizeNoIllegalCharacters(String str) {
        Expression exp = new Expression(str);
        assertDoesNotThrow(exp::tokenize, String.format("Failed with '%s'", str));
    }

    /**
     * Vérifie que l'exception NumberFormatException est bien levée
     * dans le cas où le nombre présent dans l'expression est dans
     * le mauvais format.
     *
     * @param str Chaîne de l'expression à vérifier
     *
     * @see NumberFormatException
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"1 + .", "0.", "1 + ..", "0..0", ".00."})
    void tokenizeBadNumberFormat(String str) {
        Expression exp = new Expression(str);
        assertThrows(NumberFormatException.class, exp::tokenize, String.format("Failed with '%s'", str));
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée dans le cas où
     * où le nombre présent dans l'expression est dans le
     * bon format.
     *
     * @param str Chaîne de l'expression à vérifier
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"0", ".0 + 1", ".00", "00.0 + 1"})
    void tokenizeGoodNumberFormat(String str) {
        Expression exp = new Expression(str);
        assertDoesNotThrow(exp::tokenize, String.format("Failed with '%s'", str));
    }

    /* ----- Valeur de retour ----- */

    /**
     * Vérifie qu'une chaîne sans expression mathématique
     * (chaîne vide OU caractères d'espacement uniquement)
     * renvoie une liste vide.
     *
     * @param str La chaîne sans expression
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    "})
    void tokenizeEmptyExpression(String str) throws ExpressionException {
        Expression exp = new Expression(str);

        assertTrue(exp.tokenize().isEmpty(), String.format("Failed with '%s'", str));
    }

    /**
     * Vérifie qu'une chaîne contenant une expression avec un nombre
     * entier écrit avec des espaces au milieu renvoit une liste
     * avec seulement ce nombre.
     *
     * @param expressionStr La chaîne de l'expression.
     * @param expectedNumber Le nombre attendu.
     */
    // @Disabled
    @ParameterizedTest
    @CsvSource({
            "6 7, 67.0",
            "1 2    3, 123.0",
            " 000 00   , 0.0",
            "0010, 10.0"
    })
    void tokenizeOnlyAnIntNumber(String expressionStr, double expectedNumber) throws ExpressionException {
        Expression exp = new Expression(expressionStr);

        ArrayList<Token> expectedToken = new ArrayList<>();
        expectedToken.add(new Token(TokenType.NUMBER, expectedNumber));

        assertArrayEquals(
                expectedToken.toArray(),
                exp.tokenize().toArray(),
                String.format(
                        "Failed with '%s', expected %f, got %f",
                        expressionStr,
                        expectedNumber,
                        exp.tokenize().getFirst().value()
                )
        );
    }

    /**
     * Vérifie qu'une chaîne contenant une expression avec un nombre
     * décimal écrit avec des espaces au milieu renvoit une liste
     * avec seulement ce nombre.
     *
     * @param expressionStr La chaîne de l'expression.
     * @param expectedNumber Le nombre attendu.
     */
    // @Disabled
    @ParameterizedTest
    @CsvSource({
            "9 .    5, 9.5",
            "00.0125, 0.0125",
            ".00, 0.0",
            "1.0000, 1.0"
    })
    void tokenizeOnlyADecimalNumber(String expressionStr, double expectedNumber) throws ExpressionException {
        Expression exp = new Expression(expressionStr);

        ArrayList<Token> expectedToken = new ArrayList<>();
        expectedToken.add(new Token(TokenType.NUMBER, expectedNumber));

        assertArrayEquals(
                expectedToken.toArray(),
                exp.tokenize().toArray(),
                String.format(
                        "Failed with '%s', expected %f, got %f",
                        expressionStr,
                        expectedNumber,
                        exp.tokenize().getFirst().value()
                )
        );
    }

    /**
     * Vérifie qu'une chaîne contenant une expression avec un nombre
     * décimal commençant par le point (ex: .5 au lieu de 0.5) renvoit
     * le bon nombre.
     *
     * @param expressionStr La chaîne de l'expression.
     * @param expectedNumber Le nombre attendu.
     */
    // @Disabled
    @ParameterizedTest
    @CsvSource({
            ".25, 0.25",
            ".0, 0.0",
            ".0000, 0.0"
    })
    void tokenizeDecimalStartingWithDecimalPoint(String expressionStr, double expectedNumber) throws ExpressionException {
        Expression exp = new Expression(expressionStr);

        ArrayList<Token> expectedToken = new ArrayList<>();
        expectedToken.add(new Token(TokenType.NUMBER, expectedNumber));

        assertArrayEquals(
                expectedToken.toArray(),
                exp.tokenize().toArray(),
                String.format(
                        "Failed with '%s', expected %f, got %f",
                        expressionStr,
                        expectedNumber,
                        exp.tokenize().getFirst().value()
                )
        );
    }

    /**
     * Vérifie que l'expression '4 + 2 * 3' renvoie la bonne liste
     * de tokens.
     */
    // @Disabled
    @Test
    void tokenizeSimpleOperation() throws ExpressionException {
        Expression exp = new Expression("4 + 2 * 3");

        ArrayList<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token(TokenType.NUMBER, 4.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.PLUS.ordinal()));
        expectedTokens.add(new Token(TokenType.NUMBER, 2.0));
        expectedTokens.add(new Token(TokenType.OPERATION, (double)Operation.TIMES.ordinal()));
        expectedTokens.add(new Token(TokenType.NUMBER, 3.0));

        assertArrayEquals(
                expectedTokens.toArray(),
                exp.tokenize().toArray(),
                String.format("Expected %s, got %s", expectedTokens.toString(), exp.tokenize())
        );
    }

    /**
     * Vérifie que l'expression '6 / 2 * (1 + 2)' renvoie la bonne liste
     * de tokens.
     */
    // @Disabled
    @Test
    void tokenizeOperationWithParentheses() throws ExpressionException {
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

        assertArrayEquals(
                expectedTokens.toArray(),
                exp.tokenize().toArray(),
                String.format("Expected %s, got %s", expectedTokens.toString(), exp.tokenize())
        );
    }

    /**
     * Vérifie que l'expression '-5' renvoie un token
     * qui correspond au moins unaire, suivi d'un token
     * qui correspond au nombre 5.
     */
    @Disabled
    @Test
    void tokenizeFiveMinusFive() throws ExpressionException {
        Expression exp = new Expression("-5");

        ArrayList<Token> expectedTokens = new ArrayList<>();
        // expectedTokens.add(new Token(TokenType.OPERATION, (double) Operation.MINUS.ordinal()));

        assertArrayEquals(
                expectedTokens.toArray(),
                exp.tokenize().toArray(),
                String.format("Expected %s, got %s", expectedTokens.toString(), exp.tokenize())
        );
    }
}
