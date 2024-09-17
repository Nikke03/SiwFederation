package it.uniroma3.SIW_Federation.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Disposizione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Squadra squadra;

    @ManyToOne
    private Giocatore giocatore;

    private String posizioneCampo;


    @Enumerated(EnumType.STRING)
    private Modulo modulo;

    // Mappa tra posizione e giocatore usando una classe intermedia
    @OneToMany(mappedBy = "disposizione", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosizioneGiocatore> posizioneGiocatori = new ArrayList<>();

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<PosizioneGiocatore> getPosizioneGiocatori() {
        return posizioneGiocatori;
    }

    public void setPosizioneGiocatori(List<PosizioneGiocatore> posizioneGiocatori) {
        this.posizioneGiocatori = posizioneGiocatori;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public String getPosizioneCampo() {
        return posizioneCampo;
    }

    public void setPosizioneCampo(String posizioneCampo) {
        this.posizioneCampo = posizioneCampo;
    }
}

