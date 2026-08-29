package fr.sbeghen.alexandre.exceptions;

/**
 * Exception qui a lieu lorsqu'une expression présente
 *  un nombre qui n'est pas écrit dans le bon format,
 *  la rendant donc invalide.
 *  <p>
 *  Exemples :
 *  <ul>
 *      <li>'0.0.1' = <i>incorrect</i></li>
 *      <li>'0.01' = correct</li>
 *      <li>'.0' = correct (0.0)</li>
 *      <li>'.1001.' = <i>incorrect</i></li>
 *  </ul>
 */
public class BadNumberFormatException extends RuntimeException {
    public BadNumberFormatException(String message) {
        super(message);
    }
}
