# Math Expression Evaluator

Ce programme permet de calculer une expression mathématique à partir 
d'une string.

## Installation & tests

Le guide d'installation peut être trouvé [ici](docs/INSTALL.md).

## Étapes de l'évaluateur

### 1. Grammaire

Définition des règles du « *langage d'expression* » : quels caractères et
opérateurs sont autorisés. Cette section sert de référence pour les
étapes suivantes. La vérification réelle se fera pendant la [tokénisation](#tokenisation) et
le [parsing](#parsing)

- [ ] Définition des caractères autorisés (chiffres, opérateurs, parenthèses,
etc)
- [ ] Définition des opérateurs supportés et leur type (unaire / binaire)

### <a id="tokenisation"></a> 2. Tokénisation

Transformation des données brutes (caractères ASCII) en données
exploitables, les tokens.

- [ ] Transormer la chaîne de caractères en une liste de tokens
- [ ] Gérer les nombres à plusieurs chiffres, les nombres décimaux, etc

#### Exemples

- `"4 + 2 * 3"` → `( NUMBER, PLUS, NUMBER, TIMES, NUMBER )`
- `6 / 2 (1 + 2)` → `( NUMBER, DIV, NUMBER, LEFT, NUMBER, PLUS, NUMBER,
RIGHT)`

### <a id="parsing"></a> 3. Parsing des tokens, construction de l'AST

Un [Abstract Syntax Tree](https://en.wikipedia.org/wiki/Abstract_syntax_tree) (abrégé en *AST*) est un arbre dont les nœuds sont
des opérateurs, et les feuilles sont des opérandes. Un tel arbre permet donc
de représenter une expression mathématique avec les différentes priorités
selon sa structure.

- [ ] Construire un *AST* à partir des tokens
- [ ] Gérer les priorités (opérandes, parenthèses)

#### Ordre des opérations (plus faible → plus fort)

1. Addition
    - `+`
    - `-`
2. Multiplication / Division
    - `*`
    - `/`
    - `//`
    - `%`
3. Opérateurs unaires
    - `-`
4. Puissance
    - `**`
5. Parenthèses
    - `()`

#### Exemples

```
                    +
                   / \
4 + 2 * 3    →    4   *
                     / \
                    2   3
```
```
                      *
                     / \
(4 + 2) * 3    →    +   3
                   / \
                  4   2
```

### 4. Evaluation de l'AST

L'évaluation de l'*AST* consiste en un [parcours](https://en.wikipedia.org/wiki/Tree_traversal) [post-ordre](https://en.wikipedia.org/wiki/Tree_traversal#Post-order,_LRN)
de ce dernier.  L'algorithme en pseudo-code d'évaluation de l'*AST*
est le suivant :

```
évaluer(nœud):
   si nœud est une feuille:
      retourner sa valeur
   sinon:
      gauche = évaluer(nœud.gauche)
      droit = évaluer(nœud.droit)
      retourner opération(nœud.opération, gauche, droit)
```

### 5. Gestion des erreurs

Différentes erreurs peuvent survenir lors de la tentative d'évaluation
de l'expression. Il faut donc penser à les gérer pour ne pas causer un crash /
comportement non défini. Les principales erreurs à surveiller sont
les suivantes :

- Caractère invalide
- Mauvais parenthésage
- Syntaxe (ex: `3 +* 2`)
- Opérande manquant (ex: `3 +`)
- Division par 0
