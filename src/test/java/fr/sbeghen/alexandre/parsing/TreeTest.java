package fr.sbeghen.alexandre.parsing;

import fr.sbeghen.alexandre.exception.DivisionByZeroException;
import fr.sbeghen.alexandre.tokenization.Operator;
import fr.sbeghen.alexandre.tokenization.Token;
import fr.sbeghen.alexandre.tokenization.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de AbstractSyntaxTree.
 * <p>
 * <strong>Note :</strong> ne prend pas en compte les méthodes triviales
 * telles que les guetters, setters, toString, etc. Seulement les
 * implémentations propres au programme.
 *
 * @see Tree
 */
public class TreeTest {

    /*
     * ------------------------- Tests de evaluate() --------------------------
     */

    /* ----- Exceptions ----- */

    /**
     * Vérifie que DivisionByZeroException est bien
     * levée dans le cas où le parcours mène à une
     * division qui est invalide (diviseur nul).
     *
     * @param dividend Dividende quelconque
     *
     * @see DivisionByZeroException
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.0, 6.7})
    void evaluateDivisionByZero(double dividend) {
        Tree ast = new Tree(
                new Token(TokenType.OPERATION, (double) Operator.DIV.ordinal()),
                new Tree(new Token(TokenType.NUMBER, dividend)),
                new Tree(new Token(TokenType.NUMBER, 0.0))
        );
        assertThrows(
                DivisionByZeroException.class, ast::evaluate,
                String.format("Failed with %f", dividend)
        );
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée
     * dans le cas où le parcours mène à une division
     * qui est valide (diviseur non nul).
     *
     * @param dividend Dividende
     * @param divisor Diviseur (non nul)
     */
    // @Disabled
    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0",
            "1.0, 1.1",
            "10.0, 2.0"
    })
    void evaluateDivisionNotByZero(double dividend, double divisor) {
        Tree ast = new Tree(
                new Token(TokenType.OPERATION, (double) Operator.DIV.ordinal()),
                new Tree(new Token(TokenType.NUMBER, dividend)),
                new Tree(new Token(TokenType.NUMBER, divisor))
        );
        assertDoesNotThrow(ast::evaluate, String.format("Failed with %f / %f", dividend, divisor));
    }

    /* ----- Valeur de retour ----- */

    /**
     * Vérifie que l'évaluation de l'AST suivant :
     * <pre><code>
     *       +
     *      / \
     *     4   *
     *        / \
     *       2   3
     * </code></pre>
     * représentant l'opération suivante :
     * <pre><code>
     *     4 + 2 * 3
     * </code></pre>
     * renvoie bien le résultat <code>10</code>.
     */
    // @Disabled
    @Test
    void evaluateSimpleAst() {
        Tree ast = new Tree(
                new Token(TokenType.OPERATION, (double) Operator.PLUS.ordinal()),
                new Tree(new Token(TokenType.NUMBER, 4.0)),
                new Tree(
                        new Token(TokenType.OPERATION, (double) Operator.TIMES.ordinal()),
                        new Tree(new Token(TokenType.NUMBER, 2.0)),
                        new Tree(new Token(TokenType.NUMBER, 3.0))
                )
        );
        assertEquals(10, ast.evaluate());
    }

    /**
     * Vérifie que l'évaluation de l'AST suivant :
     * <pre><code>
     *             -
     *            / \
     *           /   \
     *          /     \
     *         /       \
     *        /         \
     *       +           *
     *      / \         / \
     *     ÷   1       -   3
     *    / \         /
     *   -   4       2
     *  /
     * 6
     * </code></pre>
     * représentant l'opération suivante :
     * <pre><code>
     *     -6 / 4 + 1 --2 * 3
     * </code></pre>
     * renvoie bien le résultat <code>5.5</code>.
     */
    // @Disabled
    @Test
    void evaluateComplexAst() {
        Tree expectedAst = new Tree(
                new Token(TokenType.OPERATION, Operator.MINUS.ordinal()),
                new Tree(
                        new Token(TokenType.OPERATION, Operator.PLUS.ordinal()),
                        new Tree(
                                new Token(TokenType.OPERATION, Operator.DIV.ordinal()),
                                new Tree(
                                        new Token(TokenType.OPERATION, Operator.NEGATE.ordinal()),
                                        new Tree(new Token(TokenType.NUMBER, 6.0))
                                ),
                                new Tree(new Token(TokenType.NUMBER, 4.0))
                        ),
                        new Tree(new Token(TokenType.NUMBER, 1.0))
                ),
                new Tree(
                        new Token(TokenType.OPERATION, Operator.TIMES.ordinal()),
                        new Tree(
                                new Token(TokenType.OPERATION, Operator.NEGATE.ordinal()),
                                new Tree(new Token(TokenType.NUMBER, 2.0))
                        ),
                        new Tree(new Token(TokenType.NUMBER, 3.0))
                )
        );
        assertEquals(5.5, expectedAst.evaluate());
    }
}
