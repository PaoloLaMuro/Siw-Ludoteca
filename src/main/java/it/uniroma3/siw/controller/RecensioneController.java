package it.uniroma3.siw.controller;

import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        /*
        @PostMapping("/videogioco/{id}/nuovaRecensione")
        public String salvaRecensione(@PathVariable("id") Long id, @ModelAttribute("recensione") Recensione recensione) {
            Videogioco videogioco = videogiocoService.getVideogiocoById(id).orElse(null);
            if (videogioco == null) {
                return "redirect:/videogiochi";
            }
        
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            User user = credentials.getUser();
        
            // 🛡️ Sicurezza: forza la creazione come nuova recensione
            recensione.setId(null);
        
            recensione.setVideogioco(videogioco);
            recensione.setAutore(user);
        
            recensioneService.save(recensione);
        
            return "redirect:/videogioco/" + id;
        }
        */
        @PostMapping("/videogioco/{id}/nuovaRecensione")
public String salvaRecensione(@PathVariable("id") Long id, @ModelAttribute("recensione") Recensione recensione, RedirectAttributes redirectAttributes) {
    Videogioco videogioco = videogiocoService.getVideogiocoById(id).orElse(null);
    if (videogioco == null) {
        return "redirect:/videogiochi";
    }

    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    User user = credentials.getUser();

    // Controllo se l'utente ha già rilasciato una recensione per questo videogioco
    List<Recensione> recensioniUtente = recensioneService.getRecensioniByAutore(user);
    boolean esisteRecensione = recensioniUtente.stream()
            .anyMatch(r -> r.getVideogioco().getId().equals(id));

    if (esisteRecensione) {
        redirectAttributes.addFlashAttribute("errorMessage", "Hai già rilasciato una recensione per questo videogioco.");
        return "redirect:/videogioco/" + id;
    }

    // 🛡️ Sicurezza: forza la creazione come nuova recensione
    recensione.setId(null);

    recensione.setVideogioco(videogioco);
    recensione.setAutore(user);

    recensioneService.save(recensione);

    redirectAttributes.addFlashAttribute("successMessage", "Recensione aggiunta con successo!");
    return "redirect:/videogioco/" + id;
}

}
