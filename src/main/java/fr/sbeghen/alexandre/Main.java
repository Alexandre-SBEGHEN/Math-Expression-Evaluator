package fr.sbeghen.alexandre;

import fr.sbeghen.alexandre.tokenization.*;
import java.util.ArrayList;

/**
 * Point d'entrée du programme.
 */
public class Main {
    static void main(String[] args) {
        Expression exp = new Expression("6 / 2 * (1 + 2)");
        System.out.println("Expression: '" + exp + "'");

        ArrayList<Token> tokens = exp.tokenize();
        System.out.print("Tokens: ( ");
        for (Object t: tokens.toArray())
            System.out.print(((Token)t).type() + "\t");
        System.out.println(")");
    }
}
