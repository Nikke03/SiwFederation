package it.uniroma3.SIW_Federation.controller;

import it.uniroma3.SIW_Federation.model.Squadra;
import it.uniroma3.SIW_Federation.service.SquadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FederationController {

    @Autowired
    private SquadraService squadraService;




}
