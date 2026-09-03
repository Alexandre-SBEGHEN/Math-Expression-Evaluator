package fr.sbeghen.alexandre.parsing;

/**
 * Dans l'algorithme Pratt Parsing, le <i>Binding Power</i>
 * est une valeur numérique associée à chaque opérateur qui
 * détermine sa priorité et son associativité.
 *
 * @param lbp <i>Left binding power</i> : la force avec laquelle l'opérateur s'accroche à l'opérande à sa gauche.
 * @param rbp <i>Right binding power</i> : la force avec laquelle il s'accroche à l'opérande à sa droite
 */
public record BindingPower(double lbp, double rbp) {}
