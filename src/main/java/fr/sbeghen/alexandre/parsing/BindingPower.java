package fr.sbeghen.alexandre.parsing;

/**
 * Dans l'algorithme Pratt Parsing, le <i>Binding Power</i>
 * est une valeur numérique associée à chaque opérateur qui
 * détermine sa priorité et son associativité.
 *
 * @param left Priorité à gauche.
 * @param right Priorité à droite.
 */
public record BindingPower(double left, double right) {}
