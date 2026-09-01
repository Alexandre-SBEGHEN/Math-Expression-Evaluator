package fr.sbeghen.alexandre.parsing;

import fr.sbeghen.alexandre.exception.DivisionByZeroException;
import fr.sbeghen.alexandre.tokenization.Expression;
import fr.sbeghen.alexandre.tokenization.Operation;
import fr.sbeghen.alexandre.tokenization.Token;
import fr.sbeghen.alexandre.tokenization.TokenType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de AbstractSyntaxTree.
 * <p>
 * <strong>Note :</strong> ne prend pas en compte les méthodes triviales
 * telles que les guetters, setters, toString, etc. Seulement les
 * implémentations propres au programme.
 *
 * @see AbstractSyntaxTree
 */
public class AbstractSyntaxTreeTest {

    /*
     * -------------------- Tests de constructFromTokens() --------------------
     */

    /**
     * Vérifie pour une opération simple, si le bon
     * arbre est construit.
     */
    @Disabled
    @Test
    void constructFromTokensSimpleOperation() {
        // Construction de l'opération '2 + 3'
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.PLUS.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 3.0));

        AbstractSyntaxTree expectedAst = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, Operation.PLUS.ordinal()),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 2.0)),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 3.0))
        );

        assertEquals(expectedAst, AbstractSyntaxTree.constructFromTokens(tokens));
    }

    /**
     * Vérifie pour une opération intermédiaire,
     * si le bon arbre est construit.
     */
    @Disabled
    @Test
    void constructFromTokensIntermediateOperation() {
        // Construction de l'opération '6 / 2 * (1 + 2)'
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.NUMBER, 6.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.DIV.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.TIMES.ordinal()));
        tokens.add(new Token(TokenType.LEFT, 0.0));
        tokens.add(new Token(TokenType.NUMBER, 1.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.PLUS.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.LEFT, 0.0));

        AbstractSyntaxTree expectedAst = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, Operation.PLUS.ordinal()),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, Operation.DIV.ordinal()),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 6.0)),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 2.0))
                ),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, Operation.PLUS.ordinal()),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 1.0)),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 2.0))
                )
        );

        assertEquals(expectedAst, AbstractSyntaxTree.constructFromTokens(tokens));
    }

    /**
     * Vérifie pour une opération complexe,
     * si le bon arbre est construit.
     */
    @Disabled
    @Test
    void constructFromTokensComplexOperation() {
        // Construction de l'opération '-6 / 4 + 1 --2 * 3'
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.OPERATION, Operation.MINUS_UNARY.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 6.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.DIV.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 4.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.PLUS.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 1.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.MINUS.ordinal()));
        tokens.add(new Token(TokenType.OPERATION, Operation.MINUS_UNARY.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.OPERATION, Operation.TIMES.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 3.0));

        // Une infamie sans nom
        AbstractSyntaxTree expectedAst = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, Operation.MINUS.ordinal()),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, Operation.PLUS.ordinal()),
                        new AbstractSyntaxTree(
                                new Token(TokenType.OPERATION, Operation.DIV.ordinal()),
                                new AbstractSyntaxTree(
                                        new Token(TokenType.OPERATION, Operation.MINUS_UNARY.ordinal()),
                                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 6.0))
                                ),
                                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 4.0))
                        ),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 1.0))
                ),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, Operation.TIMES.ordinal()),
                        new AbstractSyntaxTree(
                                new Token(TokenType.OPERATION, Operation.MINUS_UNARY.ordinal()),
                                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 2.0))
                        ),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 3.0))
                )
        );

        assertEquals(expectedAst, AbstractSyntaxTree.constructFromTokens(tokens));
    }

    /*
     * ---------------------- Tests de applyOperation() ------------------------
     */

    /* ----- Exceptions ----- */

    /**
     * Vérifie que DivisionByZeroException est bien
     * levée dans le cas où l'opération est une
     * division et le diviseur est nul.
     *
     * @param dividend Dividende quelconque
     *
     * @see DivisionByZeroException
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.0, 6.7})
    void applyOperationDivisionByZero(double dividend) {
        assertThrows(
                DivisionByZeroException.class, () -> AbstractSyntaxTree.applyOperation(Operation.DIV, dividend, 0),
                String.format("Failed with %f", dividend)
        );
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée
     * dans le cas où l'opération est une division
     * et le diviseur est non nul.
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
    void applyOperationDivisionNotByZero(double dividend, double divisor) {
        assertDoesNotThrow(
                () -> AbstractSyntaxTree.applyOperation(Operation.DIV, dividend, divisor),
                String.format("Failed with %f / divisor", dividend)
        );
    }

    /* ----- Valeur de retour ----- */

    /**
     * Vérifie si le résultat des opérations
     * correspond avec le résultat attendu.
     *
     * @param a Premier opérande.
     * @param operation Opération à effectuer.
     * @param b Second opérande.
     * @param expectedResult Résultat attendu.
     */
    // @Disabled
    @ParameterizedTest
    @CsvSource({
            "4.6, PLUS, 62.4, 67.0",
            "70.0, MINUS, 3.0, 67.0",
            "3.0, TIMES, 3.0, 9.0",
            "5.0, DIV, 2.0, 2.5"
    })
    void applyOperationTest(double a, Operation operation, double b, double expectedResult) {
        assertEquals(
                expectedResult, AbstractSyntaxTree.applyOperation(operation, a, b),
                String.format("Failed with %f %c %f", a, operation.character, b)
        );
    }

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
        AbstractSyntaxTree ast = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, (double) Operation.DIV.ordinal()),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, dividend)),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 0.0))
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
        AbstractSyntaxTree ast = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, (double) Operation.DIV.ordinal()),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, dividend)),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, divisor))
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
        AbstractSyntaxTree ast = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, (double) Operation.PLUS.ordinal()),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 4.0)),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, (double) Operation.TIMES.ordinal()),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 2.0)),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 3.0))
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
        AbstractSyntaxTree expectedAst = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, Operation.MINUS.ordinal()),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, Operation.PLUS.ordinal()),
                        new AbstractSyntaxTree(
                                new Token(TokenType.OPERATION, Operation.DIV.ordinal()),
                                new AbstractSyntaxTree(
                                        new Token(TokenType.OPERATION, Operation.MINUS_UNARY.ordinal()),
                                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 6.0))
                                ),
                                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 4.0))
                        ),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 1.0))
                ),
                new AbstractSyntaxTree(
                        new Token(TokenType.OPERATION, Operation.TIMES.ordinal()),
                        new AbstractSyntaxTree(
                                new Token(TokenType.OPERATION, Operation.MINUS_UNARY.ordinal()),
                                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 2.0))
                        ),
                        new AbstractSyntaxTree(new Token(TokenType.NUMBER, 3.0))
                )
        );
        assertEquals(5.5, expectedAst.evaluate());
    }

}
