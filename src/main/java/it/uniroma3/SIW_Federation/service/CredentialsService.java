package it.uniroma3.SIW_Federation.service;


import it.uniroma3.SIW_Federation.model.Credentials;
import it.uniroma3.SIW_Federation.model.CustomUserDetails;
import it.uniroma3.SIW_Federation.model.Utente;
import it.uniroma3.SIW_Federation.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static it.uniroma3.SIW_Federation.model.Credentials.DEFAULT_ROLE;


@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;



    public void save(Credentials credentials, Utente utente) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        credentials.setRole(DEFAULT_ROLE);
        credentials.setUsername(credentials.getUsername());
        credentials.setUtente(utente);
        credentialsRepository.save(credentials);
    }

    public void changeRole(CustomUserDetails cu, String role) {
        Credentials c = findByUsername(cu.getUsername());
        c.setRole(role);
        credentialsRepository.save(c);
    }

    public Credentials findByUsername(String username) {
        return credentialsRepository.findByUsername(username);
    }
}
