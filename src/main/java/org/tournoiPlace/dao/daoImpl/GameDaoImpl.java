package org.tournoiPlace.dao.daoImpl;

import org.tournoiPlace.dao.GameDao;
import org.tournoiPlace.model.Game;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
}
