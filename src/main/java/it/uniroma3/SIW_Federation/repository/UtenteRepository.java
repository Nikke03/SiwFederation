package it.uniroma3.SIW_Federation.repository;




import it.uniroma3.SIW_Federation.model.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {

}
