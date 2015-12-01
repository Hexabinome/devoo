#Devoo : OptimodLyon

[![Build Status](https://travis-ci.org/Hexabinome/devoo.svg?branch=master)](https://travis-ci.org/Hexabinome/devoo)

## Configuration
Le projet étant désormais un projet Maven, il suffit de l'importer dans n'importe quel IDE. Il ne devrait pas avoir de 
soucis. Si vous incluez des librairies plus tard ajoutez-les comme **dépendances Maven**.

Les machines du département ont Java 7. Néanmoins on peut utiliser Java 8 pour le projet avec JavaFx 8 qui est beaucoup mieux.

## Tests
Les tests sont à placer **obligatoirement** dans ce repertoire `src/test/java`. Maven fournit des plugins par défaut pour faire les tests.
Ceci permet d'automatiser les tests grâce à maven en utilisant ces conventions par défaut.

Pour lancer les tests : `mvn test`.  

[Tuto JUnit](http://blog.soat.fr/2014/02/du-bon-usage-de-junit-12/)


## Quelques liens utiles
 - [Tuto JavaFx](http://code.makery.ch/library/javafx-2-tutorial/)
 - ...
