package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Genere;
import it.uniroma3.siw.model.PegiRating;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.CasaProduttriceRepository;
import it.uniroma3.siw.repository.VideogiocoRepository;
import jakarta.validation.Valid;

@Controller
public class AdminController {
    
 


    @Autowired
    private VideogiocoRepository videogiocoRepository;

    @Autowired
    private CasaProduttriceRepository casaProduttriceRepository;




//metodo getMapping che mi restituisce la pagina dove l'admin ha la form per inserire un nuovo videogioco
     @GetMapping("/admin/addVideogioco")
         public String mostraFormNuovoVideogioco(Model model) {
        model.addAttribute("videogioco", new Videogioco());
        model.addAttribute("generi", Genere.values());
        model.addAttribute("pegiRatings", PegiRating.values());
        model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());
        return "admin/formAddVideogioco";
    }
     

     @PostMapping("/admin/saveVideogioco")
     public String addVideogioco(@Valid @ModelAttribute("videogioco") Videogioco videogioco,
                                 BindingResult bindingResult,
                                 Model model) {      
         if (bindingResult.hasErrors()) {
             return "formAddVideogioco"; // Ritorna alla form se ci sono errori
         }
         
                  videogiocoRepository.save(videogioco);
         return "redirect:/admin/videogiochi"; // Reindirizza alla lista dei videogiochi
     }
    





}
