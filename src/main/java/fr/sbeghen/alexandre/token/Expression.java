package fr.sbeghen.alexandre.token;

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

    @Override
    public String toString() {
        return expression;
    }
}
