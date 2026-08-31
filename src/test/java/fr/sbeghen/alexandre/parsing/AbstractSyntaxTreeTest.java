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
    @Disabled
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.0, 6.7})
    void applyOperationDivisionByZero(double dividend) {
        assertThrows(DivisionByZeroException.class, () -> AbstractSyntaxTree.applyOperation(Operation.DIV, dividend, 0));
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée
     * dans le cas où l'opération est une division
     * et le diviseur est non nul.
     *
     * @param dividend Dividende
     * @param divisor Diviseur (non nul)
     */
    @Disabled
    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0",
            "1.0, 1.1",
            "10.0, 2.0"
    })
    void applyOperationDivisionNotByZero(double dividend, double divisor) {
        assertDoesNotThrow(() -> AbstractSyntaxTree.applyOperation(Operation.DIV, dividend, divisor));
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
    @ParameterizedTest
    @CsvSource({
            "4.6, PLUS, 62.4, 67.0",
            "70.0, MINUS, 3.0, 67.0",
            "3.0, TIMES, 3.0, 9.0",
            "5.0, DIV, 2.0, 2.5"
    })
    void applyOperationTest(double a, Operation operation, double b, double expectedResult) {
        assertEquals(expectedResult, AbstractSyntaxTree.applyOperation(operation, a, b));
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
    @Disabled
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.0, 6.7})
    void evaluateDivisionByZero(double dividend) {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(
                new Token(TokenType.OPERATION, (double) Operation.DIV.ordinal()),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, dividend)),
                new AbstractSyntaxTree(new Token(TokenType.NUMBER, 0.0))
        );
        assertThrows(DivisionByZeroException.class, ast::evaluate);
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée
     * dans le cas où le parcours mène à une division
     * qui est valide (diviseur non nul).
     *
     * @param dividend Dividende
     * @param divisor Diviseur (non nul)
     */
    @Disabled
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
        assertDoesNotThrow(ast::evaluate);
    }
}
