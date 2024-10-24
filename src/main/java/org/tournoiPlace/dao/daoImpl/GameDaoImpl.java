package org.tournoiPlace.dao.daoImpl;

import org.tournoiPlace.dao.GameDao;
import org.tournoiPlace.model.Game;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GameDaoImpl implements GameDao {

    private EntityManagerFactory entityManagerFactory;

    // Setter for dependency injection
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void addGame(Game game) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();

        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public List<Game> getAllGames() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Game", Game.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Game getGameById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Game.class, id);
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public void updateGame(Game game) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(game);
            em.getTransaction().commit();
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public void deleteGame(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Game game = em.find(Game.class, id);
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
}
