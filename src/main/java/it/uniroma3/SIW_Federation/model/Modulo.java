package it.uniroma3.SIW_Federation.model;

public enum Modulo {
    MODULO_442("4-4-2"),
    MODULO_352("3-5-2"),
    MODULO_433("4-3-3"),
    MODULO_4231("4-2-3-1");

    private String nome;

    Modulo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
