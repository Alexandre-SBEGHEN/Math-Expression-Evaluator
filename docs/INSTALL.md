# Installation & Tests

**Pré-requis :** avoir Java installé (version 25 minimum)

## Build classique

Pour faire un build du projet, entrez :

```shell
./mvnw install    # MacOS, Linux
mvnw.cmd install  # Windows
```

Pour exécuter le programme, entrez :

```shell
java -jar target/math-expression-evaluator-v1.0.0-SNAPSHOT.jar
```

## Tests

Pour lancer les tests, entrez :

```shell
./mvnw test     # MacOS, Linux
mvnw.cmd test   # Windows
```

## Nettoyage

Pour effacer tous les exécutables, entrez :

```shell
./mvnw clean    # MacOS, Linux
mvnw.cmd clean  # Windows
```