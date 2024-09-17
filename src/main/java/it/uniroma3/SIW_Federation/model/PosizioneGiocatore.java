package it.uniroma3.SIW_Federation.model;

import jakarta.persistence.*;

@Entity
public class PosizioneGiocatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Disposizione disposizione;

    @Enumerated(EnumType.STRING)
    private Posizione posizione; // Enum o altra classe che rappresenta le posizioni (ad es. Difensore, Attaccante, ecc.)

    @ManyToOne
    private Giocatore giocatore;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disposizione getDisposizione() {
        return disposizione;
    }

    public void setDisposizione(Disposizione disposizione) {
        this.disposizione = disposizione;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }
}
