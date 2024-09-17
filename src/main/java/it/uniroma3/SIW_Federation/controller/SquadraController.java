package it.uniroma3.SIW_Federation.controller;

import it.uniroma3.SIW_Federation.model.Presidente;
import it.uniroma3.SIW_Federation.model.Squadra;
import it.uniroma3.SIW_Federation.service.PresidenteService;
import it.uniroma3.SIW_Federation.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/squadre")
public class SquadraController {

    @Autowired
    private SquadraService squadraService;

    @Autowired
    private PresidenteService presidenteService;

    @GetMapping
    public String getAllSquadre(Model model) {
        List<Squadra> squadre = squadraService.getAllSquadre();
        model.addAttribute("squadre", squadre);
        return "squadre/list";  // Questo indirizza a src/main/resources/templates/squadre/list.html
    }

    @GetMapping("/{id}")
    public String getSquadraInfo(@PathVariable("id") Long id, Model model) {
        // Find the team by ID
        Squadra squadra = squadraService.findById(id);

        if (squadra == null) {
            return "redirect:/error";  // Handle case when team doesn't exist
        }
        /*
        Long att = squadraService.countByAttaccanti(squadraService.findById(id));
        Long dif = squadraService.countByDifensori(squadraService.findById(id));
        Long cent = squadraService.countByCentrocampisti(squadraService.findById(id));
        Long por = squadraService.countByPortiere(squadraService.findById(id));

         */
        // Add the team and its players to the model
        model.addAttribute("presidente", squadra.getPresidente());
        model.addAttribute("squadra", squadra);
        model.addAttribute("giocatori", squadra.getGiocatori());
        /*
        model.addAttribute("counterAttaccanti", att);
        model.addAttribute("counterDifensori", dif);
        model.addAttribute("counterACentrocampisti", cent);
        model.addAttribute("counterPortieri", por);

         */

        return "squadra-info";  // This will be the new view page for team details
    }

    // Metodo per mostrare la lista delle squadre
    @GetMapping("/list")
    public String listaSquadre(Model model) {
        List<Squadra> squadre = squadraService.getAllSquadre();

        model.addAttribute("squadre", squadre);
        return "Admin/squadre-list"; // Nome della vista HTML
    }

    // Metodo per modificare una squadra e cambiare presidente
    @GetMapping("/modifica/{id}")
    public String mostraModificaSquadra(@PathVariable("id") Long squadraId, Model model) {
        Squadra squadra = squadraService.findById(squadraId);
        List<Presidente> presidentiDisponibili = presidenteService.getPresidentiSenzaSquadra();
        model.addAttribute("squadra", squadra);
        model.addAttribute("presidentiDisponibili", presidentiDisponibili);
        return "Admin/modifica-squadra"; // Nome della vista HTML
    }

    // Metodo POST per salvare il nuovo presidente della squadra
    @PostMapping("/modifica/{id}")
    public String salvaModificaSquadra(@PathVariable("id") Long squadraId, @RequestParam("presidenteId") Long presidenteId) {
        squadraService.aggiornaPresidente(squadraId, presidenteId);
        return "redirect:/squadre/list"; // Ritorna alla lista delle squadre
    }


    @PostMapping("/modifica-squadra/{id}/cambia-presidente")
    public String cambiaPresidente(@PathVariable("id") Long squadraId,
                                   @RequestParam("nuovoPresidenteId") Long nuovoPresidenteId,
                                   RedirectAttributes redirectAttributes) {
        try {
            squadraService.cambiaPresidente(squadraId, nuovoPresidenteId);
            redirectAttributes.addFlashAttribute("successMessage", "Presidente cambiato con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Errore durante il cambio di presidente: " + e.getMessage());
        }
        return "redirect:/squadra/modifica-squadra/" + squadraId;
    }
}
