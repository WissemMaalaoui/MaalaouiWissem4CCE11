package tn.esprit.maalaouiwissem4cce11.service;


import tn.esprit.maalaouiwissem4cce11.domain.Employe;


import java.util.List;

public interface IEmployeService {
    Employe create(Employe employe);
    Employe update(Long id,Employe employe);
    void deleteById(Long id);
    Employe findById(Long id);
    List<Employe> findAll();
}
