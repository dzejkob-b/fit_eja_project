package fit.bikeja.dao;

import fit.bikeja.dao.ItemDao;
import fit.bikeja.dao.UserDao;
import fit.bikeja.entity.Item;
import fit.bikeja.entity.User;
import fit.bikeja.service.DbService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemDaoTest {

    @Inject
    DbService dbService;

    @Inject
    ItemDao itemDao;

    @Inject
    UserDao userDao;

    @BeforeAll
    public void setupData() {
        this.dbService.truncateAll();
    }

    @Test
    public void createTest() {

        User u1 = new User("pepa", "123456", "Pepa", "Z depa");
        User u2 = new User("jarda", "123456", "Jarda", "Novotný");

        this.userDao.save(u1);
        this.userDao.save(u2);

        this.itemDao.save(new Item("knížka", 500.0, u1));
        this.itemDao.save(new Item("varná konvice", 150.0, u1));
        this.itemDao.save(new Item("židle", 1000.0, u2));
        this.itemDao.save(new Item("šroubovák", 200.0, u2));
        this.itemDao.save(new Item("pumpička", 500.0, u2));

        assertEquals("pumpička", this.itemDao.getOne(5).getCaption());
        assertEquals("jarda", this.itemDao.getOne(5).getCreatedBy().getLoginName());

        Item[] its = this.itemDao.getItemsByUser(1).toArray(new Item[0]);

        assertEquals("knížka", its[0].getCaption());
        assertEquals("varná konvice", its[1].getCaption());
    }

}
