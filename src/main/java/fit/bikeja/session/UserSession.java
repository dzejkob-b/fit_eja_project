package fit.bikeja.session;

import fit.bikeja.dto.UserDto;
import fit.bikeja.rest.RestException;
import fit.bikeja.restProxy.UserProxy;
import fit.bikeja.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.Optional;

@SessionScoped
public class UserSession implements Serializable {

    @Inject
    UserProxy userProxy;

    private Optional<UserDto> user;

    public UserSession() {
        this.user = Optional.empty();
    }

    public boolean isLogged() {
        return this.user.isPresent();
    }

    public Optional<UserDto> getUser() {
        return this.user;
    }

    public boolean login(String loginName, String password) {
        Optional<UserDto> u = Optional.empty();

        try {
            u = this.userProxy.getUserByLoginNameAndPassword(loginName, password);
        } catch (RestException ignored) {
        }

        if (u.isPresent()) {
            this.user = u;
            return true;

        } else {
            return false;
        }
    }

    public boolean logout() {
        if (this.user.isPresent()) {
            this.user = Optional.empty();
            return true;

        } else {
            return false;
        }
    }
}
