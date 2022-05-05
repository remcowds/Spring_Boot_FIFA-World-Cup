package service;

import java.util.List;

import domain.VoetbalMatch;

public interface VoetbalMatchDao extends GenericDao<VoetbalMatch> {
	public List<VoetbalMatch> getMatchByStadion(String stadion);

	public List<String> getUniqueStadiums();
}
