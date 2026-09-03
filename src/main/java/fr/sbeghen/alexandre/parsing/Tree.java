package fr.sbeghen.alexandre.parsing;

import fr.sbeghen.alexandre.exception.DivisionByZeroException;
import fr.sbeghen.alexandre.tokenization.Operator;
import fr.sbeghen.alexandre.tokenization.Token;

/**
 * <i>Abstract Syntax Tree</i>.
 * <p>
 * Arbre binaire de l'expression mathématique.
 * Possède un nœud qui représente sa racine, ainsi
 * que d'éventuels enfants gauche et droit.
 * <p>
 * Les enfants gauche et droits sont des sous-<i>AST</i>
 * dont ils sont la racine.
 * <p>
 * Contient également les méthodes permettant
 * l'évaluation de cet arbre.
 */
public class Tree {

    /* ----- Classe ----- */

    /**
     * Applique une opération entre un ou deux opérandes.
     *
     * @param operator L'opération à effectuer.
     * @param a Premier opérande.
     * @param b Second opérande.
     * @return Résultat de l'opération
     *
     * @throws DivisionByZeroException Si l'opération est une division,
     * et que le diviseur (second opérande) est nul.
     */
    private static double applyOperation(Operator operator, double a, double b) {
        return switch (operator) {
            case PLUS -> a + b;
            case MINUS -> a - b;
            case NEGATE -> -a;
            case TIMES -> a * b;
            case DIV -> {
                if (b == 0)
                    throw new DivisionByZeroException(String.format("Tried to divide %f by 0", a));
                yield a / b;
            }
        };
    }

    /* ----- Instance ----- */

    private final Token node;
    private final Tree left;
    private final Tree right;

    /**
     * Premier constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine ainsi que <strong>deux</strong> enfants gauche et droit.
     */
    public Tree(Token node, Tree left, Tree right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }
    /**
     * Second constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine ainsi qu'<strong>un seul</strong> enfant qui est le gauche.
     */
    public Tree(Token node, Tree left) {
        this.node = node;
        this.left = left;
        right = null;
    }
    /**
     * Troisième et dernier constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine et <strong>aucun</strong> enfant.
     */
    public Tree(Token node) {
        this.node = node;
        left = null;
        right = null;
    }

    /**
     * Fonction résursive d'évaluation d'un <i>AST</i>.
     *
     * @return Le résultat de l'évaluation.
     *
     * @throws DivisionByZeroException Si une division
     * par zéro survient.
     */
    public double evaluate() {
        if (left == null && right == null)
            return node.value();

        double leftVal = (left != null) ? left.evaluate() : 1;
        double rightVal = (right != null) ? right.evaluate() : 1;
        Operator operator = Operator.values()[(int) node.value()];
        return applyOperation(operator, leftVal, rightVal);
    }
}
