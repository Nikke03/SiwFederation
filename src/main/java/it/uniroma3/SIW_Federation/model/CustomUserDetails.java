package it.uniroma3.SIW_Federation.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Credentials credentials;
    private final Utente utente;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Credentials credentials, Utente utente, Collection<? extends GrantedAuthority> authorities) {
        this.credentials = credentials;
        this.utente = utente;
        this.authorities = authorities;
    }

    public Utente getUtente() {
        return utente;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getUsername() {
        return credentials.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}