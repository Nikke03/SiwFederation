package it.uniroma3.SIW_Federation.controller;


import it.uniroma3.SIW_Federation.Validator.GiocatoreValidator;
import it.uniroma3.SIW_Federation.Validator.SquadraValidator;
import it.uniroma3.SIW_Federation.model.CustomUserDetails;
import it.uniroma3.SIW_Federation.model.Giocatore;
import it.uniroma3.SIW_Federation.model.Presidente;
import it.uniroma3.SIW_Federation.model.Squadra;
import it.uniroma3.SIW_Federation.service.CredentialsService;
import it.uniroma3.SIW_Federation.service.GiocatoreService;
import it.uniroma3.SIW_Federation.service.PresidenteService;
import it.uniroma3.SIW_Federation.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {


    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private SquadraService squadraService;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private GiocatoreValidator giocatoreValidator;

    @Autowired
    private SquadraValidator squadraValidator;

    @GetMapping("/admin/indexAdmin")
    public String indexAdmin(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("authentication", customUserDetails);
        return "Admin/indexAdmin";
    }


    // Endpoint to view presidents without teams
    @GetMapping("/Admin/Lista")
    public String visualizzaPresidentiSenzaSquadra(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        // Fetching presidents without assigned teams
        List<Presidente> presidentiSenzaSquadra = presidenteService.getPresidentiSenzaSquadra();
        model.addAttribute("authentication", customUserDetails);
        model.addAttribute("presidentiSenzaSquadra", presidentiSenzaSquadra);
        return "Admin/ListaPresidenti"; // This maps to admin/presidenti-senza-squadra.html
    }

    @GetMapping("/admin/presidenti/{id}/creaSquadra")
    public String mostraFormCreaSquadra(@PathVariable("id") Long presidenteId,@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Presidente presidente = presidenteService.findById(presidenteId);

        if (presidente == null || presidente.getId() == null) {
            return "redirect:/error"; // Se il presidente non esiste, gestisci l'errore
        }

        Squadra squadra = new Squadra();  // Non impostare il presidente qui
        model.addAttribute("authentication", customUserDetails);
        model.addAttribute("presidente", presidente);
        model.addAttribute("squadra", squadra);

        return "Admin/admin-crea-squadra";
    }

    @PostMapping("/admin/presidenti/{id}/creaSquadra")
    public String creaSquadra(@PathVariable("id") Long presidenteId, @ModelAttribute("squadra") Squadra squadra,BindingResult bindingResult, Model model ) {
        // Trova il presidente selezionato
        Presidente presidente = presidenteService.findById(presidenteId);

        if (presidente == null || presidente.getId() == null) {
            // Gestisci l'errore se il Presidente non esiste
            return "redirect:/error";
        }

        squadraValidator.validate(squadra, bindingResult);

        // Se ci sono errori di validazione, ritorna il form con gli errori
        if (bindingResult.hasErrors()) {
            // Redirect to the player list (change URL if needed)
            model.addAttribute("presidente", presidente);

            return "Admin/admin-crea-squadra";
        }

        // Imposta il presidente sulla squadra solo ora
        squadra.setPresidente(presidente);
        presidente.setSquadra(squadra);
        // Salva la squadra
        squadraService.save(squadra);
        presidenteService.save(presidente);
        // Reindirizza a una pagina di conferma o elenco
        List<Presidente> presidentiSenzaSquadra = presidenteService.getPresidentiSenzaSquadra();
        model.addAttribute("presidentiSenzaSquadra", presidentiSenzaSquadra);
        return "Admin/ListaPresidenti";
    }

    // Show form for adding a new player
    @GetMapping("/Admin/giocatori/aggiungi")
    public String mostraFormAggiungiGiocatore(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("authentication", customUserDetails);
        model.addAttribute("giocatore", new Giocatore());
        return "Admin/form-aggiungi-giocatore";  // Name of the HTML file (form-aggiungi-giocatore.html)
    }

    // Handle the form submission for adding a new player
    @PostMapping("/Admin/giocatori/aggiungi")
    public String aggiungiGiocatore(@ModelAttribute("giocatore") Giocatore giocatore, BindingResult bindingResult,Model model) {
        // Save the player to the database (this will automatically set the ID)

        giocatoreValidator.validate(giocatore, bindingResult);

        // Se ci sono errori di validazione, ritorna il form con gli errori
        if (bindingResult.hasErrors()) {
              // Redirect to the player list (change URL if needed)
            return "Admin/form-aggiungi-giocatore";
        }


        // Valida il giocatore con il validator
        giocatoreService.save(giocatore);
        return "redirect:/Admin/ListaGiocatori";
    }

    // Show list of all players (if needed)
    @GetMapping("/Admin/ListaGiocatori")
    public String mostraListaGiocatori(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Giocatore> giocatori = new ArrayList<>();
        giocatori = giocatoreService.findAll();


        model.addAttribute("authentication", customUserDetails);
        model.addAttribute("giocatori", giocatori);
        return "Admin/lista-giocatori";
    }

    /*
    @GetMapping("/Admin/cercaGiocatori")
    public String cercaGiocatori(@AuthenticationPrincipal CustomUserDetails customUserDetails,Model model, @RequestParam(value = "keyword", required = false) String keyword){
        List<Giocatore> giocatori = new ArrayList<>();
        if(keyword == null){
            giocatori = giocatoreService.findAll();
        }else {
            giocatori = giocatoreService.findByNomeSimile(keyword);
        }
        model.addAttribute("authentication", customUserDetails);
       model.addAttribute("giocatori", giocatori);
       return "Admin/lista-giocatori";
    }

    // Show list of all players (if needed)
    @GetMapping("/Admin/ListaGiocatori")
    public String mostraListaGiocatori(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Giocatore> giocatori = new ArrayList<>();
        if(keyword == null) {
            giocatori = giocatoreService.findAll();
        }else if(keyword.equals("Nome")){
            giocatori = giocatoreService.findByNome();
        }else if(keyword.equals("Ruolo")){
            giocatori = giocatoreService.findByRuolo();
        }

        model.addAttribute("authentication", customUserDetails);
        model.addAttribute("giocatori", giocatori);
        return "Admin/lista-giocatori";
    }

 */



}
