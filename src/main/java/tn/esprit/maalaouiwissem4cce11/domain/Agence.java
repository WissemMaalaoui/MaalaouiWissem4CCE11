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
@Table(name = "agence")
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}