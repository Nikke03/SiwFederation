package it.uniroma3.SIW_Federation.controller;


import it.uniroma3.SIW_Federation.model.Credentials;
import it.uniroma3.SIW_Federation.model.CustomUserDetails;
import it.uniroma3.SIW_Federation.model.Presidente;
import it.uniroma3.SIW_Federation.model.Utente;
import it.uniroma3.SIW_Federation.service.CredentialsService;
import it.uniroma3.SIW_Federation.service.PresidenteService;
import it.uniroma3.SIW_Federation.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PresidenteService presidenteService;






    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("utente") Utente utente, @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        utenteService.save(utente);
        credentialsService.save(credentials, utente);
        return "redirect:/login";
    }

    @GetMapping ("/register")
    public String registraUtente(Model model) {
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("utente", new Utente());
        return "register";
    }

    @GetMapping("/Account")
    public String account(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
        var credentials = credentialsService.findByUsername(loggedUser.getUsername());
        var utente = credentials.getUtente();

        model.addAttribute("authentication", loggedUser);
        model.addAttribute("credentials", credentials);
        model.addAttribute("utente", utente);

        if ("ROLE_DEFAULT".equals(credentials.getRole())) {
            return "Account";
        } else if ("ROLE_ADMIN".equals(credentials.getRole())) {
            return "/Admin/AccountAdmin";
        } else if ("ROLE_PRESIDENT".equals(credentials.getRole())) {
            Presidente presidente = presidenteService.findByUtenteId(utente.getId());
            model.addAttribute("p", presidente);

            // Passa la data formattata al modello solo se il presidente esiste e la data non è null
            if (presidente != null && presidente.getDataNascita() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = presidente.getDataNascita().format(formatter);
                model.addAttribute("formattedDate", formattedDate);
            } else {
                model.addAttribute("formattedDate", "Data di nascita non disponibile");
            }

            return "/Presidente/AccountP";
        }

        return "redirect:/login";
    }



    @PostMapping("/becomePresident")
    public String becomePresident(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @RequestParam String codiceFiscale,
                                  @RequestParam String dataNascita,
                                  @RequestParam String luogoNascita) {
        // Ottieni l'utente attualmente autenticato
        Utente utente = utenteService.findById(userDetails.getUtente().getId());

        // Crea un nuovo Presidente e imposta i dati
        Presidente presidente = new Presidente();
        presidente.setNome(utente.getNome());
        presidente.setCognome(utente.getCognome());
        presidente.setCodiceFiscale(codiceFiscale);
        presidente.setDataNascita(LocalDate.parse(dataNascita));
        presidente.setLuogoNascita(luogoNascita);
        presidente.setUtente(utente);

        // Salva il presidente
        presidenteService.save(presidente);

        // Aggiorna i ruoli dell'utente
        credentialsService.changeRole(userDetails, "ROLE_PRESIDENT");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_PRESIDENT"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reindirizza alla pagina del presidente
        return "redirect:/";
    }
}



