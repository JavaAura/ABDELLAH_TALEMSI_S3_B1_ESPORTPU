package org.tournoiPlace.dao.daoImpl;

import org.tournoiPlace.dao.PlayerDao;
import org.tournoiPlace.model.Game;
import org.tournoiPlace.model.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {
    private EntityManagerFactory entityManagerFactory;

    // Setter for dependency injection
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void addPlayer(Player player) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();

        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }

    }

    @Override
    public void updatePlayer(Player player) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(player);
            em.getTransaction().commit();
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }

    }

    @Override
    public void deletePlayer(int playerId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Player player = em.find(Player.class, playerId);
            if (player != null) {
                em.remove(player);
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
    public Player getPlayer(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Player.class, id);
        }catch (RuntimeException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("FROM Player ", Player.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
