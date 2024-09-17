package it.uniroma3.SIW_Federation.model;

public enum Posizione {
    // Portiere
    PORTIERE,

    // Difensori
    DIFENSORE_SINISTRO,
    DIFENSORE_CENTRALE_1,
    DIFENSORE_CENTRALE_2,
    DIFENSORE_CENTRALE_3,  // Aggiunto per moduli con 3 centrali difensivi (es. 3-5-2)
    DIFENSORE_DESTRO,

    // Centrocampisti
    CENTROCAMPISTA_SINISTRO,
    CENTROCAMPISTA_CENTRALE_1,
    CENTROCAMPISTA_CENTRALE_2,
    CENTROCAMPISTA_CENTRALE_3,  // Aggiunto per moduli con più centrocampisti centrali
    CENTROCAMPISTA_DESTRO,

    CENTROCAMPISTA_DIFENSIVO_1, // Aggiunto per moduli difensivi (es. 4-2-3-1)
    CENTROCAMPISTA_DIFENSIVO_2, // Aggiunto per moduli difensivi (es. 4-2-3-1)

    CENTROCAMPISTA_OFFENSIVO_SINISTRO, // Aggiunto per moduli come 4-2-3-1
    CENTROCAMPISTA_OFFENSIVO_CENTRALE, // Aggiunto per moduli come 4-2-3-1
    CENTROCAMPISTA_OFFENSIVO_DESTRO,   // Aggiunto per moduli come 4-2-3-1

    // Attaccanti
    ATTACCANTE_SINISTRO, // Aggiunto per moduli come 4-3-3
    ATTACCANTE_CENTRALE, // Aggiunto per moduli con un solo attaccante centrale
    ATTACCANTE_DESTRO,   // Aggiunto per moduli come 4-3-3

    ATTACCANTE_1, // Per moduli classici a 2 punte (es. 4-4-2)
    ATTACCANTE_2  // Per moduli classici a 2 punte (es. 4-4-2)
}


