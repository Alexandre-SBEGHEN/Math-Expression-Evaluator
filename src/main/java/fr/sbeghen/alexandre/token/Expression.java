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
        }; // Conversion en double et création + ajout du token
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
            // Ignorer si c'est un caractère d'espacement
            boolean isSpace = Character.isWhitespace(c);
            if (isSpace)
                continue;

            // Type de caractère
            boolean isLeft = TokenType.LEFT.characters.indexOf(c) != -1;
            boolean isRight = TokenType.RIGHT.characters.indexOf(c) != -1;
            boolean isOperation = TokenType.OPERATION.characters.indexOf(c) != -1;
            boolean isDigitOrDot = TokenType.NUMBER.characters.indexOf(c) != -1;

            // Ajout des tokens
            if (isDigitOrDot) {
                numberString.append(c);
            } else {
                numberStringCheckAndParseAndClear.run();

                if (isLeft) {
                    ++parenthesesStack;
                    tokens.add(new Token(TokenType.LEFT, 0.0));
                } else if (isRight) {
                    if (--parenthesesStack < 0)
                        throw new BadParenthesesException("Closing a parenthesis before opening it");
                    tokens.add(new Token(TokenType.RIGHT, 0.0));
                } else if (isOperation) {
                    Operation operation = Operation.fromChar(c);
                    tokens.add(new Token(TokenType.OPERATION, operation.ordinal()));
                } else {
                    throw new IllegalCharacterException(String.format("Illegal character : '%c'", c));
                }
            }
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
