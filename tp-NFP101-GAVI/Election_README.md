
# Election Simulation - README
### Étudiant : *GAVI Holali David*

## GitHub : https://github.com/g-holali-david/tp-cnam.git

## **Contexte du TP**

Ce projet est une réponse au TP dont le but est de modéliser **"des Élections législatives à 
système majoritaire uninominal à un tour"** dans le cadre du cours de 
programmation orientée objet. L'objectif est de modéliser un processus 
électoral à travers des entités (`Candidat` et `Circonscription`) en 
respectant les principes fondamentaux de la programmation orientée objet.

---

## **Objectifs du TP**

1. **Question 1 : Modélisation des candidats**
   - Créer une classe pour représenter un candidat avec un nom, un prénom, une affiliation politique (facultative) et un nombre de voix.
   - Différencier les candidats ayant 0 voix de ceux dont les voix ne sont pas encore enregistrées.
   - Implémenter un constructeur et une méthode `toString`.

2. **Question 2 : Modélisation des circonscriptions**
   - Créer une classe pour gérer les candidats, les inscrits, et le statut de l’élection.
   - Implémenter les fonctionnalités suivantes :
     - Ajouter des candidats.
     - Ajouter des voix à un candidat.
     - Clore l’élection avec une vérification de cohérence (voix ≤ inscrits).
     - Calculer le taux de participation.
     - Identifier le(s) vainqueur(s) avec gestion des égalités.
     - Fournir une méthode `toString` pour afficher les résultats.

3. **Question 3 : Utilisation des classes**
   - Simuler l’élection de Picsouville avec :
     - 3 candidats (Riri, Fifi, et Loulou).
     - Respectivement 12, 5, et 37 voix.
     - 75 inscrits sur les listes électorales.

---

## **Démarche et choix techniques**

### **1. Encapsulation stricte**
Pour ce projet, nous avons jugé bon de ne pas implémenter les setters pour 
renforcer l'encapsulation, garantir la cohérence des données, et réduire 
les risques d'erreurs dans le code. Toutes les variables d’instance sont privées.
Cela garantit l’intégrité des données et leur modification contrôlée.

### **2. Utilisation de `final`**
- Les variables immuables comme `nom`, `prenom`, ou `nom` de la circonscription sont marquées comme `final`.
- Cela empêche toute modification accidentelle et reflète le caractère constant de ces attributs 
- (par exemple, le nom d’un candidat ne change pas pendant une élection).

### **3. Validation des données**
Des contrôles sont mis en place pour éviter des situations incohérentes :
- Les noms et prénoms des candidats sont obligatoires.
- Les voix doivent être positives.
- Pas de doublons de candidats dans une même circonscription.
- Le total des voix ne peut pas dépasser le nombre d’inscrits.

### **4. Historique des actions**
Chaque modification importante (ajout de candidats, enregistrement de voix, 
clôture de l’élection) est enregistrée dans un historique pour garantir la traçabilité.

---

## **Structure du code**

1. **Classe `Candidat`**
   - Représente un candidat avec un nom, un prénom, une affiliation politique (facultative) et un nombre de voix.
   - Implémente les méthodes pour ajouter des voix et afficher les informations du candidat.

2. **Classe `Circonscription`**
   - Gère les candidats, le nombre d’inscrits, et le statut de l’élection.
   - Implémente les fonctionnalités suivantes :
     - Ajouter des candidats.
     - Ajouter des voix à un candidat.
     - Clore l’élection.
     - Calculer le taux de participation.
     - Déterminer le(s) vainqueur(s).
     - Fournir une représentation textuelle complète des résultats.

3. **Classe principale `Election`**
   - Contient le programme principal pour exécuter la simulation de l’élection de Picsouville.

---

## **Instructions d'exécution**

### **Option 1 : IDE **
1. Exécutez le programme dans un IDE sans créer des fichiers.
2. Copier et coller le code qui est dans codes-tp/Election.java
3. et coller le dans un IDE (en ligne par exemple)

### **Option 2 : IDE local - IntelliJ IDEA par exemple**

1. Le projet est un projet java
2. Vous pouvez le compilez et l'exécutez avec votre IDE (VS code, IntelliJ IDEA...).

---


---

## **Conclusion**

Ce projet répond aux consignes du TP en respectant les principes de la POO, 
notamment l’encapsulation, la validation, et la traçabilité. 
Le but est de proposer un modèle extensible pour simuler des élections législatives.
Nous avons pris soins de préserver l'intégrité et la cohérence des données,
leur immutabilité pour eviler la fraude et les modifications accidentelles.
