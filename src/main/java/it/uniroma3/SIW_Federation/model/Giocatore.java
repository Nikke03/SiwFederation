package it.uniroma3.SIW_Federation.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String luogoNascita;
    private String ruolo;
    private LocalDate dataInizioTesseramento;
    private LocalDate dataFineTesseramento;

    @ManyToOne
    @JoinColumn(name = "squadra_id", nullable = true)
    private Squadra squadra;

    // Getters e Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public LocalDate getDataInizioTesseramento() {
        return dataInizioTesseramento;
    }

    public void setDataInizioTesseramento(LocalDate dataInizioTesseramento) {
        this.dataInizioTesseramento = dataInizioTesseramento;
    }

    public LocalDate getDataFineTesseramento() {
        return dataFineTesseramento;
    }

    public void setDataFineTesseramento(LocalDate dataFineTesseramento) {
        this.dataFineTesseramento = dataFineTesseramento;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }
}

