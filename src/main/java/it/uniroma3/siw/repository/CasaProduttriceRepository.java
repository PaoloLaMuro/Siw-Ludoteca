package it.uniroma3.siw.repository;

import java.time.Year;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.CasaProduttrice;

public interface CasaProduttriceRepository extends CrudRepository<CasaProduttrice, Long> {

    // Custom query methods can be defined here if needed
    List<CasaProduttrice> findByNome(String nome);

    // Example of a method to find by publication year
    List<CasaProduttrice> findByAnnoPubblicazione(Year annoPubblicazione);

}
