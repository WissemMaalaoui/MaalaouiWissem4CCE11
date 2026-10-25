package tn.esprit.maalaouiwissem4cce11.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Contrat;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IContratRepository;
import tn.esprit.maalaouiwissem4cce11.service.IContratService;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {
    private final IContratRepository contratRepository;
    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat()!=null){
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        return contratRepository.save(contrat);
    }
    @Override
    public Contrat update(Long id,Contrat contrat) {
        Contrat existant=findById(id);
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setDateSignature(contrat.getDateSignature());
        return contratRepository.save(existant);
    }
    @Override
    public void deleteById(Long id) {
        if(!contratRepository.existsById(id)){
            throw new ResourceNotFoundException("Contrat",id);
        }
        contratRepository.deleteById(id);
    }
    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat",id));
    }
    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }
}