package tn.esprit.maalaouiwissem4cce11.service;


import tn.esprit.maalaouiwissem4cce11.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {
    Maintenance create(Maintenance maintenance);
    Maintenance update(Long id,Maintenance maintenance);
    void deleteById(Long id);
    Maintenance findById(Long id);
    List<Maintenance> findAll();
}
