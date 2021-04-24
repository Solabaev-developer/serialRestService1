package core.hibernate;

import javax.persistence.EntityManager;

public class HibernateEntityManagerFactory {

    private static EntityManager em = null;

    private HibernateEntityManagerFactory() {
    }

    public static EntityManager getEntityManager() {
        if (em != null) {
            return em;
        }
        em = HibernateSessionFactory.getSessionFactory().createEntityManager();
        return em;
    }
}
