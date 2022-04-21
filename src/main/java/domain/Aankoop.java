package domain;

import javax.validation.constraints.NotEmpty;

import validator.ValidEmail;

public class Aankoop {
	@NotEmpty
	@ValidEmail
	private String email;
	private int aantalTickets = 1;
	private int voetbalcode1 = 10;
	
	private int voetbalcode2 = 20;
	
	public Aankoop() {}
	
	public Aankoop(String email, int aantalTickets, int voetbalcode1, int voetbalcode2) {
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

	public int getAantalTickets() {
		return aantalTickets;
	}

	public void setAantalTickets(int aantalTickets) {
		this.aantalTickets = aantalTickets;
	}

	public int getVoetbalcode1() {
		return voetbalcode1;
	}

	public void setVoetbalcode1(int voetbalcode1) {
		this.voetbalcode1 = voetbalcode1;
	}

	public int getVoetbalcode2() {
		return voetbalcode2;
	}

	public void setVoetbalcode2(int voetbalcode2) {
		this.voetbalcode2 = voetbalcode2;
	}
	
	
	
}
