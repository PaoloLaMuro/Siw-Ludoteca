package it.uniroma3.siw.service;

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
}
