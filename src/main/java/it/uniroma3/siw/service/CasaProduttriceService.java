package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.CasaProduttrice;
import it.uniroma3.siw.repository.CasaProduttriceRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.VideogiocoRepository;

@Service
public class CasaProduttriceService {

    @Autowired
    private CasaProduttriceRepository casaProduttriceRepository;

    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private ImmagineRepository immagineRepository;


    public Iterable<CasaProduttrice> getAllCaseProduttrici() {
        return casaProduttriceRepository.findAll();
    }
    
}
