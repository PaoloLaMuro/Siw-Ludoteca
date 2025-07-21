package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.VideogiocoService;

@Controller
public class RecensioneController {

    @Autowired
    RecensioneService recensioneService;

    @Autowired
    VideogiocoService videogiocoService;
    @Autowired
    CredentialsService credentialsService;


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

       // GET: mostra la form
        @GetMapping("/videogioco/{id}/nuovaRecensione")
        public String nuovaRecensioneForm(@PathVariable("id") Long id, Model model) {
            Videogioco videogioco = videogiocoService.getVideogiocoById(id).orElse(null);
            model.addAttribute("videogioco", videogioco);
            model.addAttribute("recensione", new Recensione());
            return "user/nuovaRecensione";
        }

    // POST: salva la recensione
    @PostMapping("/videogioco/{id}/nuovaRecensione")
    public String salvaRecensione(@PathVariable("id") Long id, @ModelAttribute("recensione") Recensione recensione) {
        Videogioco videogioco = videogiocoService.getVideogiocoById(id).orElse(null);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User user = credentials.getUser();
        recensione.setVideogioco(videogioco);
        recensione.setAutore(user);
        recensioneService.save(recensione);
        return "redirect:/videogioco/" + id;
    }

}
