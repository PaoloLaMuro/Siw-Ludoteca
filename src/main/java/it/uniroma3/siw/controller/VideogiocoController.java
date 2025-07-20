package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Videogioco;
import it.uniroma3.siw.service.VideogiocoService;

@Controller
public class VideogiocoController {

    @Autowired
    private VideogiocoService videogiocoService;

    @GetMapping("/videogiochi")
    public String listVideogiochi(Model model) {
        model.addAttribute("videogiochi", videogiocoService.findAll());
        return "videogiochi";
    }

    @GetMapping("/videogioco/{id}")
    public String getVideogioco(@PathVariable Long id, Model model) {
        Videogioco videogioco = videogiocoService.findById(id);
        if (videogioco != null) {
            model.addAttribute("videogioco", videogioco);
            return "videogiocoDetails";
        } else {
            return "error";
        }
    }

}
