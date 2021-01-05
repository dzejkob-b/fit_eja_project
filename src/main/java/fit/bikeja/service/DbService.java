package fit.bikeja.service;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class DbService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void flushAndClean() {
        this.em.flush();
        this.em.clear();
    }

    @Transactional
    public void truncateAll() {
        // reset kompletniho stavu databaze (pouze pro testy)

        this.em.createNativeQuery("DELETE FROM RESERVATION").executeUpdate();
        this.em.createNativeQuery("DELETE FROM ITEM").executeUpdate();
        this.em.createNativeQuery("DELETE FROM USER").executeUpdate();

        this.em.createNativeQuery("ALTER TABLE RESERVATION ALTER COLUMN id RESTART WITH 1").executeUpdate();
        this.em.createNativeQuery("ALTER TABLE ITEM ALTER COLUMN id RESTART WITH 1").executeUpdate();
        this.em.createNativeQuery("ALTER TABLE USER ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

}
