package it.uniroma3.SIW_Federation.controller;

import it.uniroma3.SIW_Federation.model.*;
import it.uniroma3.SIW_Federation.repository.DisposizioneRepository;
import it.uniroma3.SIW_Federation.repository.GiocatoreRepository;
import it.uniroma3.SIW_Federation.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/presidente/squadre")
public class PresidenteSquadraController {

    @Autowired
    private SquadraService squadraService;

    @Autowired
    private PresidenteService presidenteService;

    @Autowired
    private GiocatoreService giocatoreService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private DisposizioneService disposizioneService;

    @Autowired
    private DisposizioneRepository disposizioneRepository;

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @GetMapping("/gestisci_squadra")
    public String gestisciSquadra(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {


        if ( !(loggedUser.getCredentials().getRole().equals("ROLE_PRESIDENT"))) {
            // Gestisci il caso in cui il presidente non esiste
            return "redirect:/error";
        }

        // Recupera la squadra associata al presidente
        Squadra squadra = presidenteService.findByUtenteId( loggedUser.getUtente().getId()).getSquadra();

        if (squadra == null) {
            // Gestisci il caso in cui la squadra non è ancora assegnata
            return "redirect:/error";
        }

        // Recupera i giocatori disponibili (senza squadra e senza tesseramento)
        List<Giocatore> giocatoriDisponibili = giocatoreService.findGiocatoriDisponibili();
        model.addAttribute("authentication", loggedUser);
        model.addAttribute("giocatoriDisponibili", giocatoriDisponibili);
        model.addAttribute("squadra", squadra);
        model.addAttribute("presidente", presidenteService.findByUtenteId( loggedUser.getUtente().getId()));

        return "Presidente/gestisci-squadra";
    }



    @PostMapping("/aggiungi_giocatore")
    public String aggiungiGiocatoreASquadra(@RequestParam("giocatoreId") Long giocatoreId, @AuthenticationPrincipal CustomUserDetails loggedUser) {

        // Verifica se l'utente loggato è un presidente
        if (!loggedUser.getCredentials().getRole().equals("ROLE_PRESIDENT")) {
            return "redirect:/error";
        }

        // Recupera la squadra associata al presidente
        Squadra squadra = presidenteService.findByUtenteId(loggedUser.getUtente().getId()).getSquadra();

        if (squadra == null) {
            return "redirect:/error";
        }

        // Recupera il giocatore selezionato
        Giocatore giocatore = giocatoreService.findById(giocatoreId);

        if (giocatore == null) {
            return "redirect:/error?giocatore_non_esiste";
        }

        // Verifica che il giocatore non sia già assegnato a una squadra nel periodo
        if (giocatore.getDataInizioTesseramento() != null || giocatore.getDataFineTesseramento() != null) {
            return "redirect:/presidente/gestisci_squadra?error=giocatore_non_disponibile";
        }

        // Usa il metodo del servizio per aggiungere il giocatore alla squadra
        squadraService.aggiungiGiocatore(squadra, giocatore);

        return "redirect:/presidente/squadre/gestisci_squadra";
    }

    @PostMapping("/rimuovi_tesseramento")
    public String rimuoviTesseramento(@RequestParam("giocatoreId") Long giocatoreId, @AuthenticationPrincipal CustomUserDetails loggedUser) {
        // Verifica se l'utente loggato è un presidente
        if (!loggedUser.getCredentials().getRole().equals("ROLE_PRESIDENT")) {
            return "redirect:/error";
        }

        // Recupera la squadra associata al presidente
        Squadra squadra = presidenteService.findByUtenteId(loggedUser.getUtente().getId()).getSquadra();

        if (squadra == null) {
            return "redirect:/error";
        }

        // Recupera il giocatore
        Giocatore giocatore = giocatoreService.findById(giocatoreId);

        if (giocatore == null) {
            return "redirect:/error?giocatore_non_esiste";
        }

        // Rimuovi il tesseramento
        giocatore.setSquadra(null);
        giocatore.setDataInizioTesseramento(null);
        giocatore.setDataFineTesseramento(null);

        // Salva il giocatore aggiornato
        giocatoreService.save(giocatore);

        return "redirect:/presidente/squadre/gestisci_squadra";
    }

    @GetMapping("/disposizione/crea")
    public String showDisposizioneForm(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
        Presidente presidente = presidenteService.findByUtenteId(loggedUser.getUtente().getId());

        // Prepara una nuova disposizione vuota
        Disposizione disposizione = new Disposizione();

        // Assicurati che i moduli siano aggiunti correttamente al modello
        model.addAttribute("moduli", Modulo.values());

        // Aggiungi la disposizione vuota al modello
        model.addAttribute("disposizione", disposizione);

        // Aggiungi i giocatori disponibili della squadra del presidente
        model.addAttribute("giocatori", giocatoreService.findGiocatoriBySquadra(presidente.getSquadra()));

        return "/Presidente/disposizione-form";  // La vista dove il presidente può scegliere modulo e assegnare giocatori
    }


    @PostMapping("/disposizione/crea")
    public String creaDisposizione(@RequestParam("modulo") Modulo modulo, @AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
        Disposizione disposizione = new Disposizione();
        disposizione.setModulo(modulo);
        Presidente presidente = presidenteService.findByUtenteId( loggedUser.getUtente().getId());

        // Aggiungi la disposizione vuota al modello
        model.addAttribute("disposizione", disposizione);
        // Aggiungi i giocatori disponibili della squadra
        model.addAttribute("giocatori", giocatoreService.findGiocatoriBySquadra(presidente.getSquadra()));

        return "disposizione-form";  // La vista dove il presidente può trascinare i giocatori
    }

    @PostMapping("/disposizione/salva")
    public String salvaDisposizione(@ModelAttribute Disposizione disposizione) {
        // Save or process the Disposizione object with the selected modulo
        disposizioneService.save(disposizione);

        return "redirect:/presidente/successo";
    }

    @PostMapping("/salva-disposizione")
    @ResponseBody
    public ResponseEntity<Map<String, String>> salvaDisposizione(@RequestBody Map<String, Object> payload) {
        System.out.println("Payload received: " + payload);  // Debug log

        Map<String, String> response = new HashMap<>();
        if (!payload.containsKey("disposizione") || payload.get("disposizione") == null) {
            response.put("status", "error");
            response.put("message", "Disposizione non trovata");
            return ResponseEntity.badRequest().body(response);
        }

        List<Map<String, String>> disposizione = (List<Map<String, String>>) payload.get("disposizione");

        if (disposizione.isEmpty()) {
            response.put("status", "success");
            response.put("message", "Disposizione salvata parzialmente: nessun giocatore posizionato");
            return ResponseEntity.ok(response);
        }

        System.out.println("Received Disposizione: " + disposizione);  // Log the data

        try {
            for (Map<String, String> posizionamento : disposizione) {
                String posizioneCampo = posizionamento.get("posizioneCampo");
                Long giocatoreId = Long.parseLong(posizionamento.get("giocatoreId"));

                saveDisposizioneGiocatore(giocatoreId, posizioneCampo);
            }

            response.put("status", "success");
            response.put("message", "Disposizione salvata con successo");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            response.put("status", "error");
            response.put("message", "Errore durante il salvataggio della disposizione");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }






    private void saveDisposizioneGiocatore(Long giocatoreId, String posizioneCampo) throws EntityNotFoundException {
        Optional<Giocatore> giocatoreOpt = giocatoreRepository.findById(giocatoreId);
        if (giocatoreOpt.isPresent()) {
            Giocatore giocatore = giocatoreOpt.get();

            // Crea o aggiorna l'entità di disposizione del giocatore
            Disposizione disposizione = new Disposizione();
            disposizione.setGiocatore(giocatore);
            disposizione.setPosizioneCampo(posizioneCampo);

            // Salva la disposizione
            disposizioneRepository.save(disposizione);
        } else {
            throw new EntityNotFoundException("Giocatore con ID " + giocatoreId + " non trovato.");
        }
    }





}


