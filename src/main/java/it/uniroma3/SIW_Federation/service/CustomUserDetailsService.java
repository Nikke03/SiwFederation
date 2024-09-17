package it.uniroma3.SIW_Federation.service;


import it.uniroma3.SIW_Federation.model.Credentials;
import it.uniroma3.SIW_Federation.model.CustomUserDetails;
import it.uniroma3.SIW_Federation.model.Utente;
import it.uniroma3.SIW_Federation.repository.CredentialsRepository;
import it.uniroma3.SIW_Federation.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cerca le credenziali dell'utente nel repository utilizzando lo username
        Credentials credentials = credentialsRepository.findByUsername(username);

        // Se le credenziali non vengono trovate, lancia un'eccezione
        if (credentials == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Utente utente = credentials.getUtente();
        if (utente == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Crea un'autorità (ruolo) per l'utente utilizzando direttamente il ruolo memorizzato
        GrantedAuthority authority = new SimpleGrantedAuthority(credentials.getRole());

        // Crea un oggetto CustomUserDetails che include sia le credenziali che i dettagli dell'utente
        return new CustomUserDetails(credentials, utente, Collections.singletonList(authority));
    }
}
