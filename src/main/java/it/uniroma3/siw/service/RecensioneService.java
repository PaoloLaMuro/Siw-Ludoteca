package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.RecensioneRepository;
import jakarta.transaction.Transactional;

@Service
public class RecensioneService {
    @Autowired
    private RecensioneRepository recensioneRepository;

    public List<Recensione> getRecensioniByAutore(User autore) {
    return recensioneRepository.findByAutore(autore);
}

    public List<Recensione> getRecensioniByVideogioco(Videogioco videogioco) {
        return (List<Recensione>) this.recensioneRepository.findByVideogioco(videogioco);
    }

    public List<Recensione> getRecensioniByVideogiocoId(Long videogiocoId) {
        return (List<Recensione>) this.recensioneRepository.findByVideogiocoId(videogiocoId);
    }

    public void save(Recensione recensione) {
        this.recensioneRepository.save(recensione);
    }

    public Optional<Recensione> getRecensioneById(Long id) {
        return this.recensioneRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.recensioneRepository.deleteById(id);
    }

    public boolean esisteRecensionePerVideogiocoEAutore(Videogioco videogioco, User autore) {
        return recensioneRepository.existsByVideogiocoAndAutore(videogioco, autore);
    }

}
