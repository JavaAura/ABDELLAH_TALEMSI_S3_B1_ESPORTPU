package org.tournoiPlace.dao.daoImpl;

import org.tournoiPlace.dao.TournamentDao;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.model.Tournament;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TournamentDaoExtension implements TournamentDao {
    private EntityManagerFactory entityManagerFactory;

    // Setter for dependency injection
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void addTournament(Tournament tournament) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tournament);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void updateTournament(Tournament tournament) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tournament);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }


    }

    @Override
    public void deleteTournament(Tournament tournament) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Tournament tour = em.find(Tournament.class, tournament.getId());
            if (tour != null) {
                em.remove(tour);
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Tournament getTournament(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournament.class, id);
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Tournament> getTournaments() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Tournament", Tournament.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
    public List<Team> findTeamsByTournoiId(int tournoiId) {
        EntityManager em = getEntityManager();
        try {
            // Use a typed query to fetch the teams associated with the tournament ID
            TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.tournament.id = :tournoiId", Team.class);
            query.setParameter("tournoiId", tournoiId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Game findGameByTournoiId(int tournoiId) {
        EntityManager em = getEntityManager();
        try {
            Tournament tournament = em.find(Tournament.class, tournoiId);
            if (tournament != null) {
                return tournament.getGame();
            } else {
                throw new IllegalArgumentException("Tournament not found");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public double calculerDureeEstimeeTournoi(int tournoiId) {
        Tournament tournoi = this.getTournament(tournoiId);
        if (tournoi == null) {
            throw new IllegalArgumentException("Tournament not found");
        }

        // Step 2: Get the participating teams for this tournament
        List<Team> teamsParticipating = this.findTeamsByTournoiId(tournoiId);
        int nbEquipes = teamsParticipating.size();


        int nbMatchs = nbEquipes - 1;

        Game game = this.findGameByTournoiId(tournoiId);

        // Step 4: Apply the formula

        double durre_estimate = (nbMatchs * game.getDureeMoyenneMatch()) + ((nbMatchs - 1) * tournoi.getTempsPauseEntreMatchs()) + (tournoi.getTempsCeremonie());

         tournoi.setDureeEstimee(durre_estimate);
         this.updateTournament(tournoi);
        return (nbMatchs * game.getDureeMoyenneMatch()) + ((nbMatchs - 1) * tournoi.getTempsPauseEntreMatchs()) + (tournoi.getTempsCeremonie());
    }

    @Override
    public Tournament findByName(String name) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery<Tournament> query = em.createQuery("SELECT t FROM Tournament t WHERE t.titre = :name", Tournament.class);
            query.setParameter("name", name);
            return query.getResultStream().findFirst().orElse(null);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
    }

}

