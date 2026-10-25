package tn.esprit.maalaouiwissem4cce11.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Employe;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IEmployeRepository;
import tn.esprit.maalaouiwissem4cce11.service.IEmployeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository employeRepository;
    @Override
    public Employe create(Employe employe) {
        if (employe.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau employé ne doit pas avoir d'identifiant");
        }
        return employeRepository.save(employe);
    }
    @Override
    public Employe update(Long id,Employe employe) {
        Employe existant=findById(id);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        return employeRepository.save(existant);
    }
    @Override
    public void deleteById(Long id) {
        if(!employeRepository.existsById(id)){
            throw new ResourceNotFoundException("Employe",id);
        }
        employeRepository.deleteById(id);
    }
    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe",id));
    }
    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }
}