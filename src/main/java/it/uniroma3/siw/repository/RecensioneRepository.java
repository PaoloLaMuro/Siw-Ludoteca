package it.uniroma3.siw.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Videogioco;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

    Double findMediaVotiByVideogiocoId(Long videogiocoId);

    List<Recensione> findByVideogioco(Videogioco videogioco);

    List<Recensione> findByVideogiocoId(Long videogiocoId);

    
    //metodi come in books
}
