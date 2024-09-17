package it.uniroma3.SIW_Federation.service;



import it.uniroma3.SIW_Federation.model.Utente;
import it.uniroma3.SIW_Federation.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }


    public Utente findById(Long id) {
        Optional<Utente> utente = utenteRepository.findById(id);
        return utente.orElse(null); // Oppure gestisci il caso in cui l'utente non è trovato
    }
}
