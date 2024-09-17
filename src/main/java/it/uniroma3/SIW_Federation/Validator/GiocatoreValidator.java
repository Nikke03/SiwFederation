package it.uniroma3.SIW_Federation.Validator;


import it.uniroma3.SIW_Federation.model.Giocatore;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class GiocatoreValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Giocatore.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Giocatore giocatore = (Giocatore) target;

        if(giocatore.getNome().length()<4) {
            errors.rejectValue("nome", "nome.tooShort", "Il nome deve essere almeno di 4 caratteri");
        }
        if(giocatore.getCognome().length()<4) {
            errors.rejectValue("cognome", "cognome.tooShort", "Il cognome deve essere almeno di 4 caratteri");
        }
        // Controlla che la data di nascita non sia vuota e sia valida
        if (giocatore.getDataNascita() == null) {
            errors.rejectValue("dataNascita", "giocatore.dataNascita", "La data di nascita è obbligatoria.");
        } else if (giocatore.getDataNascita().isAfter(LocalDate.now())) {
            errors.rejectValue("dataNascita", "giocatore.dataNascita", "La data di nascita non può essere nel futuro.");
        }
        else {
            if (giocatore.getDataNascita().isAfter(LocalDate.now().minusYears(18))) {
                errors.rejectValue("dataNascita", "giocatore.dataNascita.maggiorenne", "Il giocatore deve essere maggiorenne.");
            }
        }
        // Controlla che il luogo di nascita non sia vuoto
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "luogoNascita", "giocatore.luogoNascita", "Il luogo di nascita è obbligatorio.");

        // Controlla che il ruolo sia selezionato
        if (giocatore.getRuolo() == null || giocatore.getRuolo().isEmpty()) {
            errors.rejectValue("ruolo", "giocatore.ruolo", "Il ruolo è obbligatorio.");
        }
    }
}