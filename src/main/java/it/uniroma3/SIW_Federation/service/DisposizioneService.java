package it.uniroma3.SIW_Federation.service;

import it.uniroma3.SIW_Federation.model.Disposizione;
import it.uniroma3.SIW_Federation.repository.DisposizioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisposizioneService {

    @Autowired
    private DisposizioneRepository disposizioneRepository;

    public void save(Disposizione disposizione) {
        disposizioneRepository.save(disposizione);
    }
}
