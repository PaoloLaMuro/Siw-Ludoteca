package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Genere;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.PegiRating;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.CasaProduttriceRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.VideogiocoRepository;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.VideogiocoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class VideogiocoController {

    @Autowired
    private ImmagineRepository immagineRepository;

    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private CasaProduttriceRepository casaProduttriceRepository;

    @Autowired
    private VideogiocoService videogiocoService;
    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/videogiochi")
    public String listVideogiochi(Model model) {
        model.addAttribute("videogiochi", videogiocoService.findAll());
        return "catalogoVideogiochi";
    }

    @GetMapping("/videogioco/{id}")
    @Transactional
    public String getVideogioco(@PathVariable("id") Long id, Model model) {
         Optional<Videogioco> videogiocoOpt = videogiocoService.getVideogiocoById(id);
        if (videogiocoOpt.isPresent()) {
            Videogioco videogioco = videogiocoOpt.get();
            Iterable<Recensione> recensioni = recensioneService.getRecensioniByVideogioco(videogioco);
            model.addAttribute("videogioco", videogioco);
            model.addAttribute("recensioni", recensioni);
            return "dettagliVideogioco";
        }
        return "dettagliVideogioco";
    }

//metodo getMapping che mi restituisce la pagina dove l'admin ha la form per inserire un nuovo videogioco
@GetMapping("/admin/addVideogioco")
public String mostraFormNuovoVideogioco(Model model) {
model.addAttribute("videogioco", new Videogioco());
model.addAttribute("generi", Genere.values());
model.addAttribute("pegiRatings", PegiRating.values());
model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());

System.out.println("Generi: " + Genere.values());
System.out.println("PEGI Ratings: " + PegiRating.values());
System.out.println("Case Produttrici: " + casaProduttriceRepository.findAll());

return "admin/formAddVideogioco";
}

@PostMapping("/admin/saveVideogioco")
@Transactional
public String addVideogioco(@Valid @ModelAttribute("videogioco") Videogioco videogioco,
                             BindingResult bindingResult,
                             @RequestParam(value = "copertinaFile", required = false) MultipartFile copertinaFile,
                             Model model) throws IOException {
    // Log iniziale
    System.out.println("Metodo addVideogioco chiamato.");

    // Log dei dati ricevuti dal form
    System.out.println("Titolo: " + videogioco.getTitolo());
    System.out.println("Genere: " + videogioco.getGenere());
    System.out.println("Descrizione: " + videogioco.getDescrizione());
    System.out.println("Data di uscita: " + videogioco.getDataUscita());
    System.out.println("PEGI: " + videogioco.getPegi());
    System.out.println("Casa produttrice: " + (videogioco.getCasaProduttrice() != null ? videogioco.getCasaProduttrice().getNome() : "Nessuna"));

    // Controllo errori di validazione
    if (bindingResult.hasErrors()) {
        System.out.println("Errori di validazione trovati: " + bindingResult.getAllErrors());
        model.addAttribute("generi", Genere.values());
        model.addAttribute("pegiRatings", PegiRating.values());
        model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());
        return "admin/formAddVideogioco"; // Ritorna alla form se ci sono errori
    }

    // Gestione del file immagine (copertina)
    if (copertinaFile != null && !copertinaFile.isEmpty()) {
        try {
            System.out.println("Caricamento della copertina: " + copertinaFile.getOriginalFilename());
            Immagine immagine = new Immagine();
            immagine.setImageData(copertinaFile.getBytes());
            immagineRepository.save(immagine);
            videogioco.setCopertina(immagine);
            System.out.println("Copertina salvata con successo.");
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento della copertina: " + e.getMessage());
            model.addAttribute("errore", "Errore durante il caricamento della copertina.");
            model.addAttribute("generi", Genere.values());
            model.addAttribute("pegiRatings", PegiRating.values());
            model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());
            return "admin/formAddVideogioco";
        }
    } else {
        System.out.println("Nessuna copertina caricata.");
    }

    // Salvataggio del videogioco
    videogiocoRepository.save(videogioco);
    System.out.println("Videogioco salvato con successo: " + videogioco.getTitolo());

    // Redirect alla lista dei videogiochi
    return "redirect:/videogiochi";
}

