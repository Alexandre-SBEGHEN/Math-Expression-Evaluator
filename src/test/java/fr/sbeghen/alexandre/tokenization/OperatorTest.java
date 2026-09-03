package fr.sbeghen.alexandre.tokenization;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de Operator.
 * <p>
 * <strong>Note :</strong> ne prend pas en compte les méthodes triviales
 * telles que values, ordinal, etc. Seulement les
 * implémentations propres au programme.
 *
 * @see Operator
 */
public class OperatorTest {

    /**
     * Vérifie qu'une exception IllegalArgumentException
     * est bien levée dans le cas où un caractère
     * invalide est entré.
     *
     * @param opChar Caractère invalide à vérifier.
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(chars = {'a', '@', '=', '°'})
    void fromCharInvalid(char opChar) {
        assertThrows(IllegalArgumentException.class, () -> Operator.fromChar(opChar));
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée dans le cas
     * où un caractère valide est entré, et vérifie que la bonne
     * valeur de l'enum est obtenue.
     *
     * @param opChar Caractère valide à vérifier.
     * @param expectedOp Valeur de l'enum attendue.
     *
     */
    // @Disabled
    @ParameterizedTest
    @CsvSource({
            "+, PLUS",
            "-, MINUS",
            "*, TIMES",
            "/, DIV"
    })
    void fromCharValid(char opChar, Operator expectedOp) {
        assertDoesNotThrow(() -> Operator.fromChar(opChar));
        assertEquals(expectedOp, Operator.fromChar(opChar));
    }
}
