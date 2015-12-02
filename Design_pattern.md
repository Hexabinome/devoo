# Choix architecturaux et design pattern pour le projet Devoo

Voici quelques explications concernant les choix architecturaux notamment les design pattern.

## Modèle-vue-contrôleur ou MVC 

L'architecture globale du projet est basée sur le patron de conception MVC. En effet ce patron permet
d'avoir une bonne séparation entre la logique applicative et l'interface graphique de l'application. Elle
offre ainsi un cadre normalisé pour structurer une application d'une certaine complexité comme celle
qu'on a eu à développer. Avec cette structure on pourra facilement changer d'interface graphique
en gardant notre le *Modèle* qui constitue le coeur de l'application.

### Controleur
TODO

### Modèle
TODO

### Vue
TODO

## Patron de conception Etat 

Pour définir les différents comportements de notre application, on a utilisé le design pattern Etat
qui permet déléguer le traitement d'un évènement à une autre classe. Dans une logique de programmer
pour des interfaces, on a défini une interface commune à tous les états possibles de notre
application. Chaque état implémentant cette interface, rédefini les évènements qui ont un effet sur
lui. 
L'ajout d'un nouvel état c'est à dire d'une nouvelle fonctionnalité devient ainsi assez facile.
Par contre, ajouter un nouvel évènement utilisateur serait un peu fastidieux dans la mesure où il
faudrait que tous les états redéfinissent la méthode associée à ce nouvel évènement meme s'ils
n'effectueront aucun traitement.

Dans notre cas, au vu des différentes fonctionnalités de notre application,  il paraissait évident
d'utiliser un tel patron de conception.

## Patron de conception Observateur/Observable

Pour assurer une communication entre notre modèle et la vue, on a utilisé le patron de conception
observateur/observable. Ce pattern permet de faire communiquer des modules tout en reduisant les
dépendances entre eux.

Dans ce pattern on *une notion de notification envoyée à un observateur* lorsqu'il se passe quelque
chose. Dans notre cas, la vue constitue l'observateur. Elle est notifiée à chaque fois qu'il y un
changement dans le modéle par exemple lors de la fin du calcul de la tournée par exemple pour lui
dire qu'elle doit l'afficher.

## Patron de conception Commande
TODO

## Patron de conception Singleton
TODO : cf DeserialiseurXML



