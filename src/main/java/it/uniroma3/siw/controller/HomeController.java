package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * This method handles the root URL ("/") and returns the name of the view to be rendered.
     * It is used to display the home page of the application.
     *
     * @return The name of the view to be rendered, which is "home".
     */

    @GetMapping("/")
    public String mostraHome(Model model) {
        return "index"; // templates/index.html
    }







}