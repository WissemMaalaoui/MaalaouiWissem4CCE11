package tn.esprit.maalaouiwissem4cce11.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Equipement;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IEquipementRepository;
import tn.esprit.maalaouiwissem4cce11.service.IEquipementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository equipementRepository;
    @Override
    public Equipement create(Equipement equipement) {
        if (equipement.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau equipement ne doit pas avoir d'identifiant");
        }
        return equipementRepository.save(equipement);
    }
    @Override
    public Equipement update(Long id,Equipement equipement) {
        Equipement existant=findById(id);
        existant.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existant);
    }
    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }
    @Override
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }
}