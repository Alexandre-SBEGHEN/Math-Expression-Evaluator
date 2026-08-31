package fr.sbeghen.alexandre.tokenization;

import java.util.ArrayList;
import fr.sbeghen.alexandre.exception.BadParenthesesException;
import fr.sbeghen.alexandre.exception.IllegalCharacterException;

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
     * @throws BadParenthesesException Si l'expression
     * présente un mauvais parenthésage.
     * @throws IllegalCharacterException Si l'expression
     * contient un ou plusieurs caractères invalides.
     */
    public ArrayList<Token> tokenize() throws BadParenthesesException, IllegalCharacterException {
        ArrayList<Token> tokens = new ArrayList<>();

        int parenthesesStack = 0;

        // String du nombre lu (concaténations successives)
        StringBuilder numberString = new StringBuilder();
        Runnable numberStringCheckMatchOrThrow = () -> {
            if (!numberString.toString().matches("\\d*\\.?\\d+"))
                throw new NumberFormatException(String.format("'%s' does not correspond to a number", numberString));
        }; // Vérification regex, sinon NumberFormatException
        Runnable numberStringParseAndAddToTokens = () -> {
            double number = Double.parseDouble(numberString.toString());
            tokens.add(new Token(TokenType.NUMBER, number));
        }; // Conversion en double et création + ajout du tokenization
        Runnable numberStringClear = () -> {
            numberString.setLength(0);
        }; // Vider le stringBuilder
        Runnable numberStringCheckAndParseAndClear = () -> {
            if (!numberString.isEmpty()) {
                numberStringCheckMatchOrThrow.run();
                numberStringParseAndAddToTokens.run();
                numberStringClear.run();
            }
        }; // Vérification + Conversion + Vidage

        // Parcours des caractères
        for (char c: expression.toCharArray()) {
            // Caractère espacement -> ignorer
            boolean isSpace = Character.isWhitespace(c);
            if (isSpace)
                continue;

            // Digit -> ajouter au numberString, SINON -> parser numberString
            boolean isDigitOrDot = TokenType.NUMBER.characters.indexOf(c) != -1;
            if (isDigitOrDot) {
                numberString.append(c);
                continue;
            }
            numberStringCheckAndParseAndClear.run();

            // '(' -> empiler
            boolean isLeft = TokenType.LEFT.characters.indexOf(c) != -1;
            if (isLeft) {
                ++parenthesesStack;
                tokens.add(new Token(TokenType.LEFT, 0.0));
                continue;
            }

            // ')' -> dépiler
            boolean isRight = TokenType.RIGHT.characters.indexOf(c) != -1;
            if (isRight) {
                if (--parenthesesStack < 0)
                    throw new BadParenthesesException("Closing a parenthesis before opening it");
                tokens.add(new Token(TokenType.RIGHT, 0.0));
                continue;
            }

            // Opération
            boolean isOperation = TokenType.OPERATION.characters.indexOf(c) != -1;
            if (isOperation) {
                Operation operation = Operation.fromChar(c);
                tokens.add(new Token(TokenType.OPERATION, operation.ordinal()));
                continue;
            }

            // Aucun des cas ci-dessus -> IllegalCharacterException
            throw new IllegalCharacterException(String.format("Illegal character : '%c'", c));
        }

        // Dernière conversion en nombre si nécessaire
        numberStringCheckAndParseAndClear.run();

        // Vérifier que le parenthésage est nul
        if (parenthesesStack != 0)
            throw new BadParenthesesException("Parentheses not closed");

        return tokens;
    }

    @Override
    public String toString() {
        return expression;
    }
}
