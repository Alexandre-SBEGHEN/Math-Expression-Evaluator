package fr.sbeghen.alexandre.parsing;

import java.util.Collections;
import java.util.ArrayList;
import fr.sbeghen.alexandre.tokenization.Token;

/**
 * Classe du parser.
 * <p>
 * C'est la passerelle entre la liste des tokens
 * et l'<i>AST</i>.
 */
public class Parser {
    private final ArrayList<Token> tokens;

    /**
     * Constructeur du parser.
     * <p>
     * Utilise la liste des tokens à l'envers
     * pour permettre une récupération du suivant
     * en O(1) (opération <code>pop</code>)
     *
     * @param tokens La liste des tokens.
     */
    public Parser(ArrayList<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
        Collections.reverse(this.tokens);
    }

    /**
     * Construit un <i>AST</i> représentant l'expression
     * mathématique.
     *
     * @return L'arbre de l'expression.
     */
    public Tree constructTree() {
        return null;
    }

    /**
     * Regarde quel est le token suivant, le retire
     * de la liste, puis le renvoie.
     *
     * @return Le token suivant, null s'il n'y en a pas.
     */
    private Token next() {
        return tokens.removeLast();
    }

    /**
     * Regarde quel est le token suivant, puis le
     * renvoie sans le supprimer de la liste
     *
     * @return Le token suivant, null s'il n'y en a pas.
     */
    private Token peek() {
        return tokens.getLast();
    }
}
