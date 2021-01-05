package fit.bikeja.service;

import fit.bikeja.dao.UserDao;
import fit.bikeja.dto.UserDto;
import fit.bikeja.entity.User;
import fit.bikeja.entity.UserWithStats;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserDao userDao;

    @PostConstruct
    public void onInit() {
    }

    public Optional<UserDto> getUser(int id) {
        Optional<UserDto> user = Optional.empty();

        try {
            user = Optional.of(new UserDto(this.userDao.getOne(id)));
        } catch (EntityNotFoundException ignored) {
        }

        return user;
    }

    public Optional<UserDto> getUserByLoginNameAndPassword(String loginName, String password) {
        Optional<UserDto> user = Optional.empty();

        try {
            User u = this.userDao.getByLoginNameAndPassword(loginName, password);

            if (u != null) {
                user = Optional.of(new UserDto(u));
            }
        } catch (EntityNotFoundException ignored) {
        }

        return user;
    }

    public User createUser(UserDto user) {
        User u = user.toEntity();
        this.userDao.save(u);
        return u;
    }

    public void updateUser(int id, UserDto user) {
        User u = this.userDao.getOne(id);

        u.setLoginName(user.getLoginName());
        u.setPassword(user.getPassword());
        u.setFirstName(user.getFirstName());
        u.setSurName(user.getSurName());

        this.userDao.save(u);
    }

    public void deleteUser(int id) {
        this.userDao.deleteById(id);
    }

    public Collection<UserDto> getAllUsers() {
        return this.userDao.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    public Collection<UserWithStats> getAllUsersWithStats() {
        Query q = this.em.createNativeQuery(
            "SELECT u.id AS \"id\", u.loginName AS \"loginName\", u.password AS \"password\", u.firstName AS \"firstName\", u.surName AS \"surName\", COUNT(DISTINCT r1.id) AS \"totalReservations\", COUNT(DISTINCT r2.id) AS \"activeReservations\" FROM User u " +
            "LEFT JOIN Reservation r1 ON r1.user_id = u.id " +
            "LEFT JOIN Reservation r2 ON r2.user_id = u.id AND r2.isValid = 1 " +
            "GROUP BY u.id",
            UserWithStats.class
        );

        return q.getResultList();
    }
}
