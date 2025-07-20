package it.uniroma3.siw.repository;

import java.time.Year;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Videogioco;

public interface VideogiocoRepository extends CrudRepository<Videogioco, Long> {

    /* 
    // Custom query methods can be defined here if needed
    List<Videogioco> findByTitolo(String titolo);

    // Example of a method to find by release year
    List<Videogioco> findByAnnoPubblicazione(Year annoPubblicazione);
    
    // Method to find by genre
    List<Videogioco> findByGenere(String genere);
*/
}
