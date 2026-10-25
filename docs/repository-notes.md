# Notes sur la couche Repository — AutoLoc

## Choix d'interface

| Interface | Étend | Justification |
|---|---|---|
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, `findAll` renvoie une List, tri et pagination disponibles. |
| IEmployeRepository | JpaRepository<Employe, Long> | Idem. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | Idem. |
| IEquipementRepository | JpaRepository<Equipement, Long> | Idem. |
| IClientRepository | JpaRepository<Client, Long> | Idem. |
| IReservationRepository | JpaRepository<Reservation, Long> | Idem. |
| IContratRepository | JpaRepository<Contrat, Long> | Idem, avec `saveAndFlush` pour une écriture immédiate en base. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Idem ; créé pour lire les paiements, la création et le retrait passent par le Contrat. |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | Idem. |

**Pourquoi JpaRepository ?** Il cumule CRUD, listes, tri, pagination et `flush`, ce qui évite de choisir entre plusieurs interfaces.

## Anomalies SonarQube for IDE corrigées

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Import `java.util.ArrayList` inutilisé dans `Equipement.java` | S1128 : un import non utilisé alourdit le code sans raison. | Suppression de l'import. |
| Import `java.util.List` inutilisé dans `Equipement.java` | S1128 : même règle. | Suppression de l'import. |
| Import `java.util.ArrayList` inutilisé dans `Vehicule.java` | S1128 : même règle. | Suppression de l'import. |
| Import `java.util.List` inutilisé dans `Vehicule.java` | S1128 : même règle. | Suppression de l'import. |