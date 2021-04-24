package core.parcer;

import core.hibernate.HibernateEntityManagerFactory;
import entity.dto.Serial;
import entity.repo.SerialRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.List;


import static spark.Spark.get;
import static spark.Spark.port;

public class TestSpark {
    private static EntityManager em;

    public TestSpark() {
        em = HibernateEntityManagerFactory.getEntityManager();
    }

    public static void main(String[] args) {

        get("/hello", (req, res) -> getSerialByName("Danger"));
    }

    public static Serial getSerialByName(String name) {
        List<Serial> serials = em.createQuery("SELECT s FROM Serial s WHERE s.name = :name", Serial.class)
                .setParameter("name", name)
                .getResultList();

        if (serials.isEmpty()) {
            return null;
        } else {
            return serials.get(0);
        }
    }
}
