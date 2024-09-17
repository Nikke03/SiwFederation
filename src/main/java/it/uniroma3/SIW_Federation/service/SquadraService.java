package it.uniroma3.SIW_Federation.service;

import it.uniroma3.SIW_Federation.model.Giocatore;
import it.uniroma3.SIW_Federation.model.Presidente;
import it.uniroma3.SIW_Federation.model.Squadra;
import it.uniroma3.SIW_Federation.repository.PresidenteRepository;
import it.uniroma3.SIW_Federation.repository.SquadraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SquadraService {

    @Autowired
    private SquadraRepository squadraRepository;



    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private PresidenteRepository presidenteRepository;

    @Transactional(readOnly = true)
    public List<Squadra> getAllSquadre() {
        Iterable<Squadra> squadreIterable = squadraRepository.findAll();
        return StreamSupport.stream(squadreIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public void aggiungiGiocatore(Squadra squadra, Giocatore giocatore) {
        // Imposta la data di inizio tesseramento alla data attuale
        LocalDate dataInizio = LocalDate.now();
        giocatore.setDataInizioTesseramento(dataInizio);

        // Imposta la data di fine tesseramento a un anno dopo la data di inizio
        LocalDate dataFine = dataInizio.plusYears(1);
        giocatore.setDataFineTesseramento(dataFine);

        // Aggiungi il giocatore alla squadra
        squadra.getGiocatori().add(giocatore);

        // Imposta la squadra per il giocatore
        giocatore.setSquadra(squadra);

        // Salva il giocatore aggiornato
        giocatoreService.save(giocatore);

        // Salva la squadra aggiornata
        squadraRepository.save(squadra);
    }


    @Transactional(readOnly = true)
    public Squadra findById(Long id) {
        return squadraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Squadra non trovata"));
    }

    @Transactional
    public void save(Squadra squadra) {
        squadraRepository.save(squadra);
    }

    @Transactional
    public void delete(Long id) {
        squadraRepository.deleteById(id);
    }

    public void aggiornaPresidente(Long squadraId, Long presidenteId) {
        // Trova la squadra
        Squadra squadra = squadraRepository.findById(squadraId)
                .orElseThrow(() -> new EntityNotFoundException("Squadra non trovata con ID: " + squadraId));

        // Trova il nuovo presidente
        Presidente nuovoPresidente = presidenteService.findById(presidenteId);
        Presidente vecchioPresidente = this.findById(squadraId).getPresidente();

        vecchioPresidente.setSquadra(null);

        // Associa il nuovo presidente alla squadra
        squadra.setPresidente(nuovoPresidente);
        nuovoPresidente.setSquadra(this.findById(squadraId));

        // Salva la squadra con il nuovo presidente
        squadraRepository.save(squadra);
    }

    public void cambiaPresidente(Long squadraId, Long nuovoPresidenteId) {
        Squadra squadra = squadraRepository.findById(squadraId)
                .orElseThrow(() -> new EntityNotFoundException("Squadra non trovata"));

        // Ottieni il presidente attuale
        Presidente vecchioPresidente = squadra.getPresidente();

        // Imposta il nuovo presidente
        Presidente nuovoPresidente = presidenteRepository.findById(nuovoPresidenteId)
                .orElseThrow(() -> new EntityNotFoundException("Nuovo presidente non trovato"));

        // Se esiste un vecchio presidente, rimuovi il riferimento alla squadra
        if (vecchioPresidente != null) {
            vecchioPresidente.setSquadra(null);
            presidenteRepository.save(vecchioPresidente);
        }

        // Imposta il nuovo presidente per la squadra
        squadra.setPresidente(nuovoPresidente);
        nuovoPresidente.setSquadra(squadra);

        // Salva i cambiamenti nel repository
        squadraRepository.save(squadra);
        presidenteRepository.save(nuovoPresidente);
    }

    /*
    public Long countByAttaccanti(Squadra squadra){
        List<Giocatore> giocatori = squadra.getGiocatori();
        Long i = 0L;
        for(Giocatore g: giocatori){
            if(g.getRuolo().equals("Attaccante")){
                i++;
            }
        }
        return i;
    }

    public Long countByDifensori(Squadra squadra){
        List<Giocatore> giocatori = squadra.getGiocatori();
        Long i = 0L;
        for(Giocatore g: giocatori){
            if(g.getRuolo().equals("Difensore")){
                i++;
            }
        }
        return i;
    }

    public Long countByCentrocampisti(Squadra squadra){
        List<Giocatore> giocatori = squadra.getGiocatori();
        Long i = 0L;
        for(Giocatore g: giocatori){
            if(g.getRuolo().equals("Centrocampista")){
                i++;
            }
        }
        return i;
    }

    public Long countByPortiere(Squadra squadra){
        List<Giocatore> giocatori = squadra.getGiocatori();
        Long i = 0L;
        for(Giocatore g: giocatori){
            if(g.getRuolo().equals("Portiere")){
                i++;
            }
        }
        return i;
    }


     */
}
