package org.tournoiPlace.dao.daoImpl;


import org.tournoiPlace.dao.TeamDao;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.model.Team;
import org.tournoiPlace.model.Tournament;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class TeamDaoImpl implements TeamDao {
    private EntityManagerFactory entityManagerFactory;

    // Setter for dependency injection
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    @Override
    public void addTeam(Team team) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(team);
            em.getTransaction().commit();

        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }

    }

    @Override
    public void updateTeam(Team team) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(team);
            em.getTransaction().commit();
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }

    }

    @Override
    public void deleteTeam(int teamId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Game game = em.find(Game.class, teamId);
            if (game != null) {
                em.remove(game);
            }
            em.getTransaction().commit();
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }

    }

    @Override
    public Team getTeamById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Team.class, id);
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Team> getAllTeams() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Team ", Team.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Team findByName(String name) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t WHERE t.nom = :name", Team.class);
            query.setParameter("name", name);
            return query.getResultStream().findFirst().orElse(null);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
