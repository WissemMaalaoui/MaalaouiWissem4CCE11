package tn.esprit.maalaouiwissem4cce11.service;

import tn.esprit.maalaouiwissem4cce11.domain.Agence;


import java.util.List;

public interface IAgenceServices {
    Agence create(Agence agence);
    Agence update(Long id,Agence agence);
    void deleteById(Long id);
    Agence findById(Long id);
    List<Agence> findAll();



}
