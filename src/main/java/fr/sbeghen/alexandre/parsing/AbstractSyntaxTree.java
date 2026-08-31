package fr.sbeghen.alexandre.parsing;

import fr.sbeghen.alexandre.exception.DivisionByZeroException;
import fr.sbeghen.alexandre.tokenization.Operation;
import fr.sbeghen.alexandre.tokenization.Token;

/**
 * Arbre binaire de l'expression mathématique.
 * Possède une racine, ainsi que d'éventuels enfants
 * gauche et droit.
 * <p>
 * Contient également les méthodes permettant
 * l'évaluation de cet arbre.
 */
public class AbstractSyntaxTree {
    private final Token root;
    private final Token left;
    private final Token right;

    /**
     * Premier constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine ainsi que <strong>deux</strong> enfants gauche et droit.
     */
    public AbstractSyntaxTree(Token root, Token left, Token right) {
        this.root = root;
        this.left = left;
        this.right = right;
    }
    /**
     * Second constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine ainsi qu'<strong>un seul</strong> enfant qui est le gauche.
     */
    public AbstractSyntaxTree(Token root, Token left) {
        this.root = root;
        this.left = left;
        right = null;
    }
    /**
     * Troisième et dernier constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine et <strong>aucun</strong> enfant.
     */
    public AbstractSyntaxTree(Token root) {
        this.root = root;
        left = null;
        right = null;
    }

    public Token getRoot() { return root; }
    public Token getLeft() { return left; }
    public Token getRight() { return right; }

    /**
     * Applique une opération entre un ou deux opérandes.
     *
     * @param operation L'opération à effectuer.
     * @param a Premier opérande.
     * @param b Second opérande.
     * @return Résultat de l'opération
     *
     * @throws DivisionByZeroException Si l'opération est une division,
     * et que le diviseur (second opérande) est nul.
     */
    public double applyOperation(Operation operation, double a, double b) {
        return 0;
    }

    /**
     * Fonction résursive d'évaluation d'un <i>AST</i>.
     * <p>
     * Celle-ci prend en paramètre le nœud par lequel
     * commencer la récursion.
     *
     * @param node Le nœud de départ
     * @return Le résultat de l'évaluation.
     */
    public double evaluate(AbstractSyntaxTree node) {
        return 0;
    }
    /**
     * Fonction résursive d'évaluation d'un <i>AST</i>.
     * <p>
     * Signature sans paramètre, on commence donc
     * par le nœud lui-même.
     *
     * @return Le résultat de l'évaluation.
     *
     * @see evaluate(AbstractSyntaxTree)
     */
    public double evaluate() {
        return evaluate(this);
    }
}
