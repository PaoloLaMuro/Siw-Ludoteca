package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {
    @Autowired
    private RecensioneRepository recensioneRepository;

    public Iterable<Recensione> getRecensioniByVideogioco(Videogioco videogioco) {
        return this.recensioneRepository.findByVideogioco(videogioco);
    }

    public Iterable<Recensione> getRecensioniByVideogiocoId(Long videogiocoId) {
        return this.recensioneRepository.findByVideogiocoId(videogiocoId);
    }

    public void save(Recensione recensione) {
        this.recensioneRepository.save(recensione);
    }

    public Optional<Recensione> getRecensioneById(Long id) {
        return this.recensioneRepository.findById(id);
    }

    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }


}
