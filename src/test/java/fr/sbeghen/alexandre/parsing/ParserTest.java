package fr.sbeghen.alexandre.parsing;

import fr.sbeghen.alexandre.tokenization.Operator;
import fr.sbeghen.alexandre.tokenization.Token;
import fr.sbeghen.alexandre.tokenization.TokenType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    /**
     * Vérifie pour une opération simple, si le bon
     * arbre est construit.
     */
    @Disabled
    @Test
    void constructTreeSimpleOperation() {
        // Construction de l'opération '2 + 3'
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.PLUS.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 3.0));

        Parser parser = new Parser(tokens);

        Tree expectedTree = new Tree(
                new Token(TokenType.OPERATION, Operator.PLUS.ordinal()),
                new Tree(new Token(TokenType.NUMBER, 2.0)),
                new Tree(new Token(TokenType.NUMBER, 3.0))
        );

        assertEquals(expectedTree, parser.constructTree());
    }

    /**
     * Vérifie pour une opération intermédiaire,
     * si le bon arbre est construit.
     */
    @Disabled
    @Test
    void constructTreeIntermediateOperation() {
        // Construction de l'opération '6 / 2 * (1 + 2)'
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.NUMBER, 6.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.DIV.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.TIMES.ordinal()));
        tokens.add(new Token(TokenType.LEFT, 0.0));
        tokens.add(new Token(TokenType.NUMBER, 1.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.PLUS.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.LEFT, 0.0));

        Parser parser = new Parser(tokens);

        Tree expectedTree = new Tree(
                new Token(TokenType.OPERATION, Operator.PLUS.ordinal()),
                new Tree(
                        new Token(TokenType.OPERATION, Operator.DIV.ordinal()),
                        new Tree(new Token(TokenType.NUMBER, 6.0)),
                        new Tree(new Token(TokenType.NUMBER, 2.0))
                ),
                new Tree(
                        new Token(TokenType.OPERATION, Operator.PLUS.ordinal()),
                        new Tree(new Token(TokenType.NUMBER, 1.0)),
                        new Tree(new Token(TokenType.NUMBER, 2.0))
                )
        );

        assertEquals(expectedTree, parser.constructTree());
    }

    /**
     * Vérifie pour une opération complexe,
     * si le bon arbre est construit.
     */
    @Disabled
    @Test
    void constructTreeComplexOperation() {
        // Construction de l'opération '-6 / 4 + 1 --2 * 3'
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.OPERATION, Operator.NEGATE.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 6.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.DIV.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 4.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.PLUS.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 1.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.MINUS.ordinal()));
        tokens.add(new Token(TokenType.OPERATION, Operator.NEGATE.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 2.0));
        tokens.add(new Token(TokenType.OPERATION, Operator.TIMES.ordinal()));
        tokens.add(new Token(TokenType.NUMBER, 3.0));

        Parser parser = new Parser(tokens);

        // Une infamie sans nom
        Tree expectedTree = new Tree(
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

        assertEquals(expectedTree, parser.constructTree());
    }
}
