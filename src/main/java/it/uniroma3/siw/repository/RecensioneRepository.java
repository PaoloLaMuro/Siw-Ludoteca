package it.uniroma3.siw.repository;


import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Videogioco;

public interface RecensioneRepository extends CrudRepository<Recensione, Long> {

    Double findMediaVotiByVideogiocoId(Long videogiocoId);

    Iterable<Recensione> findByVideogioco(Videogioco videogioco);

    Iterable<Recensione> findByVideogiocoId(Long videogiocoId);

    
    //metodi come in books
}
