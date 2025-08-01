package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.CasaProduttriceService;

@Controller
public class CasaProduttriceController {


    @Autowired
    CasaProduttriceService casaProduttriceService;

    @GetMapping("/caseProduttrici")
    public String getCaseProduttrici(Model model) {
        var caseProduttrici = casaProduttriceService.getAllCaseProduttrici();
        System.out.println("Case Produttrici trovate: " + caseProduttrici);
        model.addAttribute("caseProduttrici", caseProduttrici);
        return "caseProduttrici";
    }



    
}
