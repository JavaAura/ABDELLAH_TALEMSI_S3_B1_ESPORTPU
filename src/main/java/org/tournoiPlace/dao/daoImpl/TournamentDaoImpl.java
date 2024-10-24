    package org.tournoiPlace.dao.daoImpl;

    import org.tournoiPlace.dao.TournamentDao;
    import org.tournoiPlace.model.Tournament;

    import javax.persistence.EntityManager;
    import javax.persistence.EntityManagerFactory;
    import java.util.Collections;
    import java.util.List;

    public class TournamentDaoImpl implements TournamentDao {
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
            }catch (RuntimeException e) {
                em.getTransaction().rollback();
                throw e;
            }finally {
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
            }catch (RuntimeException e) {
                em.getTransaction().rollback();
                throw e;
            }finally {
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
            }catch (RuntimeException e) {
                em.getTransaction().rollback();
                throw e;
            }finally {
                em.close();
            }
        }

        @Override
        public Tournament getTournament(int id) {
            EntityManager em = getEntityManager();
            try {
                return em.find(Tournament.class, id);
            }catch (RuntimeException e) {
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

        @Override
        public long calculerDureeEstimeeTournoi(Long tournoiId, int nbEquipes, long dureeMoyenneMatch, long tempsPause) {
            // Basic rule: estimated duration = (Number of teams × Average match duration) + (Time between matches)
            return (nbEquipes * dureeMoyenneMatch) + tempsPause;
        }
}
