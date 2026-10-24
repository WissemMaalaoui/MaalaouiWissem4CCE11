
| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat ; supprimer le contrat supprime ses paiements. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule survit à la suppression de son agence. |
| Agence → Employe | LAZY | Aucune | Un employé existe indépendamment de l'agence ; aucune propagation n'est nécessaire. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules ; un Set évite les doublons. |
| Client → Reservation | LAZY | PERSIST | Enregistrer un client avec de nouvelles réservations les enregistre aussi ; supprimer un client ne doit pas effacer l'historique. |
| Reservation → Vehicule | LAZY | Aucune | Le véhicule existe indépendamment de la réservation. |
| Reservation ↔ Contrat | LAZY | ALL (côté Reservation) | Un contrat n'a de sens que pour sa réservation ; son cycle de vie suit celui de la réservation. |
| Vehicule → Maintenance | LAZY | PERSIST | Enregistrer un véhicule avec de nouvelles maintenances les enregistre aussi ; l'historique n'est pas supprimé avec le véhicule. |