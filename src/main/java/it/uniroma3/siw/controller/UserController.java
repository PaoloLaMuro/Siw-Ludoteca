package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class UserController {


    @Autowired
    private  CredentialsService credentialsService;



    //ho qualche problemqa, quando inserisco la recensione poi non mi fa vedere /leMieRecensioni
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


    @GetMapping("/user")
    public String getIndexUser(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        User user = credentials.getUser();
        model.addAttribute("user", user);
        return "user/indexUser";
    }





}
