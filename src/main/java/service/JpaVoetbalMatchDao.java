package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import domain.VoetbalMatch;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("VoetbalMatchDao")
public class JpaVoetbalMatchDao extends GenericDaoJpa<VoetbalMatch> implements VoetbalMatchDao {

	@PersistenceContext
	private EntityManager em;

	public JpaVoetbalMatchDao() {
		super(VoetbalMatch.class);
	}

	@Override
	public List<VoetbalMatch> getMatchByStadion(String stadion) {
		TypedQuery<VoetbalMatch> queryMatch = em.createNamedQuery("VoetbalMatch.getMatchByStadion", VoetbalMatch.class);
		queryMatch.setParameter("stadion", stadion);
		return queryMatch.getResultList();
	}

	@Override
	public List<String> getUniqueStadiums() {
		TypedQuery<String> stadiums = em.createNamedQuery("VoetbalMatch.getUniqueStadiums", String.class);
		return stadiums.getResultList();
	}

//	@Override
//	@Transactional(readOnly = true)
//	public List<VoetbalMatch> findAll() {
//		TypedQuery<VoetbalMatch> query = em.createNamedQuery("VoetbalMatch.findAll", VoetbalMatch.class);
//		System.out.println("yes");
//		return query.getResultList();
//	}
//
//	@Override
//	@Transactional
//	public VoetbalMatch updateMatch(VoetbalMatch match) {
//		return em.merge(match);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public VoetbalMatch getMatch(Long id) {
//		return em.find(VoetbalMatch.class, id);
//	}
//
//	@Override
//	public void deleteMatch(VoetbalMatch match) {
//		em.remove(em.merge(match));
//	}
//
//	@Override
//	@Transactional
//	public void insertMatch(VoetbalMatch match) {
//		em.persist(match);
//	}

}
