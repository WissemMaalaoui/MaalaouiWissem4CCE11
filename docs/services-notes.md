# Atelier 4 — Notes : IoC, injection de dépendances et couche Service

Projet : AutoLoc (`autoloc-api`)

---

## A. Modes d'injection comparés

| Critère | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Dépendance visible dans la signature de la classe ? | Oui | Non (dépendance cachée) |
| Utilisable avec `new` hors conteneur sans dépendance `null` ? | Oui | Non (le champ reste `null`) |
| Signalé par SonarQube for IDE ? | Non | Oui (règle S6813) |

**Conclusion :** l'injection par constructeur est la convention d'AutoLoc. Le service ne peut pas exister sans son repository.

---

## B. Services

Tous les services utilisent l'injection par constructeur (`@RequiredArgsConstructor` + champ `final`). Chaque service dépend de l'interface de son repository.

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| ClientServiceImpl | IClientRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| ReservationServiceImpl | IReservationRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| ContratServiceImpl | IContratRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule (`findById`, `findAll`), les paiements passent par le Contrat |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Dépendance obligatoire, champ `final`, service sans état |

---

## C. Messages d'erreur du conteneur

### Message 1 — Bean introuvable

- **Exception :** `NoSuchBeanDefinitionException` (enveloppée dans `UnsatisfiedDependencyException`).
- **Bean concerné :** `IContratService`, demandé par `ContratController`.
- **Causes probables :**
    1. L'annotation `@Service` est oubliée sur `ContratServiceImpl`.
    2. La classe est placée en dehors du package `tn.esprit.autoloc`, donc le scan ne la trouve pas.
- **Correction :** ajouter `@Service` et vérifier le package.

### Message 2 — Plusieurs beans du même type

- **Exception :** `NoUniqueBeanDefinitionException`.
- **Bean concerné :** le paramètre du constructeur de `NotificationController`. Deux beans existent : `emailNotificateur` et `smsNotificateur`.
- **Cause :** Spring ne sait pas lequel choisir.
- **Correction 1 :** `@Primary` sur la **classe** du bean choisi par défaut (ex. `EmailNotificateur`).
- **Correction 2 :** `@Qualifier("emailNotificateur")` sur le **paramètre du constructeur** du contrôleur.

### Message 3 — Dépendance circulaire

- **Exception :** `BeanCurrentlyInCreationException`.
- **Beans concernés :** `clientServiceImpl` et `reservationServiceImpl`.
- **Pourquoi c'est impossible avec le constructeur :** chaque bean a besoin de l'autre pour être créé. Aucun des deux ne peut être créé en premier.
- **Piste de conception :** extraire la responsabilité commune dans un troisième bean, utilisé par les deux services. Les deux services ne se référencent plus entre eux.

---

## D. Anomalies SonarQube for IDE

| # | Règle | Explication | Correction |
|---|---|---|---|
| 1 | S6813 | Injection par attribut (`@Autowired` sur un champ) | Retour à l'injection par constructeur avec `@RequiredArgsConstructor` et champ `final` |
| 2 | S112 | Lever une exception générique (`RuntimeException`) | Utiliser `ResourceNotFoundException` |
| 3 | Import inutile | Un import n'est pas utilisé | Suppression de l'import |

*(À adapter selon les anomalies réellement relevées dans votre projet.)*

---

## E. Questions de compréhension

**1. Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**
Il n'y a pas de `new` dans notre code. C'est le conteneur Spring qui crée l'objet au démarrage, car la classe est annotée `@Service` et détectée par le scan.

**2. Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**
Le contrôleur dépend d'une abstraction. On peut changer l'implémentation sans modifier le contrôleur. Le couplage est faible et les tests sont plus simples.

**3. Pourquoi un service singleton doit-il rester sans état ? Exemple de bug.**
Le singleton est partagé par tous les threads. Un attribut modifiable est donc partagé entre toutes les requêtes. Exemple : un attribut `contratCourant`. Deux utilisateurs appellent le service en même temps : le contrat du premier est remplacé par celui du second, et les données sont mélangées.

**4. Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` ?**
Passer directement l'objet reçu à `save` écraserait toutes les colonnes. Avec `orphanRemoval`, la collection de paiements pourrait être vidée. On charge donc l'existant, on recopie les attributs modifiables, puis on sauvegarde.

**5. Différences et cas particulier.**
- `@Component` / `@Bean` : `@Component` se place sur notre propre classe, détectée par le scan. `@Bean` se place sur une méthode d'une classe `@Configuration`, pour une classe qu'on ne peut pas annoter (bibliothèque, JDK).
- `@Primary` / `@Qualifier` : `@Primary` se place sur la classe et désigne le candidat par défaut. `@Qualifier` se place à l'injection et désigne un bean précis.
- `IPaiementService` n'expose ni création, ni modification, ni suppression : le paiement est géré par le Contrat (cascade). Le service Paiement est en lecture seule.
