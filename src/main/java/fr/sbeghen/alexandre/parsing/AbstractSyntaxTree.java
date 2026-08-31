package fr.sbeghen.alexandre.parsing;

import fr.sbeghen.alexandre.exception.DivisionByZeroException;
import fr.sbeghen.alexandre.tokenization.Operation;
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
public class AbstractSyntaxTree {
    private final Token node;
    private final AbstractSyntaxTree left;
    private final AbstractSyntaxTree right;

    /**
     * Premier constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine ainsi que <strong>deux</strong> enfants gauche et droit.
     */
    public AbstractSyntaxTree(Token node, AbstractSyntaxTree left, AbstractSyntaxTree right) {
        this.node = node;
        this.left = left;
        this.right = right;
    }
    /**
     * Second constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine ainsi qu'<strong>un seul</strong> enfant qui est le gauche.
     */
    public AbstractSyntaxTree(Token node, AbstractSyntaxTree left) {
        this.node = node;
        this.left = left;
        right = null;
    }
    /**
     * Troisième et dernier constructeur de l'<i>AST</i>.
     * <p>
     * Il possède une racine et <strong>aucun</strong> enfant.
     */
    public AbstractSyntaxTree(Token node) {
        this.node = node;
        left = null;
        right = null;
    }

    public Token getNode() { return node; }
    public AbstractSyntaxTree getLeft() { return left; }
    public AbstractSyntaxTree getRight() { return right; }

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
    public static double applyOperation(Operation operation, double a, double b) {
        return switch (operation) {
            case PLUS -> a + b;
            case MINUS -> a - b;
            case TIMES -> a * b;
            case DIV -> {
                if (b == 0)
                    throw new DivisionByZeroException(String.format("Tried to divide %f by 0", a));
                yield a / b;
            }
        };
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

        double leftVal = left.evaluate();
        double rightVal = right.evaluate();
        Operation operation = Operation.values()[(int) node.value()];
        return applyOperation(operation, leftVal, rightVal);
    }
}
