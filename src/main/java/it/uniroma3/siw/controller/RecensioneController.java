package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class RecensioneController {

    @Autowired
    RecensioneService recensioneService;


    //attenzione a questo optional tra qua e recensione service non sono sicurissimo !!!!!
    @PostMapping("/recensione/{id}/delete")
    public String eliminaRecensione(@PathVariable("id") Long id) {
        Optional<Recensione> recensioneOpt = recensioneService.getRecensioneById(id);
        if (recensioneOpt.isPresent()) {
            Recensione recensione = recensioneOpt.get();
            Long videogiocoId = recensione.getVideogioco().getId();
            recensioneService.deleteById(id);
            return "redirect:/videogioco/" + videogiocoId;
        }
        // Se non trovata, puoi reindirizzare dove preferisci (es. lista videogiochi)
        return "redirect:/videogiochi";
    }

}
