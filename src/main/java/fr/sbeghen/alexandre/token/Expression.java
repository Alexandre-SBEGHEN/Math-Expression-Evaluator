package fr.sbeghen.alexandre.token;

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
