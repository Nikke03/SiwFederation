package it.uniroma3.SIW_Federation.model;

import jakarta.persistence.*;

@Entity
public class Credentials {
    public static final String DEFAULT_ROLE = "ROLE_DEFAULT";

    public static final String PRESIDENT_ROLE = "ROLE_PRESIDENT";

    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    private String role;
    @OneToOne
    private Utente utente;



    // Getters and Setters




    public Utente getUtente() {
        return utente;
    }



    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
