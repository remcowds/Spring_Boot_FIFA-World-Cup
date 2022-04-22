package com.springBoot.FIFAWorldCup;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Aankoop;
import domain.WedstrijdTicket;
import service.VoetbalServiceImpl;
import validator.AankoopValidation;

@Controller
@RequestMapping("/fifa")
public class FifaController {

	@Autowired
	private VoetbalServiceImpl voetbalServiceImpl;
	
	@Autowired
	private AankoopValidation aankoopValidation;

	// get algemeen
	@GetMapping
	public String showFormPage(Model model) {
		List<String> stadiums = voetbalServiceImpl.getStadiumList();

		model.addAttribute("stadiums", stadiums);

		return "kiesStadium";
	}

	@PostMapping
	public String onSubmit(@RequestParam("stadium") String stadium, Model model) {
		model.addAttribute("stadium", stadium);

		List<WedstrijdTicket> wedstrijden = voetbalServiceImpl.getWedstrijdenByStadium(stadium);

		model.addAttribute("wedstrijden", wedstrijden);

		return "overzichtMatches";
	}

	// get met parameter
	@GetMapping("/{id}")
	public String showDetailPage(@PathVariable(value = "id") String id, Model model) {
		model.addAttribute("aantalTickets", voetbalServiceImpl.getWedstrijd(id).getTickets());
		model.addAttribute("stadium", voetbalServiceImpl.getStadiumFromWedstrijd(id));
		model.addAttribute("land1", voetbalServiceImpl.getWedstrijd(id).getWedstrijd().getLanden()[0]);
		model.addAttribute("land2", voetbalServiceImpl.getWedstrijd(id).getWedstrijd().getLanden()[1]);
		model.addAttribute("dag", voetbalServiceImpl.getWedstrijd(id).getWedstrijd().getDag());
		
		model.addAttribute("aankoop", new Aankoop());
		
		return "detailMatch";
	}
	
	@PostMapping("/{id}")
	public String processBuyTicket(@PathVariable(value = "id") String id, @Valid Aankoop aankoop, BindingResult result, Model model) {		
		
		aankoopValidation.validate(aankoop, result);
		
		if(result.hasErrors()) {
			System.out.println("ni gelukt");
			
			model.addAttribute("aantalTickets", voetbalServiceImpl.getWedstrijd(id).getTickets());
			model.addAttribute("stadium", voetbalServiceImpl.getStadiumFromWedstrijd(id));
			model.addAttribute("land1", voetbalServiceImpl.getWedstrijd(id).getWedstrijd().getLanden()[0]);
			model.addAttribute("land2", voetbalServiceImpl.getWedstrijd(id).getWedstrijd().getLanden()[1]);
			model.addAttribute("dag", voetbalServiceImpl.getWedstrijd(id).getWedstrijd().getDag());
						
			return "detailMatch";
		}
		
		System.out.println("yeet gelukt");
		return "test";
	}

}
