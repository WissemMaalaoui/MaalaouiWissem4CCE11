package tn.esprit.maalaouiwissem4cce11.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
    @OneToMany(mappedBy="agence", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Vehicule> vehicules;
    @OneToMany(mappedBy="agence", fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Employe> employees;
}