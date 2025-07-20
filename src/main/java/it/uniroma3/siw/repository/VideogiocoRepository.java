package it.uniroma3.siw.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Videogioco;

public interface VideogiocoRepository extends CrudRepository<Videogioco, Long> {

    List<Videogioco> findAllWithCasaProduttrice();

    /* 
    // Custom query methods can be defined here if needed
    List<Videogioco> findByTitolo(String titolo);

    // Example of a method to find by release year
    List<Videogioco> findByDataUscita(Year dataUscita);
    
    // Method to find by genre
    List<Videogioco> findByGenere(String genere);
*/
}
