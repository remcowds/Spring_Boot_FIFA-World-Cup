package com.springBoot.FIFAWorldCup;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Aankoop;
import domain.VoetbalMatch;
import service.VoetbalMatchDao;
import service.VoetbalServiceImpl;
import validator.AankoopValidation;

@Controller
@RequestMapping("/fifa")
public class FifaController {

	@Autowired
	private VoetbalServiceImpl voetbalServiceImpl; // niet meer nodig eig dankzij JPA

	@Autowired
	private AankoopValidation aankoopValidation;

	@Autowired
	private VoetbalMatchDao voetbalMatchDao;

	// get algemeen
	@GetMapping
	public String showFormPage(Model model, @RequestParam(required = false) Integer verkocht,
			@RequestParam(required = false) boolean uitverkocht/*, Principal principal*/) {
//		System.out.println("current user: " + principal.getName());
		
		// data inserten
//		VoetbalMatchDao
//				.insert(new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 35, "Al Bayt Stadium"));
//		VoetbalMatchDao.insert(
//				new VoetbalMatch(2L, new String[] { "Brazilië", "Zwitserland" }, 27, 18, 21, "Al Bayt Stadium"));
//		VoetbalMatchDao
//				.insert(new VoetbalMatch(3L, new String[] { "Marroko", "Kroatië" }, 28, 15, 5, "Al Bayt Stadium"));
//		VoetbalMatchDao
//				.insert(new VoetbalMatch(4L, new String[] { "Spanje", "Duitsland" }, 28, 18, 95, "Al Thumama Stadium"));
//		VoetbalMatchDao.insert(
//				new VoetbalMatch(5L, new String[] { "Frankrijk", "Denemarken" }, 30, 15, 45, "Al Thumama Stadium"));
//		VoetbalMatchDao
//				.insert(new VoetbalMatch(6L, new String[] { "Argentinië", "Mexico" }, 30, 18, 10, "Al Bayt Stadium"));
//		VoetbalMatchDao.insert(new VoetbalMatch(7L, new String[] { "Engeland", "USA" }, 31, 18, 22, "Al Bayt Stadium"));
//		VoetbalMatchDao
//				.insert(new VoetbalMatch(8L, new String[] { "Nederland", "Qatar" }, 31, 21, 16, "Al Thumama Stadium"));

		List<String> stadiums = voetbalMatchDao.getUniqueStadiums();

		model.addAttribute("stadiums", stadiums);

		if (verkocht == null) {
			verkocht = 0;
		}

		model.addAttribute("verkocht", verkocht);

		model.addAttribute("uitverkocht", uitverkocht);

		return "kiesStadium";
	}

	@PostMapping
	public String onSubmit(@RequestParam("stadium") String stadium, Model model) {
		model.addAttribute("stadium", stadium);

		model.addAttribute("wedstrijden", voetbalMatchDao.getMatchByStadion(stadium));

//		List<WedstrijdTicket> wedstrijden = voetbalServiceImpl.getWedstrijdenByStadium(stadium);
//		model.addAttribute("wedstrijden", wedstrijden);

		return "overzichtMatches";
	}

	// get met parameter
	@GetMapping("/{id}")
	public String showDetailPage(@PathVariable(value = "id") String id, Model model) {
		VoetbalMatch match = voetbalMatchDao.get(Long.parseLong(id));
		
		if (match.isUitverkocht()) {
			return "redirect:/fifa?uitverkocht=true";
		}

		model.addAttribute("aantalTickets", match.getTickets());
		model.addAttribute("stadium", match.getStadion());
		model.addAttribute("land1", match.getLanden()[0]);
		model.addAttribute("land2", match.getLanden()[1]);
		model.addAttribute("dag", match.getDag());

		model.addAttribute("aankoop", new Aankoop());

		return "detailMatch";
	}

	@PostMapping("/{id}")
	@Transactional
	public String processBuyTicket(@PathVariable(value = "id") String id, @Valid Aankoop aankoop, BindingResult result,
			Model model) {

		aankoopValidation.validate(aankoop, result);

		VoetbalMatch match = voetbalMatchDao.get(Long.parseLong(id));

		if (result.hasErrors()) {
			System.out.println("ni gelukt");

			model.addAttribute("aantalTickets", match.getTickets());
			model.addAttribute("stadium", match.getStadion());
			model.addAttribute("land1", match.getLanden()[0]);
			model.addAttribute("land2", match.getLanden()[1]);
			model.addAttribute("dag", match.getDag());

			return "detailMatch";
		}

		int aantalTeBestellen = Integer.parseInt(aankoop.getAantalTickets());

		// effectief 'kopen'
//		int gekocht = voetbalServiceImpl.ticketsBestellen(id, aantalTeBestellen);
		int gekocht = match.ticketsKopen(aantalTeBestellen);

		// aantal tickets updaten in de db
		voetbalMatchDao.update(match);

		// redirecten
		return String.format("redirect:/fifa?verkocht=%d", gekocht);
	}

}
