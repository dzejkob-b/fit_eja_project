package fit.bikeja.dao;

import fit.bikeja.dao.UserDao;
import fit.bikeja.entity.User;
import fit.bikeja.service.DbService;
import fit.bikeja.service.UserService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    @Inject
    UserDao userDao;

    @Inject
    UserService userService;

    @Inject
    DbService dbService;

    @BeforeAll
    public void setupData() {
        this.dbService.truncateAll();
    }

    @Test
    public void createTest() {

        this.userDao.save(new User("pepa", "123456", "Pepa", "Z depa"));
        this.userDao.save(new User("jarda", "123456", "Jarda", "Novotný"));

        assertEquals("pepa", this.userDao.getOne(1).getLoginName());
        assertEquals("jarda", this.userDao.getOne(2).getLoginName());

        this.dbService.flushAndClean();
    }

    @Test
    public void deleteTest() {

        this.userDao.deleteById(1);
        this.userDao.deleteById(2);

        this.dbService.flushAndClean();

        // assertNull(this.userDao.getOne(1));
        // assertNull(this.userDao.getOne(2));
    }
}
