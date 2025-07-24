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
import jakarta.transaction.Transactional;

@Controller
public class RecensioneController {

    @Autowired
    RecensioneService recensioneService;

    @Autowired
    VideogiocoService videogiocoService;
    @Autowired
    CredentialsService credentialsService;





    /*
    //Metodo per eliminare una recensione con controllo di autorizzazione
    @PostMapping("/recensione/{id}/delete")
    public String eliminaRecensione(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        // Ottieni l'utente corrente autenticato
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User currentUser = credentials.getUser();
        
        Optional<Recensione> recensioneOpt = recensioneService.getRecensioneById(id);
        if (recensioneOpt.isPresent()) {
            Recensione recensione = recensioneOpt.get();
            
            // Controllo di autorizzazione: solo l'autore può eliminare la sua recensione
            if (!recensione.getAutore().getId().equals(currentUser.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Non sei autorizzato a eliminare questa recensione.");
                return "redirect:/user/recensioni";
            }
            
            recensioneService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Recensione eliminata con successo!");
            return "redirect:/user/recensioni"; // Torna alla pagina delle recensioni dell'utente
        }
        
        redirectAttributes.addFlashAttribute("errorMessage", "Recensione non trovata.");
        return "redirect:/user/recensioni";
    }


*/


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
        
        @PostMapping("/videogioco/{id}/nuovaRecensione")
    public String salvaRecensione(@PathVariable("id") Long id, @ModelAttribute("recensione") Recensione recensione, RedirectAttributes redirectAttributes) {
    Optional<Videogioco> videogioco = videogiocoService.getVideogiocoById(id);
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

    */

    @PostMapping("/videogioco/{id}/nuovaRecensione")
public String salvaRecensione(@PathVariable("id") Long id,
                              @ModelAttribute("recensione") Recensione recensione,
                              RedirectAttributes redirectAttributes) {
    Optional<Videogioco> optionalVideogioco = videogiocoService.getVideogiocoById(id);
    if (optionalVideogioco.isEmpty()) {
        redirectAttributes.addFlashAttribute("errorMessage", "Videogioco non trovato.");
        return "redirect:/videogiochi";
    }

    Videogioco videogioco = optionalVideogioco.get();

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

    recensione.setId(null);
    recensione.setVideogioco(videogioco); // entità Hibernate
    recensione.setAutore(user);

    recensioneService.save(recensione);
    redirectAttributes.addFlashAttribute("successMessage", "Recensione aggiunta con successo!");
    return "redirect:/videogioco/" + id;
}


    /*
    @GetMapping("/user/recensioni")
    public String leMieRecensioni(Model model) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    User user = credentials.getUser();

    model.addAttribute("user", user.getName());
    model.addAttribute("credentials", credentials);
    model.addAttribute("recensioni", user.getRecensioni());
    return "user/recensioneUser";

}
*/

   @PostMapping("/recensione/delete/{id}")
public String eliminaRecensione(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    System.out.println(" Richiesta eliminazione recensione con ID: " + id); // <-- LOG
    System.out.println(" Entrato nel controller delete con ID: " + id);  // LOG DI DEBUG

    Optional<Recensione> recensioneOpt = recensioneService.getRecensioneById(id);
    if (recensioneOpt.isPresent()) {
        recensioneService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Recensione eliminata con successo!");
    } else {
        redirectAttributes.addFlashAttribute("errorMessage", "Recensione non trovata.");
    }
    return "redirect:/user/recensioni";
}




}
