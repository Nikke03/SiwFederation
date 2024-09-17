package it.uniroma3.SIW_Federation.repository;

import it.uniroma3.SIW_Federation.model.Disposizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisposizioneRepository extends JpaRepository<Disposizione, Long> {
}
