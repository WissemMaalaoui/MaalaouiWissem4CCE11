# Atelier 5 : API REST, tests Postman et documentation Swagger

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| ContratController | GET /api/contrats | findAll() | 200 | — |
| ContratController | POST /api/contrats | create(contrat) | 201 | 400 |
| ContratController | GET /api/contrats/{id} | findById(id) | 200 | 404 |
| ContratController | PUT /api/contrats/{id} | update(id, contrat) | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | deleteById(id) | 204 | 404 |
| PaiementController | GET /api/paiements | findAll() | 200 | — |
| PaiementController | GET /api/paiements/{id} | findById(id) | 200 | 404 |
| AgenceController | GET /api/agences | findAll() | 200 | — |
| AgenceController | POST /api/agences | create(agence) | 201 | 400 |
| AgenceController | GET /api/agences/{id} | findById(id) | 200 | 404 |
| AgenceController | PUT /api/agences/{id} | update(id, agence) | 200 | 404, 400 |
| AgenceController | DELETE /api/agences/{id} | deleteById(id) | 204 | 404 |
| EmployeController | GET /api/employes | findAll() | 200 | — |
| EmployeController | POST /api/employes | create(employe) | 201 | 400 |
| EmployeController | GET /api/employes/{id} | findById(id) | 200 | 404 |
| EmployeController | PUT /api/employes/{id} | update(id, employe) | 200 | 404, 400 |
| EmployeController | DELETE /api/employes/{id} | deleteById(id) | 204 | 404 |
| VehiculeController | GET /api/vehicules | findAll() | 200 | — |
| VehiculeController | POST /api/vehicules | create(vehicule) | 201 | 400 |
| VehiculeController | GET /api/vehicules/{id} | findById(id) | 200 | 404 |
| VehiculeController | PUT /api/vehicules/{id} | update(id, vehicule) | 200 | 404, 400 |
| VehiculeController | DELETE /api/vehicules/{id} | deleteById(id) | 204 | 404 |
| EquipementController | GET /api/equipements | findAll() | 200 | — |
| EquipementController | POST /api/equipements | create(equipement) | 201 | 400 |
| EquipementController | GET /api/equipements/{id} | findById(id) | 200 | 404 |
| EquipementController | PUT /api/equipements/{id} | update(id, equipement) | 200 | 404, 400 |
| EquipementController | DELETE /api/equipements/{id} | deleteById(id) | 204 | 404 |
| ClientController | GET /api/clients | findAll() | 200 | — |
| ClientController | POST /api/clients | create(client) | 201 | 400 |
| ClientController | GET /api/clients/{id} | findById(id) | 200 | 404 |
| ClientController | PUT /api/clients/{id} | update(id, client) | 200 | 404, 400 |
| ClientController | DELETE /api/clients/{id} | deleteById(id) | 204 | 404 |
| ReservationController | GET /api/reservations | findAll() | 200 | — |
| ReservationController | POST /api/reservations | create(reservation) | 201 | 400 |
| ReservationController | GET /api/reservations/{id} | findById(id) | 200 | 404 |
| ReservationController | PUT /api/reservations/{id} | update(id, reservation) | 200 | 404, 400 |
| ReservationController | DELETE /api/reservations/{id} | deleteById(id) | 204 | 404 |
| MaintenanceController | GET /api/maintenances | findAll() | 200 | — |
| MaintenanceController | POST /api/maintenances | create(maintenance) | 201 | 400 |
| MaintenanceController | GET /api/maintenances/{id} | findById(id) | 200 | 404 |
| MaintenanceController | PUT /api/maintenances/{id} | update(id, maintenance) | 200 | 404, 400 |
| MaintenanceController | DELETE /api/maintenances/{id} | deleteById(id) | 204 | 404 |

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| `Contrat.paiements` | `@JsonManagedReference("contrat-paiements")` | Composition (cascade = ALL) : les paiements sont créés dans le JSON du contrat. |
| `Paiement.contrat` | `@JsonBackReference("contrat-paiements")` | Coupe la boucle Contrat ↔ Paiement. Même nom que la référence gérée. |
| `Contrat.reservation` (côté inverse du `@OneToOne`) | `@JsonIgnore` | Évite la boucle Contrat ↔ Reservation. |
| `Agence.vehicules` | `@JsonIgnore` | Le véhicule est créé seul et désigne son agence par identifiant. |
| `Agence.employees` | `@JsonIgnore` | L'employé est créé seul avec `{ "agence": {"idAgence": 1} }`. |
| `Client.reservations` | `@JsonIgnore` | La réservation est créée seule avec `{ "client": {"idClient": 1} }`. |
| `Vehicule.reservations` | `@JsonIgnore` | Liste inverse, la réservation désigne son véhicule par identifiant. |
| `Vehicule.maintenances` | `@JsonIgnore` | Liste inverse, la maintenance désigne son véhicule par identifiant. |
| `Equipement.vehicule` (côté inverse du `@ManyToMany`) | `@JsonIgnore` | Évite la boucle Vehicule ↔ Equipement. |
| `Employe.agence`, `Maintenance.vehicule`, `Reservation.client`, `Reservation.vehicule`, `Reservation.contrat`, `Vehicule.agence`, `Vehicule.equipment` (côté propriétaire) | Aucune | Ils doivent rester lisibles et écrivables dans le JSON (corps imbriqué par identifiant). |

