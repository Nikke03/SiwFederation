package it.uniroma3.SIW_Federation.Validator;


import it.uniroma3.SIW_Federation.model.Giocatore;
import it.uniroma3.SIW_Federation.model.Squadra;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class SquadraValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Squadra.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Squadra squadra = (Squadra) target;

        if(squadra.getNome().length()<4) {
            errors.rejectValue("nome", "nome.tooShort", "Il nome deve essere almeno di 4 caratteri");
        }

        // Controlla che la data di nascita non sia vuota e sia valida
        if (squadra.getAnnoFondazione() == 0) {
            errors.rejectValue("AnnoFondazione", "squadra.annoFondazione", "La'anno di fondazione è obbligatorio.");
        } else if (squadra.getAnnoFondazione() > 2024) {
            errors.rejectValue("AnnoFondazione", "squadra.annoFondazione", "L'anno di fondazione non può essere nel futuro.");
        }


        // Controlla che il luogo di nascita non sia vuoto
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "indirizzoSede", "squadra.indirizzoSede", "l'indirizzo di sede è obbligatorio.");


    }
}