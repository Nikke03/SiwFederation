package it.uniroma3.SIW_Federation.repository;

import it.uniroma3.SIW_Federation.model.Presidente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PresidenteRepository extends CrudRepository<Presidente, Long> {
    Optional<Presidente> findByUtenteId(Long utenteId);
    @Query("SELECT p FROM Presidente p WHERE p.squadra IS NULL")
    List<Presidente> findPresidentiSenzaSquadra();

}