@GetMapping("/admin/editVideogioco/{id}")
public String showEditVideogiocoForm(@PathVariable("id") Long id, Model model) {
    Optional<Videogioco> videogiocoOpt = videogiocoRepository.findById(id);
    if (videogiocoOpt.isPresent()) {
        model.addAttribute("videogioco", videogiocoOpt.get());
        model.addAttribute("generi", Genere.values());
        model.addAttribute("pegiRatings", PegiRating.values());
        model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());
        return "admin/formUpdateVideogioco";
    } else {
        System.out.println("Videogioco con ID " + id + " non trovato.");
        return "redirect:/videogiochi";
    }
}

@PostMapping("/admin/updateVideogioco/{id}")
@Transactional
public String updateVideogioco(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("videogioco") Videogioco videogioco,
                               BindingResult bindingResult,
                               @RequestParam(value = "copertinaFile", required = false) MultipartFile copertinaFile,
                               Model model) throws IOException {
    System.out.println("Aggiornamento del videogioco con ID: " + id);

    if (bindingResult.hasErrors()) {
        System.out.println("Errori di validazione trovati: " + bindingResult.getAllErrors());
        model.addAttribute("generi", Genere.values());
        model.addAttribute("pegiRatings", PegiRating.values());
        model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());
        return "admin/formEditVideogioco";
    }

    Optional<Videogioco> videogiocoOpt = videogiocoRepository.findById(id);
    if (videogiocoOpt.isPresent()) {
        Videogioco existingVideogioco = videogiocoOpt.get();

        // Aggiorna i campi del videogioco
        existingVideogioco.setTitolo(videogioco.getTitolo());
        existingVideogioco.setGenere(videogioco.getGenere());
        existingVideogioco.setDescrizione(videogioco.getDescrizione());
        existingVideogioco.setDataUscita(videogioco.getDataUscita());
        existingVideogioco.setPegi(videogioco.getPegi());
        existingVideogioco.setCasaProduttrice(videogioco.getCasaProduttrice());

        // Gestione della copertina
        if (copertinaFile != null && !copertinaFile.isEmpty()) {
            try {
                Immagine nuovaImmagine = new Immagine();
                nuovaImmagine.setImageData(copertinaFile.getBytes());

                // Elimina la vecchia immagine, se presente
                if (existingVideogioco.getCopertina() != null) {
                    immagineRepository.delete(existingVideogioco.getCopertina());
                    System.out.println("Vecchia copertina eliminata.");
                }

                // Salva la nuova immagine
                immagineRepository.save(nuovaImmagine);
                existingVideogioco.setCopertina(nuovaImmagine);
                System.out.println("Nuova copertina salvata con successo.");
            } catch (IOException e) {
                System.out.println("Errore durante il caricamento della nuova copertina: " + e.getMessage());
                model.addAttribute("errore", "Errore durante il caricamento della nuova copertina.");
                return "admin/formEditVideogioco";
            }
        }

        // Salva le modifiche
        videogiocoRepository.save(existingVideogioco);
        System.out.println("Videogioco aggiornato con successo.");
    } else {
        System.out.println("Videogioco con ID " + id + " non trovato.");
    }

    return "redirect:/videogiochi";
}

@PostMapping("/admin/deleteVideogioco/{id}")
@Transactional
public String deleteVideogioco(@PathVariable("id") Long id) {
    System.out.println("Eliminazione del videogioco con ID: " + id);

    Optional<Videogioco> videogiocoOpt = videogiocoRepository.findById(id);
    if (videogiocoOpt.isPresent()) {
        Videogioco videogioco = videogiocoOpt.get();

        // Elimina l'immagine associata, se presente
        if (videogioco.getCopertina() != null) {
            immagineRepository.delete(videogioco.getCopertina());
            System.out.println("Copertina associata eliminata.");
        }

        // Elimina il videogioco
        videogiocoRepository.delete(videogioco);
        System.out.println("Videogioco eliminato con successo.");
    } else {
        System.out.println("Videogioco con ID " + id + " non trovato.");
    }

    return "redirect:/videogiochi";
}

}
