package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.CasaProduttrice;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.RecensioneRepository;
import it.uniroma3.siw.repository.VideogiocoRepository;
import jakarta.transaction.Transactional;

@Service
public class VideogiocoService {

    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private ImmagineRepository immagineRepository;

 

    @Autowired
    private RecensioneRepository recensioneRepository;


    @Transactional
    public Optional<Videogioco> getVideogiocoById(Long id) {
        return this.videogiocoRepository.findById(id);
    }

    public Iterable<Videogioco> getAllVideogiochi() {
        return this.videogiocoRepository.findAll();
    }

    public Videogioco salvaVideogioco(Videogioco videogioco) {
        return videogiocoRepository.save(videogioco);
    }

    public void eliminaVideogioco(Videogioco videogioco) {
        videogiocoRepository.delete(videogioco);
    }

    @Transactional
    public Videogioco findById(Long id) {
        Optional<Videogioco> videogioco = videogiocoRepository.findById(id);
        return videogioco.orElse(null);
    }


    @Transactional
    public List<Videogioco> findAll() {
        return videogiocoRepository.findAllByCasaProduttrice();
    }


    public Double calcolaMediaVoti(Long videogiocoId){
        Double media = recensioneRepository.findMediaVotiByVideogiocoId(videogiocoId);
        return (media != null) ? media : 0.0;
    }

    @Transactional
    public void updateVideogioco(Videogioco videogiocoDaAggiornare, CasaProduttrice casaProduttrice, MultipartFile nuovaImmagine) throws IOException {
        Videogioco videogioco = videogiocoRepository.findById(videogiocoDaAggiornare.getId())
                .orElseThrow(() -> new IllegalArgumentException("Gioco non trovato con ID: " + videogiocoDaAggiornare.getId()));

        videogioco.setTitolo(videogiocoDaAggiornare.getTitolo());
        videogioco.setDataUscita(videogiocoDaAggiornare.getDataUscita());

        videogioco.setCasaProduttrice(casaProduttrice);

        if (nuovaImmagine != null && !nuovaImmagine.isEmpty()) {
            Immagine nuovaCopertina = new Immagine();
            nuovaCopertina.setImageData(nuovaImmagine.getBytes());
            Immagine copertinaSalvata = immagineRepository.save(nuovaCopertina);
            videogioco.setCopertina(copertinaSalvata);
        }

        videogiocoRepository.save(videogioco);
    }
    
}
