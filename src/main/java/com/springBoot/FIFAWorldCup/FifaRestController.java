package com.springBoot.FIFAWorldCup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.VoetbalMatch;
import service.VoetbalMatchDao;

@RestController
@RequestMapping("/fifaDetail")
public class FifaRestController {

	@Autowired
	private VoetbalMatchDao voetbalMatchDao;

	@GetMapping("/{id}")
	public List<String> getCountriesFromMatch(@PathVariable(value = "id") String id) {
		VoetbalMatch match = voetbalMatchDao.get(Long.parseLong(id));
		return new ArrayList<>(Arrays.asList(match.getLanden()));
	}
}