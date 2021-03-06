package domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import validator.ValidEmail;

public class Aankoop {
	@NotEmpty
	@ValidEmail
	private String email;

	//MOET IK NOG MET DIE VALUE DOEN, IPV HARDCODED 1 EN 25
	
	@NotEmpty
	@Min(value = 1, message = "{aantal.tickets.groter.gelijk}")
	@Max(value = 25, message = "{aantal.tickets.kleiner}")
	private String aantalTickets = "1";

	@NotEmpty
	private String voetbalcode1 = "10";

	@NotEmpty
	private String voetbalcode2 = "20";

	public Aankoop() {
	}

	public Aankoop(String email, String aantalTickets, String voetbalcode1, String voetbalcode2) {
		super();
		this.email = email;
		this.aantalTickets = aantalTickets;
		this.voetbalcode1 = voetbalcode1;
		this.voetbalcode2 = voetbalcode2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAantalTickets() {
		return aantalTickets;
	}

	public void setAantalTickets(String aantalTickets) {
		this.aantalTickets = aantalTickets;
	}

	public String getVoetbalcode1() {
		return voetbalcode1;
	}

	public void setVoetbalcode1(String voetbalcode1) {
		this.voetbalcode1 = voetbalcode1;
	}

	public String getVoetbalcode2() {
		return voetbalcode2;
	}

	public void setVoetbalcode2(String voetbalcode2) {
		this.voetbalcode2 = voetbalcode2;
	}

}
