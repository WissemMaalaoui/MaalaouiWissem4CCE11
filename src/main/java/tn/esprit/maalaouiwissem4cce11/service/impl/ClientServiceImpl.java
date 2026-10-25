package tn.esprit.maalaouiwissem4cce11.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maalaouiwissem4cce11.domain.Client;
import tn.esprit.maalaouiwissem4cce11.exception.ResourceNotFoundException;
import tn.esprit.maalaouiwissem4cce11.repository.IClientRepository;
import tn.esprit.maalaouiwissem4cce11.service.IClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final IClientRepository clientRepository;
    @Override
    public Client create(Client client) {
        if (client.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant");
        }
        return clientRepository.save(client);
    }
    @Override
    public Client update(Long id,Client client) {
        Client existant=findById(id);
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setTelephone(client.getTelephone());
        existant.setEmail(client.getEmail());
        return clientRepository.save(existant);
    }
    @Override
    public void deleteById(Long id) {
        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Client",id);
        }
        clientRepository.deleteById(id);
    }
    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client",id));
    }
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}