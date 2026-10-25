package tn.esprit.maalaouiwissem4cce11.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Vehicule;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IVehiculeRepository;
import tn.esprit.maalaouiwissem4cce11.service.IVehiculeService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository vehiculeRepository;
    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        return vehiculeRepository.save(vehicule);
    }
    @Override
    public Vehicule update(Long id,Vehicule vehicule) {
        Vehicule existant=findById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        return vehiculeRepository.save(vehicule);
    }
    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }
    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule",id));
    }
    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }
}
