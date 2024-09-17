package it.uniroma3.SIW_Federation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UtenteController {

    @GetMapping
    public String userPage() {
        return "user";
    }
}
