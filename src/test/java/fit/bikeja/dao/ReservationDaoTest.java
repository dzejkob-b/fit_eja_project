package fit.bikeja.dao;

import fit.bikeja.entity.Item;
import fit.bikeja.entity.Reservation;
import fit.bikeja.entity.User;
import fit.bikeja.service.DbService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationDaoTest {

    @Inject
    DbService dbService;

    @Inject
    UserDao userDao;

    @Inject
    ItemDao itemDao;

    @Inject
    ReservationDao reservationDao;

    @BeforeAll
    @Transactional
    public void setupData() {

        this.dbService.truncateAll();

        this.userDao.save(new User("pepa", "123456", "Pepa", "Z depa"));
        this.userDao.save(new User("jarda", "123456", "Jarda", "Novotný"));

        User u1 = this.userDao.getOne(1);
        User u2 = this.userDao.getOne(2);

        this.itemDao.save(new Item("knížka", 500.0, u1));
        this.itemDao.save(new Item("varná konvice", 150.0, u1));
        this.itemDao.save(new Item("židle", 1000.0, u2));
        this.itemDao.save(new Item("šroubovák", 200.0, u2));
        this.itemDao.save(new Item("pumpička", 500.0, u2));

        this.reservationDao.save(new Reservation(new Date(), true, this.itemDao.getOne(1), u1));
        this.reservationDao.save(new Reservation(new Date(), true, this.itemDao.getOne(2), u1));
        this.reservationDao.save(new Reservation(new Date(), true, this.itemDao.getOne(3), u1));
        this.reservationDao.save(new Reservation(new Date(), true, this.itemDao.getOne(4), u1));
        this.reservationDao.save(new Reservation(new Date(), true, this.itemDao.getOne(5), u1));
    }

    @Test
    @Transactional
    public void createTest() {
        // musi byt v samostatne transactional metode - jinak se neudela lazy a predtim se neflushnou reservations

        Reservation[] rs = this.userDao.getOne(1).getReservations().toArray(new Reservation[0]);

        assertEquals("pepa", rs[0].getUser().getLoginName());
        assertEquals("pepa", rs[1].getUser().getLoginName());
        assertEquals("pepa", rs[2].getUser().getLoginName());
        assertEquals("pepa", rs[3].getUser().getLoginName());
        assertEquals("pepa", rs[4].getUser().getLoginName());

        assertEquals("knížka", rs[0].getItem().getCaption());
        assertEquals("varná konvice", rs[1].getItem().getCaption());
        assertEquals("židle", rs[2].getItem().getCaption());
        assertEquals("šroubovák", rs[3].getItem().getCaption());
        assertEquals("pumpička", rs[4].getItem().getCaption());
    }
}
