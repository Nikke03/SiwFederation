package it.uniroma3.SIW_Federation.repository;

import it.uniroma3.SIW_Federation.model.Giocatore;
import it.uniroma3.SIW_Federation.model.Squadra;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

    List<Giocatore> findBySquadraIsNull();
    List<Giocatore> findByDataInizioTesseramentoIsNullAndDataFineTesseramentoIsNull();

    List<Giocatore> findBySquadraIsNullAndDataInizioTesseramentoIsNullAndDataFineTesseramentoIsNull();

    List<Giocatore> findBySquadra(Squadra squadra);

    List<Giocatore> findAll(Sort sort);

    /*
    Iterable<Giocatore> findAllByOrderByNomeAsc();
    Iterable<Giocatore> findAllByOrderByRuoloAsc();

    @Query(value = "SELECT * FROM giocatore WHERE nome LIKE :nome%", nativeQuery = true)
    Iterable<Giocatore> nomeSimile(@Param("nome")String nome);

     */

}
