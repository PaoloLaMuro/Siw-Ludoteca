package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class HomeController {



    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;
 

 // HOME PAGE VISIBILE A TUTTI
    @GetMapping("/")
    public String mostraHome(Model model) {
        return "index"; // templates/index.html
    }

    // PAGINA DI LOGIN
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "formLogin"; // templates/formLogin.html
    }

    // PAGINA DI REGISTRAZIONE UTENTE
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "formRegisterUser"; // templates/formRegisterUser.html
    }

    // ELABORAZIONE REGISTRAZIONE
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "redirect:/login"; // meglio mandare al login
        }

        return "formRegisterUser"; // se ci sono errori
    }

    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        if (Credentials.ADMIN_ROLE.equals(credentials.getRole())) {
            model.addAttribute("admin", credentials.getUser()); // Passa admin alla view
            return "admin/indexAdmin";
        }

        model.addAttribute("user", credentials.getUser()); // Passa user alla view
        return "index";
    }


}