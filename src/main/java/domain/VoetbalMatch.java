package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "VoetbalMatch.getMatchByStadion", query = "select m from VoetbalMatch m where m.stadion = :stadion"),
		@NamedQuery(name = "VoetbalMatch.getUniqueStadiums", query = "select distinct stadion from VoetbalMatch") })
public class VoetbalMatch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String[] landen;
	private int dag;
	private int uur;
	private int tickets;
	private String stadion;

	public VoetbalMatch() {

	}

	public VoetbalMatch(Long id, String[] landen, int dag, int uur, int tickets, String stadion) {
		this.id = id;
		this.landen = landen;
		this.dag = dag;
		this.uur = uur;
		this.tickets = tickets;
		this.stadion = stadion;
	}

	public Long getId() {
		return id;
	}

	public int getTickets() {
		return tickets;
	}

	public String[] getLanden() {
		return landen;
	}

	public int getDag() {
		return dag;
	}

	public int getUur() {
		return uur;
	}

	public String getStadion() {
		return stadion;
	}

	// We willen 'aantal' tickets kopen
	public int ticketsKopen(int aantal) {
		if (aantal <= 0) {
			return -1;
		}

		// Nog voldoende tickets
		if (tickets >= aantal) {
			tickets -= aantal;
			return aantal;
		}

		// Niet meer voldoende tickets
		int gekocht = tickets;
		tickets = 0;
		return gekocht;
	}
	
	public boolean isUitverkocht() {
		return this.tickets == 0;
	}

}
