package it.uniroma3.SIW_Federation.service;

import it.uniroma3.SIW_Federation.model.Presidente;
import it.uniroma3.SIW_Federation.repository.PresidenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresidenteService {

    @Autowired
    private PresidenteRepository presidenteRepository;


    public Presidente findById(Long id) {
        Optional<Presidente> presidenteOpt = presidenteRepository.findById(id);
        return presidenteOpt.orElseThrow(() -> new RuntimeException("Presidente non trovato"));
    }



    public Presidente findByUtenteId(Long utenteId) {
        return presidenteRepository.findByUtenteId(utenteId)
                .orElse(null); // Ritorna null se il presidente non è trovato
    }

    public void save(Presidente presidente) {
        presidenteRepository.save(presidente);
    }


    public void delete(Long id) {
        presidenteRepository.deleteById(id);
    }

    public List<Presidente> getPresidentiSenzaSquadra() {
        return presidenteRepository.findPresidentiSenzaSquadra();
    }



}

