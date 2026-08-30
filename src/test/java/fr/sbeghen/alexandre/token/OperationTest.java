package fr.sbeghen.alexandre.token;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de Operation.
 * <p>
 * <strong>Note :</strong> ne prend pas en compte les méthodes triviales
 * telles que values, ordinal, etc. Seulement les
 * implémentations propres au programme.
 *
 * @see Operation
 */
public class OperationTest {

    /**
     * Vérifie qu'une exception IllegalArgumentException
     * est bien levée dans le cas où un caractère
     * invalide est entré.
     *
     * @param c Caractère invalide à vérifier.
     */
    // @Disabled
    @ParameterizedTest
    @ValueSource(chars = {'a', '@', '=', '°'})
    void fromCharInvalid(char c) {
        assertThrows(IllegalArgumentException.class, () -> Operation.fromChar(c));
    }

    /**
     * Vérifie qu'il n'y a pas d'exception levée dans le cas
     * où un caractère valide est entré, et vérifie que la bonne
     * valeur de l'enum est obtenue.
     *
     * @param c Caractère valide à vérifier.
     * @param expected Valeur de l'enum attendue.
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
    void fromCharValid(char c, Operation expected) {
        assertDoesNotThrow(() -> Operation.fromChar(c));
        assertEquals(expected, Operation.fromChar(c));
    }
}
