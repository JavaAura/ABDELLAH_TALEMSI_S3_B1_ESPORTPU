# Projet de Gestion de Tournois d'e-Sport

## Introduction
Ce projet a été réalisé dans le cadre du développement d'une application de gestion pour organiser et suivre les tournois d'e-sport. Le but est de fournir une solution permettant la gestion des joueurs, des équipes et des tournois, en utilisant des technologies modernes comme **Spring**, **JPA/Hibernate**, et **H2**, tout en respectant des exigences techniques spécifiques.

## Objectifs du projet
L'application permet de :
- Gérer les joueurs : inscription, modification, suppression, et affichage.
- Gérer les équipes : création, modification, ajout/retrait de joueurs, et affichage.
- Gérer les tournois : création, modification, ajout/retrait d'équipes, affichage, et calcul de la durée estimée des tournois.

Le projet intègre plusieurs concepts et technologies, notamment :
- **Spring** pour l'Inversion de Contrôle (IoC) et l'Injection de Dépendances (DI).
- **JPA/Hibernate** pour l'accès aux données, avec une base de données **H2**.
- Des patterns de conception comme **Repository** et **Singleton**.
- Validation des entités avec des annotations comme `@NotNull`, `@Size`, etc.
- Extension du **DAO** pour le calcul avancé des durées de tournois, respectant le principe **Open/Closed** de SOLID.

## Structure du projet

### 1. Couche Modèle
Cette couche contient les entités **JPA** représentant les différentes composantes de l'application :
- **Joueur** : pseudo, âge, équipe.
- **Equipe** : nom, joueurs, tournois, classement.
- **Tournoi** : titre, jeu, date de début, date de fin, nombre de spectateurs, équipes, durée estimée, temps de pause entre les matchs, temps de cérémonie, statut (PLANIFIE, EN_COURS, TERMINE, ANNULE).
- **Jeu** : nom, difficulté, durée moyenne d'un match.

### 2. Couche DAO (Data Access Object)
Cette couche permet l'accès aux données via des interfaces et leur implémentation avec **JPA/Hibernate** :
- **TournoiDao** : contient la méthode `calculerDureeEstimeeTournoi(Long tournoiId)` qui calcule la durée estimée du tournoi.
- **TournoiDaoImpl** : implémente la méthode de calcul de base de la durée estimée.
- **TournoiDaoExtension** : étend le DAO pour calculer la durée estimée en incluant la difficulté du jeu et les cérémonies.

### 3. Couche Service
La couche service implémente la logique métier de l'application :
- **TournoiMetier** : expose la méthode `obtenirDureeEstimeeTournoi(Long tournoiId)` pour obtenir la durée totale estimée d'un tournoi, en utilisant le DAO.

### 4. Couche Présentation (Console)
Cette couche fournit une interface utilisateur sous la forme d'un menu console permettant :
- L'inscription de nouveaux joueurs et équipes.
- La création et la gestion de tournois.
- Le calcul et l'affichage de la durée estimée des tournois.

### 5. Configuration Spring
L'application utilise **Spring Core** pour l'IoC et la DI via un fichier de configuration XML (`applicationContext.xml`). Les beans sont gérés via ce fichier sans utiliser d'annotations.

### 6. Base de Données H2
La base de données **H2** est utilisée comme base embarquée. Les tables sont générées automatiquement via **JPA/Hibernate**, et les données sont persistées en mémoire lors de l'exécution.

## Réalisation Technique

### 1. Calcul de la durée estimée d'un tournoi

Deux versions du calcul ont été implémentées :

1. **Calcul de base** : La durée estimée est calculée comme suit :

    ```java
    durée estimée = (nombre d'équipes × durée moyenne d'un match) + temps de pause entre les matchs
    ```

    Cette logique est implémentée dans **TournoiDaoImpl**.

2. **Calcul avancé** : Une version plus complexe qui prend en compte la difficulté du jeu ainsi que les cérémonies d'ouverture et de clôture :

    ```java
    durée estimée = (nombre d'équipes × durée moyenne d'un match × difficulté du jeu) + temps de pause entre les matchs + temps des cérémonies
    ```

    Cette logique est implémentée dans **TournoiDaoExtension**.

### 2. Validation des Entités
Les entités **JPA** sont validées à l'aide des annotations **Hibernate Validator**, telles que `@NotNull`, afin d'assurer la cohérence des données.

### 3. Design Patterns
- **Repository Pattern** : Utilisé dans la couche DAO pour encapsuler l'accès aux données.
- **Singleton Pattern** : Implémenté pour garantir une instance unique de certains services.

### 4. Logging
Un système de logging a été mis en place à l'aide de **SLF4J** pour suivre les événements critiques dans l'application.

## Tests

### Tests unitaires
- Utilisation de **JUnit** et **Mockito** pour simuler des dépendances dans les tests.
- Vérification de la logique de calcul de la durée estimée des tournois.

### Test d'intégration
Un test d'intégration a été mis en place pour valider l'interaction entre la couche DAO et la couche service, en utilisant **JPA** et **H2** pour persister et récupérer les données.

### Couverture de Code
- **JaCoCo** est utilisé pour mesurer la couverture du code. Après exécution des tests, un rapport de couverture est généré dans le répertoire `target/site/jacoco`.

## Installation et Exécution

### Prérequis
- **Java 8** installé.
- **Maven** pour la gestion des dépendances.

### Étapes
1. Clonez le repository :
    ```bash
    git clone https://github.com/JavaAura/ABDELLAH_TALEMSI_S3_B1_ESPORTPU
    cd projet-esport
    ```

2. Installez les dépendances Maven :
    ```bash
    mvn clean install
    ```

3. Lancez l'application en exécutant la méthode `main` de la classe principale :
    ```bash
    mvn exec:java -Dexec.mainClass="org.tournoiPlace.main.Main"
    ```

4. Interagissez avec le menu console pour gérer les joueurs, les équipes et les tournois.

### Exécution des Tests
- Pour exécuter les tests :
    ```bash
    mvn test
    ```

- Pour générer le rapport de couverture :
    ```bash
    mvn jacoco:report
    ```

## Conclusion
Ce projet démontre la capacité à intégrer plusieurs technologies et concepts **Java** (Spring, Hibernate, JPA) et à appliquer les bonnes pratiques de développement (IoC, DI, SOLID). Le calcul de la durée estimée des tournois montre l'importance d'une architecture flexible et extensible. Des tests unitaires et d'intégration assurent la qualité du code, tandis que le système de logging permet de suivre les événements importants.
