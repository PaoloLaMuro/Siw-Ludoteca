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

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Genere;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.PegiRating;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.CasaProduttriceRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.VideogiocoRepository;
import it.uniroma3.siw.service.CredentialsService;
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



}
