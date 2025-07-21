package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.CasaProduttrice;
import it.uniroma3.siw.model.Genere;
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.model.PegiRating;
import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.repository.CasaProduttriceRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import it.uniroma3.siw.repository.VideogiocoRepository;
import jakarta.validation.Valid;

@Controller
public class AdminController {
    
 
    @Autowired
    private ImmagineRepository immagineRepository;

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
    @Transactional
    public String addVideogioco(@Valid @ModelAttribute("videogioco") Videogioco videogioco,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "copertinaFile", required = false) MultipartFile copertinaFile,
                                 Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generi", Genere.values());
            model.addAttribute("pegiRatings", PegiRating.values());
            model.addAttribute("caseProduttrici", casaProduttriceRepository.findAll());
            return "admin/formAddVideogioco"; // Ritorna alla form se ci sono errori
        }

        if (copertinaFile != null && !copertinaFile.isEmpty()) {
            Immagine immagine = new Immagine();
            immagine.setImageData(copertinaFile.getBytes());
            videogioco.setCopertina(immagine);
        }

        videogiocoRepository.save(videogioco);
        return "redirect:/admin/videogiochi"; // Reindirizza alla lista dei videogiochi
    }
    


     @GetMapping("/admin/addCasaProduttrice")
public String mostraFormNuovaCasaProduttrice(Model model) {
    model.addAttribute("casaProduttrice", new CasaProduttrice());
    return "admin/formAddCasaProduttrice";
}

@PostMapping("/admin/saveCasaProduttrice")
@Transactional
public String addCasaProduttrice(@Valid @ModelAttribute("casaProduttrice") CasaProduttrice casaProduttrice,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "fileLogo", required = false) MultipartFile fileLogo,
                                 Model model) throws IOException {
    if (bindingResult.hasErrors()) {
        return "admin/formAddCasaProduttrice"; // Ritorna alla form se ci sono errori
    }

    if (fileLogo != null && !fileLogo.isEmpty()) {
        Immagine immagine = new Immagine();
        immagine.setImageData(fileLogo.getBytes());
        immagineRepository.save(immagine); // Salva l'immagine nel repository
         // Associa l'immagine alla casa produttrice
        casaProduttrice.setLogo(immagine);
    }

    casaProduttriceRepository.save(casaProduttrice);
    return "redirect:/caseProduttrici"; // Reindirizza alla lista delle case produttrici
}

@GetMapping("/admin/updateCasaProduttrice/{id}")
public String mostraFormModificaCasaProduttrice(@PathVariable("id") Long id, Model model) {
    CasaProduttrice casaProduttrice = casaProduttriceRepository.findById(id).orElse(null);
    if (casaProduttrice == null) {
        return "redirect:/caseProduttrici"; // Reindirizza se l'ID non esiste
    }
    model.addAttribute("casaProduttrice", casaProduttrice);
    return "admin/formEditCasaProduttrice";
}

@PostMapping("/admin/updateCasaProduttrice/{id}")
@Transactional
public String aggiornaCasaProduttrice(@PathVariable("id") Long id,
                                      @Valid @ModelAttribute("casaProduttrice") CasaProduttrice casaProduttrice,
                                      BindingResult bindingResult,
                                      @RequestParam(value = "fileLogo", required = false) MultipartFile fileLogo,
                                      Model model) throws IOException {
    if (bindingResult.hasErrors()) {
        return "admin/formUpdateCasaProduttrice"; // Ritorna alla form se ci sono errori
    }

    CasaProduttrice casaProduttriceEsistente = casaProduttriceRepository.findById(id).orElse(null);
    if (casaProduttriceEsistente == null) {
        return "redirect:/caseProduttrici"; // Reindirizza se l'ID non esiste
    }

    // Aggiorna i campi della casa produttrice
    casaProduttriceEsistente.setNome(casaProduttrice.getNome());
    casaProduttriceEsistente.setTesto(casaProduttrice.getTesto());
    casaProduttriceEsistente.setAnnoPubblicazione(casaProduttrice.getAnnoPubblicazione());

    // Aggiorna il logo se un nuovo file è stato caricato
    if (fileLogo != null && !fileLogo.isEmpty()) {
        Immagine immagine = new Immagine();
        immagine.setImageData(fileLogo.getBytes());
        immagineRepository.save(immagine); // Salva la nuova immagine
        casaProduttriceEsistente.setLogo(immagine);
    }

    casaProduttriceRepository.save(casaProduttriceEsistente);
    return "redirect:/caseProduttrici"; // Reindirizza alla lista delle case produttrici
}

@PostMapping("/admin/deleteCasaProduttrice/{id}")
@Transactional
public String eliminaCasaProduttrice(@PathVariable("id") Long id) {
    CasaProduttrice casaProduttrice = casaProduttriceRepository.findById(id).orElse(null);
    if (casaProduttrice != null) {
        // Rimuovi l'immagine associata, se presente
        if (casaProduttrice.getLogo() != null) {
            immagineRepository.delete(casaProduttrice.getLogo());
        }
        // Elimina la casa produttrice
        casaProduttriceRepository.delete(casaProduttrice);
    }
    return "redirect:/caseProduttrici"; // Reindirizza alla lista delle case produttrici
}

}
