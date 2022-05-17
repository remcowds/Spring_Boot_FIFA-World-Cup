package com.springBoot.FIFAWorldCup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import domain.Aankoop;
import domain.VoetbalMatch;
import service.VoetbalMatchDao;
import validator.AankoopValidation;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
public class FifaControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	// voetbalMatchDao mocken
	private FifaController controller;

	@Mock
	private VoetbalMatchDao voetbalMatchDaoMock;

	// aankoopvalidation ook mocken
	@Mock
	private AankoopValidation aankoopValidationMock;

	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new FifaController();
		mockMvc = standaloneSetup(controller).build();
	}

	@Test
	public void loadHomePage() throws Exception {
		// mock trainen
		Mockito.when(voetbalMatchDaoMock.getUniqueStadiums())
				.thenReturn(new ArrayList<>(List.of("Al Bayt Stadium", "Al Thumama Stadium")));
		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);

		mockMvc.perform(get("/fifa")).andExpect(status().isOk()).andExpect(view().name("kiesStadium"))
				.andExpect(model().attributeExists("stadiums")).andExpect(model().attribute("uitverkocht", false))
				.andExpect(model().attribute("verkocht", 0));
	}

	@Test
	public void loadHomePageBought1() throws Exception {
		// mock trainen
		Mockito.when(voetbalMatchDaoMock.getUniqueStadiums())
				.thenReturn(new ArrayList<>(List.of("Al Bayt Stadium", "Al Thumama Stadium")));

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);

		mockMvc.perform(get("/fifa").param("verkocht", "1")).andExpect(status().isOk())
				.andExpect(view().name("kiesStadium")).andExpect(model().attributeExists("stadiums"))
				.andExpect(model().attribute("uitverkocht", false)).andExpect(model().attribute("verkocht", 1));
	}

	@Test
	public void loadHomePageSoldOut() throws Exception {
		// mock trainen
		Mockito.when(voetbalMatchDaoMock.getUniqueStadiums())
				.thenReturn(new ArrayList<>(List.of("Al Bayt Stadium", "Al Thumama Stadium")));

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);

		mockMvc.perform(get("/fifa").param("uitverkocht", "true")).andExpect(status().isOk())
				.andExpect(view().name("kiesStadium")).andExpect(model().attributeExists("stadiums"))
				.andExpect(model().attribute("uitverkocht", true)).andExpect(model().attribute("verkocht", 0));
	}

	@Test
	public void kiesStadion() throws Exception {
		List<VoetbalMatch> temp = new ArrayList<>(List.of(
				new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 35, "Al Bayt Stadium"),
				new VoetbalMatch(2L, new String[] { "Brazilië", "Zwitserland" }, 27, 18, 21, "Al Bayt Stadium")));

		// mock trainen
		Mockito.when(voetbalMatchDaoMock.getMatchByStadion("Al Bayt Stadium")).thenReturn(temp);

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);

		mockMvc.perform(post("/fifa").param("stadium", "Al Bayt Stadium")).andExpect(status().isOk())
				.andExpect(view().name("overzichtMatches")).andExpect(model().attribute("stadium", "Al Bayt Stadium"))
				.andExpect(model().attributeExists("wedstrijden"));
	}

	@Test
	public void showDetailPageButSoldOut() throws Exception {
		VoetbalMatch temp = new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 0, "Al Bayt Stadium");

		// mock trainen
		Mockito.when(voetbalMatchDaoMock.get(1L)).thenReturn(temp);

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);

		// moet dus mocken da match me id = 1 uitverkocht is
		mockMvc.perform(get("/fifa/1")).andExpect(redirectedUrl("/fifa?uitverkocht=true"));
	}

	@Test
	public void showDetailPage() throws Exception {
		VoetbalMatch temp = new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 35, "Al Bayt Stadium");

		// mock trainen
		Mockito.when(voetbalMatchDaoMock.get(1L)).thenReturn(temp);

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);

		// mocken da match met id=1 zoveel tickets heeft, wat de
		mockMvc.perform(get("/fifa/1")).andExpect(status().isOk()).andExpect(view().name("detailMatch"))
				.andExpect(model().attribute("aantalTickets", 35))
				.andExpect(model().attribute("stadium", "Al Bayt Stadium"))
				.andExpect(model().attribute("land1", "België")).andExpect(model().attribute("land2", "Canada"))
				.andExpect(model().attribute("dag", 26)).andExpect(model().attributeExists("aankoop"));
	}

	@Test
	public void processBuyTicketNoErrors() throws Exception {
		VoetbalMatch temp = new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 35, "Al Bayt Stadium");

		// mock trainen
		Mockito.when(voetbalMatchDaoMock.get(1L)).thenReturn(temp);

		// als de update gedaan wordt, gewoon niks doen, dus ook ni nodig om mock te
		// trainen

		// validator hier niet trainen: ervan uitgaan dat in orde is (cuz no error test)

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);
		ReflectionTestUtils.setField(controller, "aankoopValidation", aankoopValidationMock);

		mockMvc.perform(post("/fifa/1").flashAttr("aankoop", new Aankoop("remco@email.com", "1", "10", "20")))
				.andExpect(redirectedUrl("/fifa?verkocht=1"));
	}

	@Test
	public void processBuyTicketEmptyEmailError() throws Exception {
		VoetbalMatch tempMatch = new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 35,
				"Al Bayt Stadium");
		Aankoop tempAankoop = new Aankoop("", "1", "10", "20");

		// mock trainen
		Mockito.when(voetbalMatchDaoMock.get(1L)).thenReturn(tempMatch);

		// aankoopvalidation mock trainen
		BindingResult br = Mockito.mock(BindingResult.class);
		Mockito.doAnswer(invocation -> {
			// hier dan doen wa er gebeurt bij foute email
			br.rejectValue("email", "ValidEmail", "geen geldige email");
			return null;
		}).when(aankoopValidationMock).validate(tempAankoop, br);

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);
		ReflectionTestUtils.setField(controller, "aankoopValidation", aankoopValidationMock);

		mockMvc.perform(post("/fifa/1").flashAttr("aankoop", tempAankoop)).andExpect(view().name("detailMatch"))
				.andExpect(model().attribute("aantalTickets", tempMatch.getTickets()))
				.andExpect(model().attribute("stadium", tempMatch.getStadion()))
				.andExpect(model().attribute("land1", tempMatch.getLanden()[0]))
				.andExpect(model().attribute("land2", tempMatch.getLanden()[1]))
				.andExpect(model().attribute("dag", tempMatch.getDag())).andExpect(model().hasErrors());
	}

	@Test
	public void processBuyTicketNotNumericValuesError() throws Exception {
		VoetbalMatch tempMatch = new VoetbalMatch(1L, new String[] { "België", "Canada" }, 26, 21, 35,
				"Al Bayt Stadium");
		Aankoop tempAankoop = new Aankoop("remco@email.com", "d", "dsf", "sqdf");

		// mock trainen
		Mockito.when(voetbalMatchDaoMock.get(1L)).thenReturn(tempMatch);

		// aankoopvalidation mock trainen
		BindingResult br = Mockito.mock(BindingResult.class);
		Mockito.doAnswer(invocation -> {
			// hier dan doen wa er gebeurt bij foute email
			br.rejectValue("aantalTickets", "must.be.numeric", "moet uit getallen bestaan");
			return null;
		}).when(aankoopValidationMock).validate(tempAankoop, br);

		// injectie
		ReflectionTestUtils.setField(controller, "voetbalMatchDao", voetbalMatchDaoMock);
		ReflectionTestUtils.setField(controller, "aankoopValidation", aankoopValidationMock);

		mockMvc.perform(post("/fifa/1").flashAttr("aankoop", tempAankoop)).andExpect(view().name("detailMatch"))
				.andExpect(model().attribute("aantalTickets", tempMatch.getTickets()))
				.andExpect(model().attribute("stadium", tempMatch.getStadion()))
				.andExpect(model().attribute("land1", tempMatch.getLanden()[0]))
				.andExpect(model().attribute("land2", tempMatch.getLanden()[1]))
				.andExpect(model().attribute("dag", tempMatch.getDag())).andExpect(model().hasErrors());

	}

}
