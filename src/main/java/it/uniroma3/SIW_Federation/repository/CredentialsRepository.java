package it.uniroma3.SIW_Federation.repository;


import it.uniroma3.SIW_Federation.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
    Credentials findByUsername(String username);
}
