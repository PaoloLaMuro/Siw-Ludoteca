package it.uniroma3.siw.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Genere;
import it.uniroma3.siw.model.Videogioco;

public interface VideogiocoRepository extends CrudRepository<Videogioco, Long> {


    @Query("SELECT v FROM Videogioco v JOIN FETCH v.casaProduttrice")
    List<Videogioco> findAllByCasaProduttrice();


    @Query(value = "SELECT v.* FROM videogioco v JOIN recensione r ON v.id = r.videogioco_id GROUP BY v.id ORDER BY AVG(r.voto) DESC LIMIT 4", nativeQuery = true)
    List<Videogioco> getBest4Videogiochi();


    
    List<Videogioco> findByTitolo(String titolo);

    List<Videogioco> findByGenere(Genere genere);

    /* 
    // Custom query methods can be defined here if needed
    List<Videogioco> findByTitolo(String titolo);

    // Example of a method to find by release year
    List<Videogioco> findByDataUscita(Year dataUscita);
    
    // Method to find by genre
    List<Videogioco> findByGenere(String genere);
*/


}
