package fit.bikeja.dao;

import fit.bikeja.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface UserDao extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.loginName = ?1 AND u.password = ?2")
    User getByLoginNameAndPassword(String loginName, String password);

}