**Différence entre `@JsonBackReference` et `@JsonIgnore` :**

- `@JsonBackReference` fonctionne en paire avec `@JsonManagedReference` : la liste est sérialisée, le parent ne l'est pas, et les enfants imbriqués dans le JSON sont rattachés à leur parent à la création.
- `@JsonIgnore` supprime simplement la propriété du JSON, en lecture comme en écriture. Il convient à une liste inverse qu'on ne veut jamais exposer ni recevoir.
- `@JsonBackReference` sur `Reservation.client` serait une erreur : la propriété serait ignorée à la lecture et le POST créerait une réservation sans client.

---

## C. Postman et Swagger



## D. Anomalies SonarQube

| # | Règle | Explication | Correction |
|---|---|---|---|
| 1 | java:S1710 | @ApiResponses est une enveloppe inutile : @ApiResponse est répétable depuis Java 8. | Suppression de @ApiResponses et de son import, @ApiResponse placées directement sur la méthode. |
---

## E. Questions de compréhension

1. **Composant qui reçoit les requêtes.** C'est le `DispatcherServlet`, point d'entrée unique. Il interroge le `HandlerMapping`, qui retrouve le contrôleur et la méthode à appeler à partir du verbe HTTP et de l'URL.

2. **Dépendance à `IContratService`.** Le contrôleur ne doit contenir aucune logique métier ni accès aux données : il délègue au service. Dépendre de l'interface (et non de `ContratServiceImpl`) réduit le couplage et permet de changer l'implémentation sans toucher au contrôleur. Dépendre de `ContratRepository` court-circuiterait la couche Service.

3. **Codes 201, 204 et 404.**
    - POST renvoie 201 Created : une ressource a été créée.
    - DELETE renvoie 204 No Content : la suppression a réussi et il n'y a rien à renvoyer.
    - Une ressource absente donne 404 car `ResourceNotFoundException` est traduite par le `GlobalExceptionHandler`. Sans ce traitement, l'exception non gérée produirait un 500, qui signale une erreur serveur et non une erreur du client.

4. **`PaiementController` en lecture seule.** Les paiements sont portés par le contrat (cascade = ALL) et créés, modifiés ou supprimés via lui. Le contrôleur n'expose donc que deux GET. Un `POST /api/paiements` reçoit 405 Method Not Allowed.

5. **Entités JPA exposées et choix des annotations.**
    - Risques : couplage entre l'API et la base, champs internes visibles, boucles de sérialisation, proxys LAZY non sérialisables, modification de l'API à chaque changement du modèle.
    - `Contrat.paiements` utilise `@JsonManagedReference` car c'est une composition : les paiements sont créés dans le JSON du contrat.
    - `Agence.vehicules` utilise `@JsonIgnore` car le véhicule est créé seul et désigne son agence par identifiant.
