package it.uniroma3.SIW_Federation.service;

import it.uniroma3.SIW_Federation.model.Giocatore;
import it.uniroma3.SIW_Federation.model.Posizione;
import it.uniroma3.SIW_Federation.model.PosizioneGiocatore;
import it.uniroma3.SIW_Federation.model.Squadra;
import it.uniroma3.SIW_Federation.repository.GiocatoreRepository;
import it.uniroma3.SIW_Federation.repository.SquadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiocatoreService {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @Autowired
    private SquadraRepository squadraRepository;


    // Fetch players without a team



    public List<Giocatore> findGiocatoriSenzaSquadra() {
        return giocatoreRepository.findByDataInizioTesseramentoIsNullAndDataFineTesseramentoIsNull();
    }

    public List<Giocatore> findGiocatoriDisponibili() {
        return giocatoreRepository.findBySquadraIsNullAndDataInizioTesseramentoIsNullAndDataFineTesseramentoIsNull();
    }

    public Giocatore findById(Long id) {
        return giocatoreRepository.findById(id).orElse(null);
    }

    public void save(Giocatore giocatore) {
        giocatoreRepository.save(giocatore);
    }

    // Get all players
    public List<Giocatore> findAll() {
        return (List<Giocatore>) giocatoreRepository.findAll();
    }

    public List<Giocatore> findGiocatoriBySquadra(Squadra squadra) {
        return giocatoreRepository.findBySquadra(squadra);
    }

    /*

public  List<Giocatore> findByNome(){
    Iterable<Giocatore> giocatori = giocatoreRepository.findAllByOrderByNomeAsc();
    List<Giocatore> giocatorigigi = new ArrayList<>();
    for(Giocatore g: giocatori){
        giocatorigigi.add(g);
    }
    return  giocatorigigi;
}

    public  List<Giocatore> findByRuolo(){
        Iterable<Giocatore> giocatori = giocatoreRepository.findAllByOrderByRuoloAsc();
        List<Giocatore> giocatorigigi = new ArrayList<>();
        for(Giocatore g: giocatori){
            giocatorigigi.add(g);
        }
        return  giocatorigigi;
    }

    public List<Giocatore> findByNomeSimile(String keyword){
        Iterable<Giocatore> giocatori = giocatoreRepository.nomeSimile(keyword);
        List<Giocatore> giocatorigigi = new ArrayList<>();
        for(Giocatore g: giocatori){
            giocatorigigi.add(g);
        }
        return  giocatorigigi;
    }


     */
}