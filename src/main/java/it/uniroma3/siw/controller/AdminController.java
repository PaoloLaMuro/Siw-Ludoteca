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
import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.repository.CasaProduttriceRepository;
import it.uniroma3.siw.repository.ImmagineRepository;
import jakarta.validation.Valid;

@Controller
public class AdminController {
    
 
    @Autowired
    private ImmagineRepository immagineRepository;


    @Autowired
    private CasaProduttriceRepository casaProduttriceRepository;







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
    System.out.println("Inizio metodo addCasaProduttrice");

    // Verifica se ci sono errori di validazione
    if (bindingResult.hasErrors()) {
        System.out.println("Errori di validazione trovati: " + bindingResult.getAllErrors());
        return "admin/formAddCasaProduttrice"; // Ritorna alla form se ci sono errori
    }

    // Verifica se il file del logo è stato caricato
    if (fileLogo != null && !fileLogo.isEmpty()) {
        System.out.println("File logo caricato: " + fileLogo.getOriginalFilename());
        try {
            Immagine immagine = new Immagine();
            immagine.setImageData(fileLogo.getBytes());
            immagineRepository.save(immagine); // Salva l'immagine nel repository
            System.out.println("Logo salvato con successo nel repository");
            casaProduttrice.setLogo(immagine); // Associa l'immagine alla casa produttrice
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del logo: " + e.getMessage());
            model.addAttribute("errore", "Errore durante il caricamento del logo.");
            return "admin/formAddCasaProduttrice";
        }
    } else {
        System.out.println("Nessun file logo caricato");
    }

    // Salva la casa produttrice
    try {
        casaProduttriceRepository.save(casaProduttrice);
        System.out.println("Casa produttrice salvata con successo: " + casaProduttrice.getNome());
    } catch (Exception e) {
        System.out.println("Errore durante il salvataggio della casa produttrice: " + e.getMessage());
        model.addAttribute("errore", "Errore durante il salvataggio della casa produttrice.");
        return "admin/formAddCasaProduttrice";
    }

    System.out.println("Fine metodo addCasaProduttrice");
    return "redirect:/caseProduttrici"; // Reindirizza alla lista delle case produttrici
}

@GetMapping("/admin/updateCasaProduttrice/{id}")
public String mostraFormModificaCasaProduttrice(@PathVariable("id") Long id, Model model) {
    CasaProduttrice casaProduttrice = casaProduttriceRepository.findById(id).orElse(null);
    if (casaProduttrice == null) {
        return "redirect:/caseProduttrici"; // Reindirizza se l'ID non esiste
    }
    model.addAttribute("casaProduttrice", casaProduttrice);
    return "admin/formUpdateCasaProduttrice";
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
