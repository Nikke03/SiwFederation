package it.uniroma3.SIW_Federation.controller;



import it.uniroma3.SIW_Federation.model.Credentials;
import it.uniroma3.SIW_Federation.model.CustomUserDetails;
import it.uniroma3.SIW_Federation.model.Presidente;
import it.uniroma3.SIW_Federation.model.Squadra;
import it.uniroma3.SIW_Federation.repository.CredentialsRepository;
import it.uniroma3.SIW_Federation.service.PresidenteService;
import it.uniroma3.SIW_Federation.service.SquadraService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired private CredentialsRepository credentialsRepository;

    @Autowired private PresidenteService presidenteService;

    @Autowired private SquadraService squadraService;


    @GetMapping("/redirectByRole")
    public String redirectByRole(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null ) {
            Credentials credentials = customUserDetails.getCredentials();
            String role = credentials.getRole();

            if (Credentials.ADMIN_ROLE.equals(role)) {
                return "redirect:/";
            } else {
                return "redirect:/";
            }

        }

        // Se l'utente non è autenticato, reindirizza alla pagina di login
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            if (userDetails.getCredentials().getRole().equals("ROLE_DEFAULT") || userDetails.getCredentials().getRole().equals("DEFAULT")) {
                model.addAttribute("authentication", userDetails);
                return "Account";
            } else if (userDetails.getCredentials().getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("Bnb", userDetails);
                return "/Admin/AccountAdmin";
            }else if (userDetails.getCredentials().getRole().equals("ROLE_PRESIDENT")) {
                Presidente p = presidenteService.findById(userDetails.getCredentials().getId());
                model.addAttribute("Bnb", userDetails);
                model.addAttribute("presidente", p);
                return "/Presidente/AccountP";
            }
        }
        // Se l'utente non è autenticato, reindirizza alla pagina di login
        return "redirect:/login";
    }

    @GetMapping("/")
    public String getIndex(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("authentication", userDetails);
        List<Squadra> squadre = squadraService.getAllSquadre();
        model.addAttribute("squadre", squadre);
        return "index"; // Il nome del template Thymeleaf
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }


}
