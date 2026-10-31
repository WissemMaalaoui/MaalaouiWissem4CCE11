package tn.esprit.maalaouiwissem4cce11.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Agence;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IAgenceRepository;
import tn.esprit.maalaouiwissem4cce11.service.IAgenceService;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository agenceRepository;
    @Override
    public Agence create(Agence agence) {
        if(agence.getIdAgence()!=null){
            throw new IllegalArgumentException("Un nouveau agnece ne doit pas avoir d'identifiant");
        }
        return agenceRepository.save(agence);
    }
    @Override
    public Agence update(Long id,Agence agence) {
        Agence existant=findById(id);
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return agenceRepository.save(existant);
    }
    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {

            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }
    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence",id));
    }
    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }
}