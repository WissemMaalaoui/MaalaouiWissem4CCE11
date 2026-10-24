package tn.esprit.maalaouiwissem4cce11.domain;

import jakarta.persistence.*;
import lombok.*;
@Getter
@NoArgsConstructor
@Setter
@Builder
@ToString
@AllArgsConstructor
@Entity
@Table(name="employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;
    private String nom;
    private String prenom;

}
